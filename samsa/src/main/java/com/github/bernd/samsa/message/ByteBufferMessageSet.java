package com.github.bernd.samsa.message;

import com.github.bernd.samsa.SamsaException;
import com.github.bernd.samsa.commons.TimestampType;
import com.github.bernd.samsa.compression.CompressionCodec;
import com.github.bernd.samsa.compression.CompressionFactory;
import com.github.bernd.samsa.utils.AbstractIterator;
import com.google.common.base.Function;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.GatheringByteChannel;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * A sequence of messages stored in a byte buffer
 * <p/>
 * There are two ways to create a ByteBufferMessageSet
 * <p/>
 * Option 1: From a ByteBuffer which already contains the serialized message set. Consumers will use this method.
 * <p/>
 * Option 2: Give it a list of messages along with instructions relating to serialization format. Producers will use this method.
 * <p>
 * * Message format v1 has the following changes:
 * - For non-compressed messages, timestamp and timestamp type attributes have been added. The offsets of
 * the messages remain absolute offsets.
 * - For compressed messages, timestamp and timestamp type attributes have been added and inner offsets (IO) are used
 * for inner messages of compressed messages (see offset calculation details below). The timestamp type
 * attribute is only set in wrapper messages. Inner messages always have CreateTime as the timestamp type in attributes.
 * <p>
 * We set the timestamp in the following way:
 * For non-compressed messages: the timestamp and timestamp type message attributes are set and used.
 * For compressed messages:
 * 1. Wrapper messages' timestamp type attribute is set to the proper value
 * 2. Wrapper messages' timestamp is set to:
 * - the max timestamp of inner messages if CreateTime is used
 * - the current server time if wrapper message's timestamp = LogAppendTime.
 * In this case the wrapper message timestamp is used and all the timestamps of inner messages are ignored.
 * 3. Inner messages' timestamp will be:
 * - used when wrapper message's timestamp type is CreateTime
 * - ignored when wrapper message's timestamp type is LogAppendTime
 * 4. Inner messages' timestamp type will always be ignored with one exception: producers must set the inner message
 * timestamp type to CreateTime, otherwise the messages will be rejected by broker.
 * <p>
 * Absolute offsets are calculated in the following way:
 * Ideally the conversion from relative offset(RO) to absolute offset(AO) should be:
 * <p>
 * AO = AO_Of_Last_Inner_Message + RO
 * <p>
 * However, note that the message sets sent by producers are compressed in a streaming way.
 * And the relative offset of an inner message compared with the last inner message is not known until
 * the last inner message is written.
 * Unfortunately we are not able to change the previously written messages after the last message is written to
 * the message set when stream compression is used.
 * <p>
 * To solve this issue, we use the following solution:
 * <p>
 * 1. When the producer creates a message set, it simply writes all the messages into a compressed message set with
 * offset 0, 1, ... (inner offset).
 * 2. The broker will set the offset of the wrapper message to the absolute offset of the last message in the
 * message set.
 * 3. When a consumer sees the message set, it first decompresses the entire message set to find out the inner
 * offset (IO) of the last inner message. Then it computes RO and AO of previous messages:
 * <p>
 * RO = IO_of_a_message - IO_of_the_last_message
 * AO = AO_Of_Last_Inner_Message + RO
 * <p>
 * 4. This solution works for compacted message sets as well.
 */

public class ByteBufferMessageSet extends MessageSet {
    private final ByteBuffer buffer;

    private int shallowValidByteCount = -1;

    public ByteBufferMessageSet(final ByteBuffer buffer) {
        this.buffer = buffer;
    }

    public ByteBufferMessageSet(final CompressionCodec compressionCodec,
                                final List<Message> messages) throws IOException {
        this(create(new AtomicLong(0), compressionCodec, messages));
    }

    public ByteBufferMessageSet(final CompressionCodec compressionCodec,
                                final AtomicLong offsetCounter,
                                final List<Message> messages) throws IOException {
        this(create(offsetCounter, compressionCodec, messages));
    }

    public ByteBufferMessageSet(final List<Message> messages) throws IOException {
        this(CompressionCodec.NONE, new AtomicLong(0), messages);
    }

    private static ByteBuffer create(final OffsetAssigner offsetAssigner,
                                     final CompressionCodec compressionCodec,
                                     final Long wrapperMessageTimestamp,
                                     final TimestampType timestampType,
                                     final List<Message> messages) throws IOException {
        if (messages.size() == 0) {
            return MessageSet.EMPTY.getBuffer();
        } else if (compressionCodec == CompressionCodec.NONE) {
            final ByteBuffer buffer = ByteBuffer.allocate(MessageSet.messageSetSize(messages));

            for (Message message : messages) {
                writeMessage(buffer, message, offsetAssigner.nextAbsoluteOffset());
            }

            buffer.rewind();
            return buffer;
        } else {

            MagicAndTimestamp magicAndTimestamp = null;

            if (wrapperMessageTimestamp == null) {
                magicAndTimestamp = MessageSet.magicAndLargestTimestamp(messages);
            } else {
                magicAndTimestamp = new MagicAndTimestamp(messages.get(0).magic(), wrapperMessageTimestamp);
            }

            //CALL WRITE COMPRESS MESSAGE

            final ByteArrayOutputStream byteArrayStream = new ByteArrayOutputStream(MessageSet.messageSetSize(messages));
            final DataOutputStream output = new DataOutputStream(CompressionFactory.create(compressionCodec, byteArrayStream));
            long offset = -1L;
            try {
                for (Message message : messages) {
                    offset = offsetCounter.getAndIncrement();
                    output.writeLong(offset);
                    output.writeInt(message.size());
                    output.write(message.buffer().array(), message.buffer().arrayOffset(), message.buffer().limit());
                }
            } finally {
                output.close();
            }
            byte[] bytes = byteArrayStream.toByteArray();
            final Message message = new Message(bytes, compressionCodec);
            final ByteBuffer buffer = ByteBuffer.allocate(message.size() + MessageSet.LOG_OVERHEAD);
            writeMessage(buffer, message, offset);
            buffer.rewind();
            return buffer;
        }
    }

    public static ByteBufferMessageSet decompress(final Message message) throws IOException {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        final InputStream inputStream = new ByteBufferBackedInputStream(message.payload());
        byte[] intermediateBuffer = new byte[1024];
        final InputStream compressed = CompressionFactory.create(message.compressionCodec(), inputStream);
        try {
            while (true) {
                final int dataRead = compressed.read(intermediateBuffer);

                if (dataRead < 1) {
                    break;
                }

                outputStream.write(intermediateBuffer, 0, dataRead);
            }
        } finally {
            compressed.close();
        }
        final ByteBuffer outputBuffer = ByteBuffer.allocate(outputStream.size());
        outputBuffer.put(outputStream.toByteArray());
        outputBuffer.rewind();

        return new ByteBufferMessageSet(outputBuffer);
    }

    public static void writeMessage(final ByteBuffer buffer, final Message message, final long offset) {
        buffer.putLong(offset);
        buffer.putInt(message.size());
        buffer.put(message.buffer());
        message.buffer().rewind();
    }

    private int shallowValidBytes() {
        if (shallowValidByteCount < 0) {
            int bytes = 0;
            final Iterator<MessageAndOffset> iter = internalIterator(true);
            while (iter.hasNext()) {
                final MessageAndOffset messageAndOffset = iter.next();
                bytes += MessageSet.entrySize(messageAndOffset.getMessage());
            }
            shallowValidByteCount = bytes;
        }
        return shallowValidByteCount;
    }

    /**
     * Write the messages in this set to the given channel
     */
    @Override
    public int writeTo(GatheringByteChannel channel, long offset, int maxSize) throws IOException {
        // Ignore offset and size from input. We just want to write the whole buffer to the channel.
        buffer.mark();
        int written = 0;
        while (written < sizeInBytes()) {
            written += channel.write(buffer);
        }
        buffer.reset();
        return written;
    }

    /**
     * default iterator that iterates over decompressed messages
     */
    @Override
    public Iterator<MessageAndOffset> iterator() {
        return internalIterator(false);
    }

    /**
     * iterator over compressed messages without decompressing
     */
    public Iterator<MessageAndOffset> shallowIterator() {
        return internalIterator(true);
    }

    @Override
    public int sizeInBytes() {
        return buffer.limit();
    }

    public ByteBuffer getBuffer() {
        return buffer;
    }

    private Iterator<MessageAndOffset> internalIterator(final boolean isShallow) {
        return new AbstractIterator<MessageAndOffset>() {
            private ByteBuffer topIter = buffer.slice();
            private Iterator<MessageAndOffset> innerIter = null;

            @Override
            protected MessageAndOffset makeNext() {
                try {
                    if (isShallow) {
                        return makeNextOuter();
                    } else {
                        if (innerDone()) {
                            return makeNextOuter();
                        } else {
                            return innerIter.next();
                        }
                    }
                } catch (IOException e) {
                    throw new SamsaException(e);
                }
            }

            public boolean innerDone() {
                return innerIter == null || !innerIter.hasNext();
            }

            public MessageAndOffset makeNextOuter() throws IOException {
                // if there isn't at least an offset and size, we are done
                if (topIter.remaining() < 12) {
                    return allDone();
                }
                long offset = topIter.getLong();
                int size = topIter.getInt();
                if (size < Message.MIN_HEADER_SIZE) {
                    throw new InvalidMessageException("Message found with corrupt size (" + size + ")");
                }

                // we have an incomplete message
                if (topIter.remaining() < size) {
                    return allDone();
                }

                // read the current message and check correctness
                ByteBuffer message = topIter.slice();
                message.limit(size);
                topIter.position(topIter.position() + size);
                final Message newMessage = new Message(message);

                if (isShallow) {
                    return new MessageAndOffset(newMessage, offset);
                } else {
                    switch (newMessage.compressionCodec()) {
                        case NONE:
                            innerIter = null;
                            return new MessageAndOffset(newMessage, offset);
                        default:
                            innerIter = ByteBufferMessageSet.decompress(newMessage).internalIterator(false);

                            if (!innerIter.hasNext()) {
                                innerIter = null;
                            }
                            return makeNext();
                    }
                }
            }
        };
    }


    private static void writeCompressedMessages(final CompressionCodec codec, final OffsetAssigner offsetAssigner, final MagicAndTimestamp magicAndTimestamp, final TimestampType timestampType, final List<Message> messages) {

        if(codec == CompressionCodec.NONE)
        {
            throw new SamsaException("compressionCodec must not be NONE");
        }
        if(messages == null || messages.size() == 0)
        {
            throw new SamsaException("cannot write empty compressed message set");
        }

        Long offset = -1L;
        Byte magic = magicAndTimestamp.getMagic();
        //new MessageWriter(Math.min(Math.max(MessageSet.messageSetSize(messages) / 2, 1024), 1 << 16));

//        
//        val messageWriter = new MessageWriter(math.min(math.max(MessageSet.messageSetSize(messages) / 2, 1024), 1 << 16))
//        messageWriter.write(
//                codec = codec,
//                timestamp = magicAndTimestamp.timestamp,
//                timestampType = timestampType,
//                magicValue = magic) { outputStream =>
//
//            val output = new DataOutputStream(CompressionFactory(codec, magic, outputStream))
//            try {
//                for (message <- messages) {
//                    offset = offsetAssigner.nextAbsoluteOffset()
//
//                    if (message.magic != magicAndTimestamp.magic)
//                        throw new IllegalArgumentException("Messages in the message set must have same magic value")
//
//                    // Use inner offset if magic value is greater than 0
//                    val innerOffset = if (magicAndTimestamp.magic > Message.MagicValue_V0)
//                        offsetAssigner.toInnerOffset(offset)
//                    else
//                        offset
//
//                    output.writeLong(innerOffset)
//                    output.writeInt(message.size)
//                    output.write(message.buffer.array, message.buffer.arrayOffset, message.buffer.limit)
//                }
//            } finally {
//                output.close()
//            }
//        }
//
//        (messageWriter, offset)


    }

    /**
     * Update the offsets for this message set. This method attempts to do an in-place conversion
     * if there is no compression, but otherwise recopies the messages
     */
    public ByteBufferMessageSet assignOffsets(final AtomicLong offsetCounter, final CompressionCodec codec) throws IOException {
        if (codec == CompressionCodec.NONE) {
            // do an in-place conversion
            int position = 0;
            buffer.mark();
            while (position < sizeInBytes() - MessageSet.LOG_OVERHEAD) {
                buffer.position(position);
                buffer.putLong(offsetCounter.getAndIncrement());
                position += MessageSet.LOG_OVERHEAD + buffer.getInt();
            }
            buffer.reset();
            return this;
        } else {
            // messages are compressed, crack open the messageset and recompress with correct offset
            final Iterator<Message> messages = Iterators.transform(internalIterator(false), new Function<MessageAndOffset, Message>() {
                @Override
                public Message apply(MessageAndOffset messageAndOffset) {
                    return messageAndOffset.getMessage();
                }
            });
            return new ByteBufferMessageSet(codec, offsetCounter, Lists.newArrayList(messages));
        }
    }


    static class OffsetAssigner {
        private Long[] offset;
        int index = 0;

        public OffsetAssigner(Long... offsetVet) {
            this.offset = offsetVet;
        }

        public OffsetAssigner(Long baseOffset, Byte magic, List<MessageAndOffset> messageAndOffsets) {
            this.offset = messageAndOffsets.stream().map(messageAndOffset -> messageAndOffset.getOffset()).toArray(size -> new Long[size]);
        }

        long nextAbsoluteOffset() {
            Long result = offset[index];
            index += 1;
            return result;
        }

        Long toInnerOffset(Long offsetValue) {
            return offsetValue - offset[0];
        }

    }


}
