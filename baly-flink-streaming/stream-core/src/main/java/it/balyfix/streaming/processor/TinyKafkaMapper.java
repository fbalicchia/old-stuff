package it.balyfix.streaming.processor;


import org.apache.flink.api.common.functions.MapFunction;


public class TinyKafkaMapper implements MapFunction<String,String>
{
    @Override
    public String map(String s) throws Exception
    {
        return "Kafka sad " + s;
    }
}
