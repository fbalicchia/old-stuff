## Logagent Plugin: Grok

Input filter plugin for [@sematext/logagent](http://sematext.com/logagent/). The plugin acts as grok filter.

## Installation 

Install [@sematext/logagent](https://www.npmjs.com/package/@sematext/logagent) 

```
npm i -g @sematext/logagent 
npm i -g logagent-input-filter-grok
```
 
### Configuration

```
input:
  stdin: true
  tcp: 
    # external modules must be installed with npm i megastef/logagent-tcp-input -g 
    module: input-tcp 
    port: 45900 
    bindAddress: 0.0.0.0
    sourceName: tcpTest  

inputFilter:
  - module: grok
    config:
      # Logagent use node-grok and by default is load load different patterns. but using 'matchSource' parameter is possiible to define a custom one
      # please see https://github.com/Beh01der/node-grok/tree/master/lib/patterns to find patter loaded at bootstrap
      matchSource: '%{IP:client} \[%{TIMESTAMP_ISO8601:timestamp}\] "%{WORD:method} %{URIHOST:site}%{URIPATHPARAM:url}" %{INT:code} %{INT:request} %{INT:response} - %{NUMBER:took} \[%{DATA:cache}\] "%{DATA:mtag}" "%{DATA:agent}"'
      filePath: /tmp/grok-patterns
      idpattern: USER
      
output:
  stdout: yaml


```

Start logagent

```
logagent --config input-grok-filter.yml
```

