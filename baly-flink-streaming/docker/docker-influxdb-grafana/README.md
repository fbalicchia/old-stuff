# Grafana + influxdb + chronograh in 2 minutes 

Executing run.sh we bootstrap grafana with influxdb. to ingest can be possible to use directly rest api without
telegraf.

For influxdb by defaul we use [tsm1](https://github.com/influxdata/influxdb/commit/15d723dc77651bac83e09e2b1c94be480966cb0d) engine 
and grafana we load briangann-gauge-panel,natel-plotly-panel,grafana-simple-json-datasource plugins.

`To use it type run.sh`


* `Chronograph: Influxdb admin interface` (default: 8888)
* `Influxdb port` (default: 8090)
* `Grafana port` (default: 3000)


To check database list

`curl -G http://localhost:8086/query?pretty=true --data-urlencode "db=glances" --data-urlencode "q=SHOW DATABASES"`

For creating a dummydb and load dummy data to play
```
curl -i -XPOST 'http://localhost:8086/write?db=mydb' --data-binary 'cpu_load_short,host=server02 value=0.67
cpu_load_short,host=server02,region=us-west value=0.55 1422568543702900257
cpu_load_short,direction=in,host=server01,region=us-west value=2.0 1422568543702900257'
```

login and password for every console are admin/admin