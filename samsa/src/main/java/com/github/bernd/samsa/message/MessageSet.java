package com.github.bernd.samsa.message;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.GatheringByteChannel;
import java.util.Iterator;
import java.util.List;


/**
 * A set of messages with offsets. A message set has a fixed serialized form, though the container
 * for the bytes could be either in-memory or on disk. The format of each message is
 * as follows:
 * 8 byte message offset number
 * 4 byte size containing an integer N
 * N message bytes as described in the Message class
 */
public abstract class MessageSet implements Iterable<MessageAndOffset> {
    public static final int MESSAGE_SIZE_LENGTH = 4;
    public static final int OFFSET_LENGTH = 8;
    public static final int LOG_OVERHEAD = MESSAGE_SIZE_LENGTH + OFFSET_LENGTH;
    public static final ByteBufferMessageSet EMPTY = new ByteBufferMessageSet(ByteBuffer.allocate(0));


    /**
     * The size of a message set containing the given messages
     */
    public static int messageSetSize(Iterable<Message> messages) {
        int size = 0;

        for (Message message : messages) {
            size += entrySize(message);
        }

        return size;
    }

    /**
     * The size of a list of messages
     */
    public static int messageSetSize(final List<Message> messages) {
        return messageSetSize((Iterable<Message>) messages);
    }

    /**
     * The size of a size-delimited entry in a message set
     */
    public static int entrySize(final Message message) {
        return LOG_OVERHEAD + message.size();
    }


    /**
     * Validate that all "magic" values in `messages` are the same and return their magic value and max timestamp
     */


    public static MagicAndTimestamp magicAndLargestTimestamp(List<Message> messages){

        byte firstMagicValue = messages.get(0).magic();
        Long largestTimestamp = Message.NO_TIMESTAMP;

        for(Message message :  messages)
        {
            if(message.magic() != firstMagicValue)
            {
                throw new IllegalStateException("Messages in the same message set must have same magic value");
            }
            if(firstMagicValue > Message.MAGIC_VALUE_V0)
            {
                largestTimestamp = Math.max(largestTimestamp, message.timestamp());
            }
        }

        return new MagicAndTimestamp(firstMagicValue, largestTimestamp);
    }

    /**
     * Write the messages in this set to the given channel starting at the given offset byte.
     * Less than the complete amount may be written, but no more than maxSize can be. The number
     * of bytes written is returned
     */
    public abstract int writeTo(GatheringByteChannel channel, long offset, int maxSize) throws IOException;

    /**
     * Provides an iterator over the message/offset pairs in this set
     */
    @Override
    public abstract Iterator<MessageAndOffset> iterator();

    /**
     * Gives the total size of this message set in bytes
     */
    public abstract int sizeInBytes();

    /**
     * Print this message set's contents. If the message set has more than 100 messages, just
     * print the first 100.
     */
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();

        builder.append(getClass().getSimpleName() + "(");
        Iterator<MessageAndOffset> iter = iterator();
        int i = 0;

        while (iter.hasNext() && i < 100) {
            final MessageAndOffset message = iter.next();
            builder.append(message);
            if (iter.hasNext()) {
                builder.append(", ");
            }
            i += 1;
        }
        if (iter.hasNext()) {
            builder.append("...");
        }
        builder.append(")");

        return builder.toString();
    }


    static class MagicAndTimestamp {
        private final Byte magic;
        private final Long timestamp;

        MagicAndTimestamp(Byte magic, Long timestamp) {
            this.magic = magic;
            this.timestamp = timestamp;
        }

        public Byte getMagic() {
            return magic;
        }

        public Long getTimestamp() {
            return timestamp;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            MagicAndTimestamp that = (MagicAndTimestamp) o;

            if (!magic.equals(that.magic)) return false;
            return timestamp.equals(that.timestamp);

        }

        @Override
        public int hashCode() {
            int result = magic.hashCode();
            result = 31 * result + timestamp.hashCode();
            return result;
        }
    }

}
