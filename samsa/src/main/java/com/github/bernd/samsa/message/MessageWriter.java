package com.github.bernd.samsa.message;

/**
 * Created by fbalicchia on 13/12/16.
 */
public class MessageWriter extends BufferingOutputStream {


    public MessageWriter(Integer segmentSize) {
        super(segmentSize);
    }

}
