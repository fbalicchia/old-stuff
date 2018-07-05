

# Overview

Sematext Docker Agent is a modern, Docker-native monitoring and log collection agent. It runs as a tiny container on every Docker host and collects logs, metrics, and events for all cluster nodes and their containers. The agent discovers all containers on all nodes managed by Docker Swarm, Docker Cloud, Kubernetes, Red Hat OpenShift, Rancher or Mesos. After the deployment of Sematext Docker Agent, all logs, Docker events, and metrics are immediately available out of the box.

<div class="video_container">
  <iframe class="video" src="https://www.youtube.com/embed/cLKnn1Qbxlc" frameborder="0" allowfullscreen ></iframe>
</div>

The following information is collected and transmitted to Sematext Cloud or [Sematext Enterprise](sematext-enterprise).  Sematext Cloud integration for Docker uses the open-source
 [Docker monitoring agent](https://github.com/sematext/sematext-agent-docker) available on
Docker Registry as a ready-to-go [sematext-agent-docker image](https://hub.docker.com/r/sematext/sematext-agent-docker/).

<table>
<tbody>
<tr class="odd">
<td><strong>Type</strong></td>
<td><strong>Description</strong></td>
</tr>
<tr class="even">
<td><strong>Operating System Metrics</strong></td>
<td><p>Host machine metrics</p>
<ul>
<li>CPU Usage</li>
<li>Memory Usage</li>
<li>Network Stats</li>
<li>Disk I/O Stats</li>
</ul></td>
</tr>
<tr class="odd">
<td><strong>Docker Container Metrics/Stats</strong></td>
<td><ul>
<li>CPU Usage / limits</li>
<li>Memory Usage / Limits / Fail Counters</li>
<li>Network Stats</li>
<li>Disk I/O Stats</li>
</ul></td>
</tr>
<tr class="even">
<td><strong>Events</strong></td>
<td> </td>
</tr>
<tr class="odd">
<td> Agent Startup Event</td>
<td>server-info – created by spm-agent framework with node.js and OS version info on startup. Please note the agent is implemented in node.js.</td>
</tr>
<tr class="even">
<td> </td>
<td>Docker-info – Docker Version, API Version, Kernel Version on startup</td>
</tr>
<tr class="odd">
<td>Docker Events</td>
<td>Container Lifecycle Events| create, exec_create, destroy, export, ...</td>
</tr>
<tr class="even">
<td>Container Runtime Events</td>
<td>die, exec_start, kill, pause, restart, start, stop, unpause, ...</td>
</tr>
<tr class="odd">
<td><strong>Docker Logs</strong></td>
<td> </td>
</tr>
<tr class="even">
<td>Default Fields</td>
<td><ul>
<li>hostname / IP address</li>
<li>container id</li>
<li>container name</li>
<li>image name</li>
<li>message</li>
</ul></td>
</tr>
<tr class="odd">
<td><p>Log formats</p>
<p>(detection and log parsers)</p></td>
<td><ul>
<li>Nginx</li>
<li>Apache httpd, Kafka, Solr, HBase, Zookeeper, Cassandra</li>
<li>MySQL</li>
<li>MongoDB</li>
<li>Redis</li>
<li>Elasticsearch</li>
<li>NSQ / Nsq.io</li>
<li>JSON, plain text</li>
<li>patterns are maintained at <a href="https://github.com/sematext/logagent-js" class="uri" class="external-link">https://github.com/sematext/logagent-js</a></li>
</ul></td>
</tr>

</tbody>
</table>


## Certified and public images 

There are several places to obtain Sematext Docker Agent images: 

1. Docker Certified images in the [Docker Store](https://store.docker.com/images/sematext-agent-monitoring-and-logging) 

```
docker pull store/sematext/sematext-agent-docker
``` 

2. Red Hat certified images in the [Red Hat Container Catalog](https://access.redhat.com/containers/?tab=overview&platform=docker#/registry.connect.redhat.com/sematext/sematext-agent-docker)

```
docker login registry.connect.redhat.com
docker pull registry.connect.redhat.com/sematext/sematext-agent-docker
```

3. Public images from [Docker Hub](https://hub.docker.com/r/sematext/sematext-agent-docker/) 

```
docker pull sematext/sematext-agent-docker
```


## Github Repository

Latest information
for [sematext-agent-docker](https://github.com/sematext/sematext-agent-docker)
and [open issues](https://github.com/sematext/sematext-agent-docker/issues)

