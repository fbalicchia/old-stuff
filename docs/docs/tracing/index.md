### What is Transaction Tracing

Transaction tracing lets one trace code execution from beginning to end.
 It also enables [Database Operations](database-operations) reporting. In case of
SPM, transaction tracing can also cross applications, networks, and
servers. For example, you can trace code execution from a beginning of
an HTTP request made to a web application through any calls this web
application makes to relational, NoSQL, or any other databases or
backend servers and services like Elasticsearch or Solr or Kafka, etc.,
all the way to the response the application returns to the original
caller. Transaction tracing is not limited to HTTP requests - one can
also trace arbitrary applications, including backend apps, command-line
apps, batch jobs like MapReduce, etc.  Such tracing makes it possible to
find performance bottlenecks in running code, whether in production or
some other environment.

Starting with version 1.24.10, the SPM client provides ability to track
application transactions, thus making it easier to find bottleneck in
running applications and troubleshoot performance issues.

[![](https://sematext.files.wordpress.com/2015/08/appmap1_annotated.png)](http://blog.sematext.com/2015/08/06/introducing-appmap/)

**Notes:**

  - Transaction Tracing requires SPM monitor running in [embedded mode](/monitoring/spm-monitor-javaagent).
  - Enabling/disabling the tracing agent requires SPM monitor restart,
    which means it requires the restart of the application running the
    embedded SPM monitor.
  - Enabling transaction traces adds only about 1% CPU overhead.
  - Transaction Tracing is different from [On Demand Profiling](/monitoring/on-demand-profiling).

**Resources**:

  - <http://blog.sematext.com/2015/08/03/transaction-tracing-performance-monitoring/>
  - <http://blog.sematext.com/2015/08/05/transaction-tracing-reports/>
  - <http://blog.sematext.com/2015/08/06/introducing-appmap/>
