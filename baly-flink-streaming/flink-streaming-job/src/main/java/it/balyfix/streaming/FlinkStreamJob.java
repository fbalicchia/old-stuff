package it.balyfix.streaming;

import it.balyfix.streaming.commons.StreamFactory;
import it.balyfix.streaming.model.Order;
import it.balyfix.streaming.processor.OrderMapping;
import it.balyfix.streaming.processor.TinyInfluxMapper;
import it.balyfix.streaming.sink.InfluxDBConfig;
import it.balyfix.streaming.sink.InfluxDBSink;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.timestamps.AscendingTimestampExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class FlinkStreamJob implements AutoCloseable
{
    final Logger log = LoggerFactory.getLogger(FlinkStreamJob.class);

    private static final int N = 10000;

    @Autowired
    private StreamProperties streamProperties;

    @Autowired
    private StreamFactory streamFactory;


    public void doRun()
    {

        InfluxDBConfig influxDBConfig = streamProperties.retrieveDbConfig();

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);

        DataStream<String> ingestStream = streamFactory.get(env);
        DataStream<Order> orderStream = ingestStream.map(new OrderMapping());

        DataStream<Order> orderTimeStamped = orderStream.assignTimestampsAndWatermarks(new AscendingTimestampExtractor<Order>()
        {
            private static final long serialVersionUID = 8547487372808983262L;

            @Override
            public long extractAscendingTimestamp(Order element)
            {
                long currentTimestamp = element.getTimestamp();
                return currentTimestamp == Long.MIN_VALUE ? Long.MIN_VALUE : currentTimestamp - 1;
            }
        });

        orderTimeStamped.map(new TinyInfluxMapper()).addSink(new InfluxDBSink(influxDBConfig));

        try
        {
            env.execute("flinkStream start  ");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    @Override
    public void close() throws Exception
    {
    }


}
