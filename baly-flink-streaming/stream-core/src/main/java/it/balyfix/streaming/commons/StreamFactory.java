package it.balyfix.streaming.commons;

import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.io.Serializable;


public interface StreamFactory  extends Serializable
{
    DataStream<String> get(StreamExecutionEnvironment env);
}
