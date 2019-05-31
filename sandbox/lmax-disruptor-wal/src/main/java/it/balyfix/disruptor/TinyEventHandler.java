package it.balyfix.disruptor;

import com.lmax.disruptor.EventHandler;

/**
 * @author fbalicchia
 * @version $Id: $
 */
public class TinyEventHandler implements EventHandler<LongEvent>
{

    /**
     * {@inheritDoc}
     */
    @Override
    public void onEvent(LongEvent event, long sequence, boolean endOfBatch) throws Exception
    {
       System.out.println("Message arrived from " + event.toString() );
        
    }

}
