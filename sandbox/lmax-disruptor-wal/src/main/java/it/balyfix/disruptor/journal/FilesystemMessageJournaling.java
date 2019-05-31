package it.balyfix.disruptor.journal;

import java.io.*;
import java.nio.*;
import java.nio.channels.*;
import java.nio.charset.*;
import java.util.List;

/**
 * @author fbalicchia
 * @version $Id: $
 */
public class FilesystemMessageJournaling implements Journal
{


    private final File directory;
    private FileChannel file = null;
    private final ByteBuffer[] buffers = new ByteBuffer[2];
    public static Charset charset = Charset.forName("UTF-8");
    public static CharsetEncoder encoder = charset.newEncoder();

    public FilesystemMessageJournaling(File directory)
    {
        this.directory = directory;
        buffers[0] = ByteBuffer.allocate(4);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Entry createEntry(byte[] idBytes, byte[] messageBytes)
    {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long write(List<Entry> entries)
    {
        // TODO Auto-generated method stub
        return 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long write(byte[] idBytes, byte[] messageBytes)
    {
        // TODO Auto-generated method stub
        return 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<JournalReadEntry> read(long maximumCount)
    {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void markJournalOffsetCommitted(long offset)
    {
        // TODO Auto-generated method stub
        
    }


    private void save() throws IOException
    {
        String event = "Test";
        int size = event.toString().getBytes().length;

        if (null == file)
        {
            file = new RandomAccessFile(new File(directory, "jnl"), "rw").getChannel();
        }

        buffers[0].clear();
        buffers[0].putInt(size).flip();
        buffers[1] = encoder.encode(CharBuffer.wrap(event.toString()));
        buffers[1].clear().limit(size);

        while (buffers[1].hasRemaining())
        {
            file.write(buffers);
        }

        buffers[1].clear();
        buffers[1] = null;

//
//        if (endOfBatch)
//        {
//            file.force(true);
//        }
    }

}
