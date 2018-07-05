cd kafka-test
docker-compose version 
docker version
export KAFKA_ADVERTISED_HOST_NAME=127.0.0.1
if [[ "$(docker ps)" =~ "kafka" ]]; then 
  docker-compose stop && true
  docker-compose rm -f && true
fi 
docker-compose up -d 
sleep 25
node load-file.js
