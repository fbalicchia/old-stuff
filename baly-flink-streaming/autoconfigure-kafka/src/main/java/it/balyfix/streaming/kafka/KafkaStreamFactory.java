package it.balyfix.streaming.kafka;


import it.balyfix.streaming.commons.StreamFactory;
import lombok.Builder;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer010;

import java.util.Properties;


@Builder
public class KafkaStreamFactory implements StreamFactory
{

    private String topic;

    private String groupId;

    private String host;


    @Override
    public DataStream<String> get(StreamExecutionEnvironment env)
    {
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", host);
        properties.setProperty("group.id", groupId);
        return env
            .addSource(new FlinkKafkaConsumer010<>(
                topic,
                new SimpleStringSchema(),
                properties));

    }
}
