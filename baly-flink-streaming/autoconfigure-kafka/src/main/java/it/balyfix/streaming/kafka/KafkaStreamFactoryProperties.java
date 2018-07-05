package it.balyfix.streaming.kafka;

import it.balyfix.streaming.commons.StreamFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties("it.balyfix.streaming.kafka")
public class KafkaStreamFactoryProperties
{

    @Value("${topic:test}")
    private String topic;
    @Value("${groupid:test}")
    private String groupId;

    @Value("${bootstrapServers:localhost:9092}")
    private String bootstrapServers;

    public String getTopic()
    {
        return topic;
    }

    public void setTopic(String topic)
    {
        this.topic = topic;
    }

    public String getGroupId()
    {
        return groupId;
    }

    public void setGroupId(String groupId)
    {
        this.groupId = groupId;
    }

    public String getBootstrapServers()
    {
        return bootstrapServers;
    }

    public void setBootstrapServers(String bootstrapServers)
    {
        this.bootstrapServers = bootstrapServers;
    }

    public StreamFactory build()
    {
        return KafkaStreamFactory.builder().topic(topic).groupId(groupId).host(bootstrapServers).build();
    }
}
