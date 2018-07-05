#!/bin/bash

echo "start"

if [[ `mkdir -p /tmp/influxdb/data` -ne 0 ]]; then
        echo "\nCannot create folder at /tmp/influxdb/data. Dying ..."
        exit 1
fi

#Remove all cantainer  
if [[ "$(docker ps)" =~ "influx" ]]; then 
  docker-compose stop && true
  docker-compose rm -f && true
fi 

docker-compose up -d

echo "Grafana: http://127.0.0.1:3000 - admin/admin"

echo
echo "Current database list"
curl -G http://localhost:8086/query?pretty=true --data-urlencode "db=glances" --data-urlencode "q=SHOW DATABASES"

echo
echo "Create a new database ?"
echo "curl -XPOST 'http://localhost:8086/query' --data-urlencode 'q=CREATE DATABASE mydb'"