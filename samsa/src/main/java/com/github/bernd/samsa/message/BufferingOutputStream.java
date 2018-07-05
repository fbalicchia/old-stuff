package com.github.bernd.samsa.message;

import java.io.IOException;
import java.io.OutputStream;


/**
 * OutputStream that buffers incoming data in segmented byte arrays
 * This does not copy data when expanding its capacity
 * It has a ability to
 * - write data directly to ByteBuffer
 * - copy data from an input stream to interval segmented arrays directly
 * - hold a place holder for an unknown value that can be filled in later
 */
public class BufferingOutputStream {

    private final MyOutputStream outputStream = new MyOutputStream();
    Segment currentSegment;
    Integer filled;
    Segment headSegment;
    Integer segmentSize;


    public BufferingOutputStream(Integer segmentSize) {
        currentSegment = new Segment(segmentSize);
        headSegment = currentSegment;
        filled = 0;
        segmentSize = segmentSize;
    }


    public Integer size() {
        return filled + currentSegment.written;
    }

    private void addSegment() {
        filled += currentSegment.written;
        Segment newSeg = new Segment(segmentSize);
        currentSegment.next = newSeg;
        currentSegment = newSeg;
    }

    public void write(byte[] b, int off, int len) throws IOException {
        if (off >= 0 && off <= b.length && len >= 0 && off + len <= b.length) {
            Integer remaining = len;
            Integer offset = off;
            while (remaining > 0) {
                if (currentSegment.getFreeSpace() <= 0) addSegment();

                Integer amount = Math.min(currentSegment.getFreeSpace(), remaining);
                System.arraycopy(b, offset, currentSegment.bytes, currentSegment.written, amount);
                currentSegment.written += amount;
                offset += amount;
                remaining -= amount;
            }
        } else {
            throw new IndexOutOfBoundsException();
        }
    }


    private class ReservedOutput extends OutputStream {
        Segment cur;
        Integer off;
        Integer len;

        ReservedOutput(Segment seg, Integer offSet, Integer length) {
            this.cur = seg;
            this.off = offSet;
            this.len = length;
        }

        @Override
        public void write(int value) throws IOException {

            if (len <= 0) throw new IndexOutOfBoundsException();
            if (cur.bytes.length <= off) {
                cur = cur.next;
                off = 0;
            }
            cur.bytes[off] = Integer.valueOf(value).byteValue();
            off += 1;
            len -= 1;
        }
    }


    private class Segment {
        Byte[] bytes;
        Integer written = 0;
        Segment next = null;

        public Segment(Integer size) {
            bytes = new Byte[size];
        }

        public Integer getFreeSpace() {
            return bytes.length - written;
        }

    }


    private class MyOutputStream extends OutputStream {
        @Override
        public void write(int b) throws IOException {

            if (currentSegment.getFreeSpace() <= 0)
                addSegment();
            currentSegment.bytes[currentSegment.written] = Integer.valueOf(b).byteValue();
            currentSegment.written += 1;

        }
    }
}
