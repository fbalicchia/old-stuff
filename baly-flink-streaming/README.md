# flink-streaming collector 

This is just me playing around with flink-streaming and influx as collector
```bash


        +-----------------------+
        |         Kafka         |
        |                       |
        +-----------+-----------+
                    |
+-------------------v--------------------+
|                                        |
|      +-------------------------+       |
|      |    StreamFactory        |       |
|      +------------+------------+       |
|                   |                    |
|                   |                    |
|      +------------v------------+       |
|      |       Filtering         |       |
|      +------------+------------+       |
|                   |                    |
|                   |                    |
|      +------------v------------+       |
|      |  AggregationFactory     |       |
|      +------------+------------+       |
|                   |                    |
|                   |                    |
+----------------------------------------+
                    |
       +------------v-------------+
       |   Storage (Influxdb)     |
       |                          |
       +--------------------------+


# run local

mvn clean install;cd flink-streaming-job;mvn package

java -jar ./target/flink-streaming-job.jar \
  --it.balyfix.streaming.kafka.topic=ciao \
  --it.balyfix.streaming.url=http://localhost:8086
```

## Notes
  improve build
  improve settings and check
