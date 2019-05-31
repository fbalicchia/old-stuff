//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package it.balyfix.disruptor.journal.wal;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;


public class NullWritable implements WritableComparable
{
    private static final NullWritable THIS = new NullWritable();

    private NullWritable() {
    }

    public static NullWritable get() {
        return THIS;
    }

    public String toString() {
        return "(null)";
    }

    public int hashCode() {
        return 0;
    }

    public int compareTo(Object other) {
        if(!(other instanceof NullWritable)) {
            throw new ClassCastException("can\'t compare " + other.getClass().getName() + " to NullWritable");
        } else {
            return 0;
        }
    }

    public boolean equals(Object other) {
        return other instanceof NullWritable;
    }

    public void readFields(DataInput in) throws IOException {
    }

    public void write(DataOutput out) throws IOException {
    }

    static {
        WritableComparator.define(NullWritable.class, new NullWritable.Comparator());
    }

    public static class Comparator extends WritableComparator {
        public Comparator() {
            super(NullWritable.class);
        }

        public int compare(byte[] b1, int s1, int l1, byte[] b2, int s2, int l2) {
            assert 0 == l1;

            assert 0 == l2;

            return 0;
        }
    }
}
