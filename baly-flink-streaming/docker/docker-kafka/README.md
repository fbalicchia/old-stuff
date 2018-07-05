# Apache-kafka docker-image  

Apache kafka docker image with a tiny Node.js producer that load and xml and publish it. When image become
container load a fake cert that can be used to test SSL connection.

This image can be used to test a pipeline where entry point is Apache Kafka where producer can be an ESB endPoint
like spring-integration, mule, camel etc.etc

```bash
# npm install
# ./test.sh 
```

bash shell send order.xml to kafka on topic `test` with `clientid test`