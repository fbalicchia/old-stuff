package it.balyfix.disruptor;

import com.lmax.disruptor.dsl.Disruptor;

import it.balyfix.disruptor.journal.JournalingMessageHandler;

import com.lmax.disruptor.RingBuffer;

import java.io.File;
import java.nio.ByteBuffer;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class LongEventMain {
    
    
    public int BUFFER_SIZE = 1024;
    
    
	public void handleEvent(LongEvent event, long sequence, boolean endOfBatch) {
		System.out.println(event);
	}

	public void translate(LongEvent event, long sequence, ByteBuffer buffer) {
		event.set(buffer.getLong(0));
	}

	public static void main(String[] args) throws Exception {
		new LongEventMain().run();
	}
	public void run() throws InterruptedException 
	{
	    Executor executor = Executors.newCachedThreadPool();
        Disruptor<LongEvent> disruptor = new Disruptor<>(LongEvent::new, BUFFER_SIZE, executor);
        JournalingMessageHandler journaller = new JournalingMessageHandler(new File("/tmp/"));
        disruptor.handleEventsWith(journaller).then(new TinyEventHandler());
        disruptor.start();
        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();
        LongEventProducerWithTranslator longEventProducerWithTranslator = new LongEventProducerWithTranslator(
                ringBuffer);
        ByteBuffer bb = ByteBuffer.allocate(8);
        for (long l = 0; true; l++) {
            bb.putLong(0, l);
            longEventProducerWithTranslator.onData(bb);
            Thread.sleep(1000);
        }
	}
	
	
	
}