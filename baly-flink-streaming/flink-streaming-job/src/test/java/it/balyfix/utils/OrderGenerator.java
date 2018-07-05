package it.balyfix.utils;


import it.balyfix.streaming.model.OrderData;
import org.apache.flink.shaded.curator.org.apache.curator.shaded.com.google.common.collect.Lists;
import org.apache.flink.streaming.api.functions.source.RichParallelSourceFunction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;


public class OrderGenerator extends RichParallelSourceFunction<OrderData>
{
    private static final long serialVersionUID = -6912715112733100988L;

    private volatile boolean running = true;

    private static final int baseInterval = 5000;

    private static Random rnd = new Random();

    private static final int maxDelay = 2000;


    private int numOrders = 10;

    @Override
    public void run(SourceContext<OrderData> sourceContext) throws Exception
    {

        final ScheduledExecutorService exec = Executors.newScheduledThreadPool(2);

        try
        {
            while (running)
            {
                for (int i = 0; i < numOrders; i++)
                {

                    long delay = rnd.nextInt(maxDelay);

                    OrderData newOrder = new OrderData();
                    newOrder.setEmail(getRandomMail());
                    newOrder.setOrderNumber(buildOrderNumber());
                    newOrder.setPaymentType(getPaymentType());
                    newOrder.setTotalAmount(getRandomPrice());
                    newOrder.setOrderDate(randomLocaDateTime());
                    newOrder.setNumber(rnd.nextInt(300));


                    exec.schedule(() ->
                    {
                        try
                        {
                            sourceContext.collect(newOrder);
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }, delay, TimeUnit.MILLISECONDS);
                }

                Thread.sleep(baseInterval);
            }
        }
        finally
        {
            exec.shutdownNow();
        }

    }


    private static String getRandomMail()
    {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        while (salt.length() < 10)
        {
            int index = rnd.nextInt(SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index % SALTCHARS.length()));
        }
        String saltStr = salt.toString();
        return saltStr + "@gmail.copm";

    }

    private static BigDecimal getRandomPrice()
    {
        int abs = Math.abs(rnd.nextInt());
        //hope sometimes return negative
        return BigDecimal.valueOf(abs);

    }


    private static String buildOrderNumber()
    {
        String SaltNumber = "1234567890";

        StringBuilder newOrderNumber = new StringBuilder();

        while (newOrderNumber.length() < 16)
        {
            int index = rnd.nextInt(SaltNumber.length());
            newOrderNumber.append(SaltNumber.charAt(index % SaltNumber.length() ));
        }

        return newOrderNumber.toString();
    }


    private static Long randomLocaDateTime()
    {
        long minTime = LocalDateTime.of(1970, 1, 1, 0, 0, 0, 0).toEpochSecond(ZoneOffset.UTC);
        long maxTime = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
        long randomSec = ThreadLocalRandom.current().nextLong(minTime, maxTime);
        return LocalDateTime.ofEpochSecond(randomSec, 30, ZoneOffset.UTC).toEpochSecond(ZoneOffset.UTC);
    }

    private static String getPaymentType()
    {
        int pick = rnd.nextInt(PaymentType.values().length);
        return PaymentType.values()[pick].toString();
    }


    public static List<OrderData> orderList()
    {
        List<OrderData> result = Lists.newArrayList();

        OrderData newOrder = new OrderData();
        newOrder.setEmail(getRandomMail());
        newOrder.setOrderNumber(buildOrderNumber());
        newOrder.setPaymentType(PaymentType.CARTA_DOCENTI.toString());
        newOrder.setTotalAmount(BigDecimal.valueOf(22));
        newOrder.setOrderDate(randomLocaDateTime());
        newOrder.setNumber(200);


        OrderData newOrder1 = new OrderData();
        newOrder1.setEmail(getRandomMail());
        newOrder1.setOrderNumber(buildOrderNumber());
        newOrder1.setPaymentType(PaymentType.CARTA_DOCENTI.toString());
        newOrder1.setTotalAmount(BigDecimal.valueOf(25));
        newOrder1.setOrderDate(randomLocaDateTime());
        newOrder1.setNumber(100);


        result.add(newOrder);
        result.add(newOrder1);


        return result;
    }



    @Override
    public void cancel()
    {
        running = false;
    }
}
