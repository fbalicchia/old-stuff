package it.balyfix.disruptor;

import java.io.*;
import java.nio.*;
import java.nio.channels.*;
import java.nio.charset.*;
import java.util.*;


/**
 * Created by fbalicchia on 30/03/2018.
 */
public class TestMain
{
    public static void main(String[] argv) throws IOException
    {


//        ByteBuffer[] buffers = new ByteBuffer[2];
//        Charset charset = Charset.forName("UTF-8");
//        CharsetEncoder encoder = charset.newEncoder();
//        FileChannel channel = new RandomAccessFile(new File("/tmp/test", "jonny"), "rw").getChannel();
//        buffers[0] = ByteBuffer.allocate(4);
//        buffers[0].clear();
//        buffers[0].putInt(2).flip();
//        //buffers[1] = encoder.encode(CharBuffer.wrap(event.toString()));
//        buffers[1] = encoder.encode(CharBuffer.wrap("Ciao Mondo"));
//        channel.write(buffers);
//        System.out.println("Done");


        //generate random UUIDs
        UUID idOne = UUID.randomUUID();
        UUID idTwo = UUID.randomUUID();
        log("UUID One: " + idOne);
        log("UUID Two: " + idTwo);




    }


    private static void log(Object aObject){
        System.out.println( String.valueOf(aObject) );
    }
}
