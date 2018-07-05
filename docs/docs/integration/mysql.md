## Integration

- Instructions: [https://apps.sematext.com/ui/howto/MySQL/overview](https://apps.sematext.com/ui/howto/MySQL/overview)

## Metrics

Metric Name | Key | Agg | Type | Description
--- | --- | --- | --- | ---
opened table definitions | mysql.tables.definition.opened | Sum | Long | <b>Opened_table_definitions</b>: The number of .frm files that have been cached
created tmp tables | mysql.tables.tmp | Sum | Long | <b>Created_tmp_tables</b>: The number of internal temporary tables created by the server while executing statements
flush commands | mysql.tables.flushes | Sum | Long | <b>Flush_commands</b>: The number of times the server flushes tables, whether because a user executed a FLUSH TABLES statement or due to                            internal server operation
table locks waited | mysql.tables.locks.waited | Sum | Long | <b>Table_locks_waited</b>: The number of times that a request for a table lock could not be granted immediately and a wait was needed.                           If this is high and you have performance problems, you should first optimize your queries, and then either                           split your table or tables or use replication
opened tables | mysql.tables.opened | Sum | Long | <b>Opened_tables</b>: The number of tables that have been opened. If Opened_tables is big, your table_open_cache value is probably too small
table definition cache | mysql.tables.definition.cache | Avg | Long | <b>table_definition_cache</b>: The number of table definitions (from .frm files) that can be stored in the definition cache. If you                             use a large number of tables, you can create a large table definition cache to speed up opening of                             tables. This variable was added in MySQL 5.1.3
open table definitions | mysql.tables.definition.open | Avg | Long | <b>Open_table_definitions</b>: The number of cached .frm files
table open cache | mysql.tables.cache | Avg | Long | <b>table_open_cache</b>: The number of open tables for all threads. Increasing this value increases the number of file descriptors                            that mysqld requires. You can check whether you need to increase the table cache by checking the Opened_tables                            status variable
created tmp disk tables | mysql.tables.tmp.disk | Sum | Long | <b>Created_tmp_disk_tables</b>: The number of internal on-disk temporary tables created by the server while executing statements.                            If an internal temporary table is created initially as an in-memory table but becomes too large,                             MySQL automatically converts it to an on-disk table
open tables | mysql.tables.open | Avg | Long | <b>Open_tables</b>: The number of tables that are open
table locks immediate | mysql.tables.locks.immediate | Sum | Long | <b>Table_locks_immediate</b>: The number of times that a request for a table lock could be granted immediately
innodb buffer pool bytes dirty | mysql.innodb.buffer.bytes.dirty | Avg | Long | <b>Innodb_buffer_pool_bytes_dirty</b>: The total current number of bytes held in dirty pages in the InnoDB buffer pool
innodb buffer pool reads | mysql.innodb.buffer.reads | Sum | Long | <b>Innodb_buffer_pool_reads</b>: The number of logical reads that InnoDB could not satisfy from the buffer pool, and had to read directly from the disk
innodb buffer pool pages free | mysql.innodb.buffer.pages.free | Avg | Long | <b>Innodb_buffer_pool_pages_free</b>: The number of free pages in the InnoDB buffer pool
innodb buffer pool pages total | mysql.innodb.buffer.pages | Avg | Long | <b>Innodb_buffer_pool_pages_total</b>: The total size of the InnoDB buffer pool, in pages
innodb buffer pool read ahead evicted | mysql.innodb.buffer.read.ahead.evicted | Sum | Long | <b>Innodb_buffer_pool_read_ahead_evicted</b>: The number of pages read into the InnoDB buffer pool by the read-ahead background thread that were subsequently evicted without having been accessed by queries.                             This variable was added in MySQL 5.1.41
innodb buffer pool pages dirty | mysql.innodb.buffer.pages.dirty | Avg | Long | <b>Innodb_buffer_pool_pages_dirty</b>: The current number of dirty pages in the InnoDB buffer pool
innodb buffer pool write requests | mysql.innodb.buffer.write.requests | Sum | Long | <b>Innodb_buffer_pool_write_requests</b>: The number writes done to the InnoDB buffer pool
innodb buffer pool pages misc | mysql.innodb.buffer.pages.misc | Avg | Long | <b>Innodb_buffer_pool_pages_misc</b>: The number of pages in the InnoDB buffer pool that are busy because they have been allocated for administrative overhead, such as row locks or the adaptive hash index
innodb buffer pool pages flushed | mysql.innodb.buffer.pages.flushed | Sum | Long | <b>Innodb_buffer_pool_pages_flushed</b>: The number of requests to flush pages from the InnoDB buffer pool
innodb buffer pool size | mysql.innodb.buffer.size | Avg | Long | <b>innodb_buffer_pool_size</b>: The size in bytes of the buffer pool, the memory area where InnoDB caches table and index data
innodb buffer pool read ahead | mysql.innodb.buffer.read.ahead | Sum | Long | <b>Innodb_buffer_pool_read_ahead</b>: The number of pages read into the InnoDB buffer pool by the read-ahead background thread. This variable was added in MySQL 5.1.41
innodb buffer pool read ahead seq | mysql.innodb.buffer.read.ahead.seq | Sum | Long | <b>Innodb_buffer_pool_read_ahead_seq</b>: The number of sequential read-aheads initiated by InnoDB. This happens when InnoDB does a sequential full table scan
innodb buffer pool bytes data | mysql.innodb.buffer.bytes.data | Avg | Long | <b>Innodb_buffer_pool_bytes_data</b>: The total number of bytes in the InnoDB buffer pool containing data. The number includes both dirty and clean pages
innodb buffer pool read ahead rnd | mysql.innodb.buffer.read.ahead.rnd | Sum | Long | <b>Innodb_buffer_pool_read_ahead_rnd</b>: The number of “random” read-aheads initiated by InnoDB. This happens when a query scans a large portion of a table but in random order
innodb buffer pool wait free | mysql.innodb.buffer.wait.free | Sum | Long | <b>Innodb_buffer_pool_wait_free</b>: Normally, writes to the InnoDB buffer pool happen in the background. However, if it is necessary to read or create a page and no clean pages are available,                            it is also necessary to wait for pages to be flushed first. This counter counts instances of these waits. If the buffer pool size has been set properly,                            this value should be small.
innodb buffer pool read requests | mysql.innodb.buffer.read.requests | Sum | Long | <b>Innodb_buffer_pool_read_requests</b>: The number of logical read requests
innodb buffer pool pages data | mysql.innodb.buffer.pages.data | Avg | Long | <b>Innodb_buffer_pool_pages_data</b>: The number of pages in the InnoDB buffer pool containing data. The number includes both dirty and clean pages
innodb read ahead threshold | mysql.innodb.buffer.read.ahead.threshold | Avg | Long | <b>innodb_read_ahead_threshold</b>: Controls the sensitivity of linear read-ahead that InnoDB uses to prefetch pages into the buffer pool. The permissible range of values is 0 to 64. The default                             is 56: InnoDB must read at least 56 pages sequentially from an extent to initiate an asynchronous read for the following extent
innodb row lock waits | mysql.innodb.lock.waits | Sum | Long | <b>Innodb_row_lock_waits</b>: The number of times a row lock had to be waited for
innodb data pending writes | mysql.innodb.data.writes.pending | Avg | Long | <b>Innodb_data_pending_writes</b>: The current number of pending writes
innodb page size | mysql.innodb.pages.size | Avg | Long | <b>Innodb_page_size</b>: The compiled-in InnoDB page size (default 16KB)
innodb pages written | mysql.innodb.pages.written | Sum | Long | <b>Innodb_pages_written</b>: The number of pages written
innodb data read | mysql.innodb.data.read | Sum | Long | <b>Innodb_data_read</b>: The amount of data read
innodb os log pending writes | mysql.innodb.log.writes.pending | Avg | Long | <b>Innodb_os_log_pending_writes</b>: The number of pending log file writes
innodb pages read | mysql.innodb.pages.read | Sum | Long | <b>Innodb_pages_read</b>: The number of pages read
innodb row lock time avg | mysql.innodb.lock.acquiring.time.avg | Avg | Double | <b>Innodb_row_lock_time_avg</b>: The average time to acquire a row lock
innodb log write requests | mysql.innodb.log.write.requests | Sum | Long | <b>Innodb_log_write_requests</b>: The number of log write requests
innodb log buffer size | mysql.innodb.log.buffer.size | Avg | Long | <b>innodb_log_buffer_size</b>: The size in bytes of the buffer that InnoDB uses to write to the log files on disk. The default value is 8MB.                             A large log buffer enables large transactions to run without a need to write the log to disk before the                             transactions commit. Thus, if you have transactions that update, insert, or delete many rows, making the log                             buffer larger saves disk I/O
innodb data reads | mysql.innodb.data.reads | Sum | Long | <b>Innodb_data_reads</b>: The number of data reads
innodb data pending reads | mysql.innodb.data.reads.pending | Avg | Long | <b>Innodb_data_pending_reads</b>: The current number of pending reads
innodb log file size | mysql.innodb.log.file.size | Avg | Long | <b>innodb_log_file_size</b>: The size in bytes of each log file in a log group
innodb os log pending fsyncs | mysql.innodb.log.fsyncs.pending | Avg | Long | <b>Innodb_os_log_pending_fsyncs</b>: The number of pending log file fsync() operations
innodb log writes | mysql.innodb.log.writes | Sum | Long | <b>Innodb_log_writes</b>: The number of physical writes to the log file
innodb pages created | mysql.innodb.pages.created | Sum | Long | <b>Innodb_pages_created</b>: The number of pages created
innodb row lock time max | mysql.innodb.lock.acquiring.time.max | Avg | Long | <b>Innodb_row_lock_time_max</b>: The maximum time to acquire a row lock
innodb data written | mysql.innodb.data.written | Sum | Long | <b>Innodb_data_written</b>: The amount of data written in bytes
innodb data fsyncs | mysql.innodb.data.fsyncs | Sum | Long | <b>Innodb_data_fsyncs</b>: The number of fsync() operations
innodb row lock current waits | mysql.innodb.lock.waiting | Avg | Long | <b>Innodb_row_lock_current_waits</b>: The number of row locks currently being waited for
innodb data pending fsyncs | mysql.innodb.data.fsyncs.pending | Avg | Long | <b>Innodb_data_pending_fsyncs</b>: The current number of pending fsync() operations
innodb row lock time | mysql.innodb.lock.acquiring.time | Sum | Long | <b>Innodb_row_lock_time</b>: The total time spent in acquiring row locks
innodb os log written | mysql.innodb.log.written | Sum | Long | <b>Innodb_os_log_written</b>: The number of bytes written to the log file
innodb dblwr pages written | mysql.innodb.pages.written.dblwr | Sum | Long | <b>Innodb_dblwr_pages_written</b>: The number of pages that have been written for doublewrite operations
innodb dblwr writes | mysql.innodb.pages.writes.dblwr | Sum | Long | <b>Innodb_dblwr_writes</b>: The number of doublewrite operations that have been performed
innodb log waits | mysql.innodb.log.waits | Sum | Long | <b>Innodb_log_waits</b>: The number of times that the log buffer was too small and a wait was required for it to be flushed before continuing
innodb rows inserted | mysql.innodb.rows.inserted | Sum | Long | <b>Innodb_rows_inserted</b>: The number of rows inserted into InnoDB tables
innodb os log fsyncs | mysql.innodb.log.fsyncs | Sum | Long | <b>Innodb_os_log_fsyncs</b>: The number of fsync() writes done to the log file
innodb rows deleted | mysql.innodb.rows.deleted | Sum | Long | <b>Innodb_rows_deleted</b>: The number of rows deleted from InnoDB tables
innodb data writes | mysql.innodb.data.writes | Sum | Long | <b>Innodb_data_writes</b>: The number of data writes
innodb rows read | mysql.innodb.rows.read | Sum | Long | <b>Innodb_rows_read</b>: The number of rows read from InnoDB tables
innodb rows updated | mysql.innodb.rows.updated | Sum | Long | <b>Innodb_rows_updated</b>: The number of rows updated in InnoDB tables
select range check | mysql.queries.select.join.check | Sum | Long | <b>Select_range_check</b>: The number of joins without keys that check for key usage after each row. If this is not 0,                             you should carefully check the indexes of your tables
queries | mysql.queries | Sum | Long | <b>Queries</b>: The number of statements executed by the server. This variable includes statements executed within stored programs, unlike the Questions variable.                            It does not count COM_PING or COM_STATISTICS commands. This variable was added in MySQL 5.0.76
select scan | mysql.queries.select.join.scan | Sum | Long | <b>Select_scan</b>: The number of joins that did a full scan of the first table
slow queries | mysql.queries.slow | Sum | Long | <b>Slow_queries</b>: The number of queries that have taken more than long_query_time seconds. This counter increments regardless of whether the slow query log is enabled
not flushed delayed rows | mysql.queries.insert.delayed.queued | Avg | Long | <b>Not_flushed_delayed_rows</b>: The number of rows waiting to be written in INSERT DELAYED queues
sort scan | mysql.queries.sort.scan | Sum | Long | <b>Sort_scan</b>: The number of sorts that were done by scanning the table
select full join | mysql.queries.select.join.full | Sum | Long | <b>Select_full_join</b>: The number of joins that perform table scans because they do not use indexes.                             If this value is not 0, you should carefully check the indexes of your tables
sort rows | mysql.queries.sort.rows | Sum | Long | <b>Sort_rows</b>: The number of sorted rows
sort merge passes | mysql.queries.sort.mergepasses | Sum | Long | <b>Sort_merge_passes</b>: The number of merge passes that the sort algorithm has had to do. If this value is large,                             you should consider increasing the value of the sort_buffer_size system variable
select full range join | mysql.queries.select.join.range.full | Sum | Long | <b>Select_full_range_join</b>: The number of joins that used a range search on a reference table
select range | mysql.queries.select.join.range | Sum | Long | <b>Select_range</b>: The number of joins that used ranges on the first table. This is normally not a                            critical issue even if the value is quite large
delayed insert threads | mysql.queries.insert.delayed.threads | Avg | Long | <b>Delayed_insert_threads</b>: The number of INSERT DELAYED handler threads in use
max prepared stmt count | mysql.queries.prepared.stmts.max | Avg | Long | <b>max_prepared_stmt_count</b>: This variable limits the total number of prepared statements in the server. (The sum of the number of prepared statements across all sessions)
long query time | mysql.queries.slow.latency | Avg | Double | <b>long_query_time</b>: If a query takes longer than this many seconds, the server increments the Slow_queries status variable. If you are using the --log-slow-queries option,                           the query is logged to the slow query log file. This value is measured in real time, not CPU time, so a query that is under the threshold on a lightly                           loaded system might be above the threshold on a heavily loaded one
max length for sort data | mysql.queries.sort.config.maxlength | Avg | Long | <b>max_length_for_sort_data</b>: The cutoff on the size of index values that determines which filesort algorithm to use
delayed errors | mysql.queries.insert.delayed.errors | Sum | Long | <b>Delayed_errors</b>: The number of rows written with INSERT DELAYED for which some error occurred (probably duplicate key)
sort range | mysql.queries.sort.range | Sum | Long | <b>Sort_range</b>: The number of sorts that were done using ranges
sort buffer size | mysql.queries.sort.config.buffer | Avg | Long | <b>sort_buffer_size</b>: Each session that needs to do a sort allocates a buffer of this size. sort_buffer_size is not specific to any storage engine and applies                            in a general manner for optimization. If you see many Sort_merge_passes per second, you can consider increasing the sort_buffer_size value                             to speed up ORDER BY or GROUP BY operations that cannot be improved with query optimization or improved indexing
questions | mysql.queries.client | Sum | Long | <b>Questions</b>: The number of statements executed by the server. As of MySQL 5.0.72, this includes only statements sent to the server by clients and no longer                             includes statements executed within stored programs, unlike the Queries variable. This variable does not count COM_PING, COM_STATISTICS,                             COM_STMT_PREPARE, COM_STMT_CLOSE, or COM_STMT_RESET commands
prepared stmt count | mysql.queries.prepared.stmts | Avg | Long | <b>Prepared_stmt_count</b>: The current number of prepared statements. (The maximum number of statements is given by the max_prepared_stmt_count system variable)
max sort length | mysql.queries.sort.config.sortlength | Avg | Long | <b>max_sort_length</b>: The number of bytes to use when sorting data values. Only the first max_sort_length bytes of each value are used; the rest are ignored
delayed writes | mysql.queries.insert.delayed.writes | Sum | Long | <b>Delayed_writes</b>: The number of INSERT DELAYED rows written
binlog stmt cache size | mysql.binlog.stmt.cache.size | Avg | Long | <b>binlog_stmt_cache_size</b>: Beginning with MySQL 5.5.9, this variable determines the size of the cache for the binary log to hold                             nontransactional statements issued during a transaction. In MySQL 5.5.3 and later, separate binary log                             transaction and statement caches are allocated for each client if the server supports any transactional                             storage engines and if the server has the binary log enabled (--log-bin option). If you often use large                             nontransactional statements during transactions, you can increase this cache size to get more performance
binlog cache use | mysql.binlog.cache.use | Sum | Long | <b>Binlog_cache_use</b>: The number of transactions that used the temporary binary log cache
slave retried transactions | mysql.repl.slave.transactions.retired | Sum | Long | <b>Slave_retried_transactions</b>: The total number of times since startup that the replication slave SQL thread has retried transactions
slave sql running | mysql.repl.slave.sql | Avg | Double | <b>Slave_SQL_Running</b>: Whether the SQL thread is started. Value 1 means YES, value 0 means NO.                            Decimal value between 0 and 1 means that in monitored time period SQL thread was at some points running and at other points not running.
binlog stmt cache disk use | mysql.binlog.stmt.cache.use.disk | Sum | Long | <b>Binlog_stmt_cache_disk_use</b>: The number of nontransaction statements that used the binary log statement cache but that exceeded the                            value of binlog_stmt_cache_size and used a temporary file to store those statements
slave received heartbeats | mysql.repl.slave.heartbeats.received | Sum | Long | <b>Slave_received_heartbeats</b>: This counter increments with each replication heartbeat received by a replication slave since the last time that the slave was restarted or reset,                            or a CHANGE MASTER TO statement was issued
seconds behind master | mysql.repl.slave.behind.seconds | Avg | Long | <b>Seconds_Behind_Master</b>: This field is an indication of how “late” the slave is. In essence, this field measures the time difference                             in seconds between the slave SQL thread and the slave I/O thread. If the network connection between master and                            slave is fast, the slave I/O thread is very close to the master, so this field is a good approximation of how                            late the slave SQL thread is compared to the master. If the network is slow, this is not a good approximation
slave open temp tables | mysql.repl.slave.tables.temp.open | Avg | Long | <b>Slave_open_temp_tables</b>: The number of temporary tables that the slave SQL thread currently has open.                            If the value is greater than zero, it is not safe to shut down the slave
binlog cache size | mysql.binlog.cache.size | Avg | Long | <b>binlog_cache_size</b>: The size of the cache to hold the SQL statements for the binary log during a transaction. A binary log                             cache is allocated for each client if the server supports any transactional storage engines and if the server                            has the binary log enabled (--log-bin option). If you often use large, multiple-statement transactions, you                             can increase this cache size to get better performance
slave io running | mysql.repl.slave.io | Avg | Double | <b>Slave_IO_Running</b>: Whether the I/O thread is started and has connected successfully to the master. Value 1 means YES, value 0 means NO.                            Decimal value between 0 and 1 means that in monitored time period I/O thread was at some points running and at other points not running.
slave heartbeat period | mysql.repl.slave.heartbeats.period | Avg | Double | <b>Slave_heartbeat_period</b>: Shows the replication heartbeat interval on a replication slave
binlog cache disk use | mysql.binlog.cache.use.disk | Sum | Long | <b>Binlog_cache_disk_use</b>: The number of transactions that used the temporary binary log cache but that exceeded the value of                             binlog_cache_size and used a temporary file to store statements from the transaction
binlog stmt cache use | mysql.binlog.stmt.cache.use | Sum | Long | <b>Binlog_stmt_cache_use</b>: The number of nontransactional statements that used the binary log statement cache
thread cache size | mysql.threads.cached.allowed | Avg | Long | <b>thread_cache_size</b>: How many threads the server should cache for reuse
max connections | mysql.connections.allowed | Avg | Long | <b>max_connections</b>: The maximum permitted number of simultaneous client connections
opened files | mysql.files.my_open | Sum | Long | <b>Opened_files</b>: The number of files that have been opened with my_open()
created tmp files | mysql.files.tmp.created | Sum | Long | <b>Created_tmp_files</b>: How many temporary files mysqld has created
uptime | mysql.uptime | Avg | Long | <b>Uptime</b>: The number of seconds that the server has been up
max used connections | mysql.connections.concurrent.max | Avg | Long | <b>Max_used_connections</b>: The maximum number of connections that have been in use simultaneously since the server started
max user connections | mysql.connections.user.max | Avg | Long | <b>max_user_connections</b>: The maximum number of simultaneous connections permitted to any given MySQL user account
aborted connects | mysql.connections.failed | Sum | Long | <b>Aborted_connects</b>: The number of failed attempts to connect to the MySQL server
threads connected | mysql.threads.connected | Avg | Long | <b>Threads_connected</b>: The number of currently open connections
open files | mysql.files.open | Avg | Long | <b>Open_files</b>: The number of files that are open. This count includes regular files opened by the server
uptime since flush | mysql.uptime.sinceflush | Avg | Long | <b>Uptime_since_flush_status</b>: The number of seconds since the most recent FLUSH STATUS statement
slow launch threads | mysql.threads.slowlaunch | Sum | Long | <b>Slow_launch_threads</b>: The number of threads that have taken more than slow_launch_time seconds to create
threads created | mysql.threads.created | Sum | Long | <b>Threads_created</b>: The number of threads created to handle connections. If Threads_created is big, you may want to increase the thread_cache_size value
thread stack | mysql.threads.stack.size | Avg | Long | <b>thread_stack</b>: The stack size for each thread
threads running | mysql.threads.running | Avg | Long | <b>Threads_running</b>: The number of threads that are not sleeping
open streams | mysql.files.streams | Avg | Long | <b>Open_streams</b>: The number of streams that are open (used mainly for logging)
aborted clients | mysql.connections.aborted | Sum | Long | <b>Aborted_clients</b>: The number of connections that were aborted because the client died without closing the connection properly
threads cached | mysql.threads.cached | Avg | Long | <b>Threads_cached</b>: The number of threads in the thread cache
replace | mysql.commands.replace | Sum | Long | <b>Com_replace</b>: The number of times REPLACE command has been executed
drop user | mysql.commands.drop.user | Sum | Long | <b>Com_drop_user</b>: The number of times DROP USER command has been executed
update multi | mysql.commands.update.multi | Sum | Long | <b>Com_update_multi</b>: The number of times UPDATE command with multiple-table syntax has been executed
create table | mysql.commands.create.table | Sum | Long | <b>Com_create_table</b>: The number of times CREATE TABLE command has been executed
insert select | mysql.commands.insert.select | Sum | Long | <b>Com_insert_select</b>: The number of times INSERT with SELECT command has been executed
replace select | mysql.commands.replace.select | Sum | Long | <b>Com_replace_select</b>: The number of times REPLACE with SELECT command has been executed
drop table | mysql.commands.drop.table | Sum | Long | <b>Com_drop_table</b>: The number of times DROP TABLE command has been executed
update | mysql.commands.update | Sum | Long | <b>Com_update</b>: The number of times UPDATE command has been executed
delete | mysql.commands.delete | Sum | Long | <b>Com_delete</b>: The number of times DELETE command has been executed
rollback | mysql.commands.rollback | Sum | Long | <b>Com_rollback</b>: The number of times ROLLBACK command has been executed
rollback to savepoint | mysql.commands.replace.rollback.savepoint | Sum | Long | <b>Com_rollback_to_savepoint</b>: The number of times ROLLBACK TO SAVEPOINT command has been executed
drop DB | mysql.commands.drop.db | Sum | Long | <b>Com_drop_db</b>: The number of times DROP DATABASE command has been executed
create user | mysql.commands.create.user | Sum | Long | <b>Com_create_user</b>: The number of times CREATE USER command has been executed
create DB | mysql.commands.create.db | Sum | Long | <b>Com_create_db</b>: The number of times CREATE DATABASE command has been executed
insert | mysql.commands.insert | Sum | Long | <b>Com_insert</b>: The number of times INSERT command has been executed
commit | mysql.commands.commit | Sum | Long | <b>Com_commit</b>: The number of times COMMIT command has been executed
load | mysql.commands.load | Sum | Long | <b>Com_load</b>: The number of times LOAD command has been executed
delete multi | mysql.commands.delete.multi | Sum | Long | <b>Com_delete_multi</b>: The number of times DELETE command with multiple-table syntax has been executed
select | mysql.commands.select | Sum | Long | <b>Com_select</b>: The number of times SELECT command has been executed
free cache memory | mysql.cache.bytes.free | Avg | Long | <b>Qcache_free_memory</b>: The amount of free memory for the query cache
free blocks | mysql.cache.blocks.free | Avg | Long | <b>Qcache_free_blocks</b>: The number of free memory blocks in the query cache
inserts | mysql.cache.queries.inserts | Sum | Long | <b>Qcache_inserts</b>: The number of queries added to the query cache
queries not cached | mysql.cache.queries.noncached | Sum | Long | <b>Qcache_not_cached</b>: The number of noncached queries (not cacheable, or not cached due to the query_cache_type setting)
queries in cache | mysql.cache.queries.cached | Avg | Long | <b>Qcache_queries_in_cache</b>: The number of queries registered in the query cache
hits | mysql.cache.queries.hits | Sum | Long | <b>Qcache_hits</b>: The number of query cache hits
total blocks | mysql.cache.blocks | Avg | Long | <b>Qcache_total_blocks</b>: The total number of blocks in the query cache
lowmem prunes | mysql.cache.queries.prunes.lowmem | Sum | Long | <b>Qcache_lowmem_prunes</b>: The number of queries that were deleted from the query cache because of low memory
query cache size | mysql.cache.bytes | Avg | Long | <b>query_cache_size</b>: The amount of memory allocated for caching query results. The default value is 0, which disables the query cache
bytes received | mysql.traffic.rx.bytes | Sum | Long | <b>Bytes_received</b>: The number of bytes received from all clients
bytes sent | mysql.traffic.tx.bytes | Sum | Long | <b>Bytes_sent</b>: The number of bytes sent to all clients
key reads | mysql.myisam.key.read.blocks | Sum | Long | <b>Key_reads</b>: The number of physical reads of a key block from disk. If Key_reads is large, then your key_buffer_size value is probably too small
key buffer size | mysql.myisam.key.buffer | Avg | Long | <b>key_buffer_size</b>: Index blocks for MyISAM tables are buffered and are shared by all threads. key_buffer_size is the size of the buffer used for index blocks.                             The key buffer is also known as the key cache.  The value of this variable indicates the amount of memory requested. Internally, the server                             allocates as much memory as possible up to this amount, but the actual allocation might be less.
key writes | mysql.myisam.key.write.blocks | Sum | Long | <b>Key_writes</b>: The number of physical writes of a key block to disk
key cache age threshold | mysql.myisam.key.cache.age.threshold | Avg | Long | <b>key_cache_age_threshold</b>: This value controls the demotion of buffers from the hot sublist of a key cache to the warm sublist.                             Lower values cause demotion to happen more quickly
key read requests | mysql.myisam.key.read.reaquests | Sum | Long | <b>Key_read_requests</b>: The number of requests to read a key block from the cache
key blocks unused | mysql.myisam.key.blocks.unused | Avg | Long | <b>Key_blocks_unused</b>: The number of unused blocks in the key cache. You can use this value to determine how much of the key cache is in use
key blocks not flushed | mysql.myisam.key.blocks.unflushed | Avg | Long | <b>Key_blocks_not_flushed</b>: The number of key blocks in the key cache that have changed but have not yet been flushed to disk
key write requests | mysql.myisam.key.write.requests | Sum | Long | <b>Key_write_requests</b>: The number of requests to write a key block to the cache
key blocks used | mysql.myisam.key.blocks.used | Avg | Long | <b>Key_blocks_used</b>: The number of used blocks in the key cache. This value is a high-water mark that indicates the maximum number of blocks that have ever been in use at one time
key cache block size | mysql.myisam.key.cache.block.size | Avg | Long | <b>key_cache_block_size</b>: The size in bytes of blocks in the key cache
handler rollback | mysql.handler.rollback | Sum | Long | <b>Handler_rollback</b>: The number of requests for a storage engine to perform a rollback operation
handler read first | mysql.handler.read.first | Sum | Long | <b>Handler_read_first</b>: The number of times the first entry in an index was read. If this value is high, it                             suggests that the server is doing a lot of full index scans; for example, SELECT col1 FROM foo, assuming that col1 is indexed
handler read key | mysql.handler.read.key | Sum | Long | <b>Handler_read_key</b>: The number of requests to read a row based on a key. If this value is high, it is a                             good indication that your tables are properly indexed for your queries
handler read next | mysql.handler.read.next | Sum | Long | <b>Handler_read_next</b>: The number of requests to read the next row in key order. This value is incremented if                            you are querying an index column with a range constraint or if you are doing an index scan
handler savepoint | mysql.handler.savepoint | Sum | Long | <b>Handler_savepoint</b>: The number of requests for a storage engine to place a savepoint
handler delete | mysql.handler.delete | Sum | Long | <b>Handler_delete</b>: The number of times that rows have been deleted from tables
handler write | mysql.handler.write | Sum | Long | <b>Handler_write</b>: The number of requests to insert a row in a table
handler update | mysql.handler.update | Sum | Long | <b>Handler_update</b>: The number of requests to update a row in a table
handler prepare | mysql.handler.prepare | Sum | Long | <b>Handler_prepare</b>: A counter for the prepare phase of two-phase commit operations
handler read rnd | mysql.handler.read.rnd | Sum | Long | <b>Handler_read_rnd</b>: The number of requests to read a row based on a fixed position. This value is high if you are doing a lot of                             queries that require sorting of the result. You probably have a lot of queries that require MySQL to scan                             entire tables or you have joins that do not use keys properly
handler read last | mysql.handler.read.last | Sum | Long | <b>Handler_read_last</b>: The number of requests to read the last key in an index. With ORDER BY, the server will                             issue a first-key request followed by several next-key requests, whereas with With ORDER BY DESC,                             the server will issue a last-key request followed by several previous-key requests. This variable was added in MySQL 5.6.1
handler savepoint rollback | mysql.handler.savepoint.rollback | Sum | Long | <b>Handler_savepoint_rollback</b>: The number of requests for a storage engine to roll back to a savepoint
handler discover | mysql.handler.discover | Sum | Long | <b>Handler_discover</b>: The MySQL server can ask the NDBCLUSTER storage engine if it knows about a table with a                             given name. This is called discovery. Handler_discover indicates the number of times that                            tables have been discovered using this mechanism
handler read prev | mysql.handler.read.prev | Sum | Long | <b>Handler_read_prev</b>: The number of requests to read the previous row in key order. This read method is mainly used to optimize ORDER BY ... DESC
handler read rnd next | mysql.handler.read.rnd.next | Sum | Long | <b>Handler_read_rnd_next</b>: The number of requests to read the next row in the data file. This value is high if you are doing a lot of                             table scans. Generally this suggests that your tables are not properly indexed or that your queries are not                            written to take advantage of the indexes you have
handler commit | mysql.handler.commit | Sum | Long | <b>Handler_commit</b>: The number of internal COMMIT statements
