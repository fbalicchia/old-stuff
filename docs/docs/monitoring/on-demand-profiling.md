On-demand profiling lets you profile your own JVM-based applications or
even any 3rd party JVM-based applications (e.g. Spark, Elasticsearch,
Solr, Kafka, Hadoop, Storm, Cassandra, HBase, etc.).

 

**Notes**:

  - Profiling is different from [Transaction Tracing](transaction-tracing).  Profiling is something
    you run on demand, for a short period of time, on a specific
    app/server.  It detects application hotspots, just like a typical
    profiler.  Transaction Tracing traces individual *transactions*,
    such as HTTP requests, and shows slow parts of each such transaction
    trace.  Once enabled, it traces continuously.  Using a profiler and
    having Transaction Tracing is not exclusive - one can use both.
  - The profiler does not require you to enable it in the monitoring
    agent.  It requires no restarts.  It works with both
    [embedded](spm-monitor-javaagent) and
    [standalone](spm-monitor-standalone) agent.
  - SPM client version 1.29.2 or greater is required.
  - Anything that runs in the JVM can be profiled - Java, Scala,
    Clojure, Groovy, JRuby, JPython, etc.

**Known issues**:

  - In order to profile Elasticsearch server you must enable remote JMX
    connection (as described [standalone monitor setup instructions page](spm-monitor-standalone)) and
    set SPM\_MONITOR\_JMX\_PARAMS property in a .properties file under
    the /opt/spm/spm-monitor/conf/ directory.

**Resources**:

  - <https://sematext.com/blog/2016/03/17/on-demand-java-profiling/>

