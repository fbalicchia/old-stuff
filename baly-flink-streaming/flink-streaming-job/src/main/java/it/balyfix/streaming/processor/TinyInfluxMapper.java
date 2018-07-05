package it.balyfix.streaming.processor;

import it.balyfix.streaming.model.Order;
import it.balyfix.streaming.sink.InfluxDBPoint;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.shaded.curator.org.apache.curator.shaded.com.google.common.collect.Maps;

import java.util.HashMap;
import java.util.Map;


public class TinyInfluxMapper implements MapFunction<Order,InfluxDBPoint>
{

    private static final long serialVersionUID = 0L;

    @Override
    public InfluxDBPoint map(Order orderData) throws Exception
    {

        long timestamp = orderData.getTimestamp();
        Map<String, String> tags = Maps.newHashMap();
        tags.put("payment_type", orderData.getPaymentType());
        Map<String, Object> fields = new HashMap<>();
        fields.put("total_amount", orderData.getTotalAmount());
        return new InfluxDBPoint("orders", timestamp, tags, fields);
    }
}
