# Kafka cheat sheet

## Zookeeper
TL;DR Zookeeper is the coordination service for distributed apps
> ZooKeeper is a high-performance coordination service for distributed applications. It exposes common services - such as naming, configuration management, synchronization, and group services - in a simple interface so you don't have to write them from scratch. You can use it off-the-shelf to implement consensus, group management, leader election, and presence protocols. And you can build on it for your own, specific needs.
## Kafka
is the distributed, persistent (for configurable lengths of time), event-driven messaging system.
Emits events and topics; publish-subscribe to use or consume events.
In Confluent Platform, realtime streaming events are stored in a Kafka topic, which is essentially an append-only log.
A **topic** is the most fundamental unit of organisation in Kafka, analogous to a db table.
### install
installed under user home (i.e. downloading and extracting confluent-7.2.0.tar.gz)
install datagen source connector via confluent hub
> confluent-hub install confluentinc/kafka-connect-datagen:0.5.3
### start kafka
Needs `CONFLUENT_HOME` environment variable (and append to path's `/bin`), i.e.
> export CONFLUENT_HOME=~/confluent-7.2.0

> export PATH=$PATH:$CONFLUENT_HOME/bin

Needs `Java 8` or `Java 11`, so... use a combination of `/usr/libexec/java_home` and `JAVA_HOME` environment variable. i.e.
> export JAVA_HOME=`/usr/libexec/java_home -v 11` -------// echo $JAVA_HOME  should show Java v11 now  

Then from home directory
> joseph.jobbings@JosephJobbingss-MacBook-Pro ~ % confluent local services start          

```bash
The local commands are intended for a single-node development environment only, NOT for production usage. https://docs.confluent.io/current/cli/index.html

Using CONFLUENT_CURRENT: /var/folders/p1/99852nrd7xn0fjqkp8gfk6rh0000gn/T/confluent.565137
Starting ZooKeeper
ZooKeeper is [UP]
Kafka is [UP]
Starting Schema Registry
Schema Registry is [UP]
Starting Kafka REST
Kafka REST is [UP]
Starting Connect
Connect is [UP]
Starting ksqlDB Server
ksqlDB Server is [UP]
Starting Control Center
Control Center is [UP]
```

### stop kafka
> confluent local services stop


### output logs
> bin/confluent local services connect log  
> bin/confluent local services connect log -f  # --------tail
Confirm log levels: curl -Ss http://localhost:8083/admin/loggers | jq


### Control centre
http://localhost:9021 - in local install.  use the control centre to add a topic (i.e. a grouping of related events).  n.b. topic names ARE case sensitive.  in the getting started guide, later on you create data generators that produce data to these topics.
connect topics to datagen source connector (for development illustrations)

## Concepts
(Stream and table names are not case-sensitive).
### Stream
immutable, append-only events (or 'rows') - no delete, no update.
A stream is a an ***immutable***, append-only collection that represents a series of historical facts, or events. Once a row is inserted into a stream, the row can never change. You can append new rows at the end of the stream, but you can’t update or delete existing rows.
### Table
a ***mutable*** collection that models change over time.   Table uses row keys to display the most recent data for each key. All but the newest rows for each key are deleted periodically. Also, each row has a timestamp, so you can define a **windowed** table which enables controlling how to group records that have the same key for stateful operations – like aggregations and joins – into time spans. Windows are tracked by record key.  
Together, streams and tables comprise a fully realized database.
> When you register a stream or a table on a topic, you can use the stream/table in SQL statements.
### queries to process data
* `Transient query`: a non-persistent, client-side query that you terminate manually or with a LIMIT clause. A transient query doesn’t create a new topic.
* `Persistent query`: a server-side query that outputs a new stream or table that’s backed by a new topic. It runs until you issue the TERMINATE statement. The syntax for a persistent query uses the CREATE STREAM AS SELECT or CREATE TABLE AS SELECT statements.  
Following query looks like SQL - `JOIN ... the table and stream` e.g.
```sql
CREATE STREAM user_pageviews
  AS SELECT users_table.id AS userid, pageid, regionid, gender
    FROM pageviews_stream
    LEFT JOIN users_table ON pageviews_stream.userid = users_table.id
EMIT CHANGES;
```
Filter query - `WHERE` clause,
```sql
CREATE STREAM pageviews_region_like_89
  WITH (KAFKA_TOPIC='pageviews_filtered_r8_r9', VALUE_FORMAT='AVRO')
    AS SELECT * FROM user_pageviews
    WHERE regionid LIKE '%_8' OR regionid LIKE '%_9'
EMIT CHANGES;
```
windowed view,
```sql
CREATE TABLE pageviews_per_region_89 WITH (KEY_FORMAT='JSON')
  AS SELECT userid, gender, regionid, COUNT(*) AS numusers
    FROM pageviews_region_like_89
    WINDOW TUMBLING (SIZE 30 SECOND)
    GROUP BY userid, gender, regionid
    HAVING COUNT(*) > 1
EMIT CHANGES;
```
* `Push query`: A query that produces results continuously to a subscription. The syntax for a push query uses the EMIT CHANGES keyword. Push queries can be transient or persistent.
* `Pull query`: A query that gets a result as of “now”, like a query against a traditional relational database. A pull query runs once and returns the current state of a table. Tables are updated incrementally as new events arrive, so pull queries run with predictably low latency. Pull queries are always transient.  
You can get the current state of a table by using a pull query, which returns rows for a specific key at the time you issue the query. A pull query runs once and terminates.
```sql
SELECT * FROM pageviews_per_region_89
  WHERE userid = 'User_1' AND gender='FEMALE' AND regionid='Region_9';
```
### Topic
By default, the Datagen Source Connector produces data in `Avro` format, (which defines the schemas of pageviews and users messages).
#### Schema Registry
ensures that messages sent to your cluster have the correct schema. For more information, see Schema Registry Overview.

### [ksqlDB](https://ksqldb.io)

use the ksqldb editor in the Confluent control centre to
1. run a query that creates a stream over a topic
1. use a SELECT query to confirm data is moving through the   
__alternatively (or additionally)__
1. create a table over a topic

## Confluent local
```
confluent local services [command]

Available Commands:
  connect         Manage Connect.
  control-center  Manage Control Center.
  kafka           Manage Apache Kafka®.
  kafka-rest      Manage Kafka REST.
  ksql-server     Manage ksqlDB Server.
  list            List all Confluent Platform services.
  schema-registry Manage Schema Registry.
  start           Start all Confluent Platform services.
  status          Check the status of all Confluent Platform services.
  stop            Stop all Confluent Platform services.
  top             View resource usage for all Confluent Platform services.
  zookeeper       Manage Apache ZooKeeper™.
```
e.g.s
> confluent local services list
> confluent local services schema-registry
> confluent local services kafka consume dsv // consume new msgs from topic
> confluent local services kafka consume mytopic1 --value-format avro --from-beginning

Consume newly arriving non-Avro data from a topic called `mytopic2` on a development Kafka cluster on localhost.
>  confluent local services kafka consume mytopic2

### consume options
--cloud                       Consume from Confluent Cloud.
     --config string               Change the Confluent Cloud configuration file. (default "/Users/joseph.jobbings/.confluent/config")
     --value-format string         Format output data: avro, json, or protobuf.

     --bootstrap-server string     The server(s) to connect to. The broker list string has the form HOST1:PORT1,HOST2:PORT2.
     --consumer-property string    A mechanism to pass user-defined properties in the form key=value to the consumer.
     --consumer.config string      Consumer config properties file. Note that [consumer-property] takes precedence over this config.
     --enable-systest-events       Log lifecycle events of the consumer in addition to logging consumed messages. (This is specific for system tests.)
     --formatter string            The name of a class to use for formatting kafka messages for display. (default "kafka.tools.DefaultMessageFormatter")
     --from-beginning              If the consumer does not already have an established offset to consume from, start with the earliest message present in the log rather than the latest message.
     --group string                The consumer group id of the consumer.
     --isolation-level string      Set to read_committed in order to filter out transactional messages which are not committed. Set to read_uncommitted to read all messages. (default "read_uncommitted")
     --key-deserializer string     
     --max-messages int            The maximum number of messages to consume before exiting. If not set, consumption is continual.
     --offset string               The offset id to consume from (a non-negative number), or "earliest" which means from beginning, or "latest" which means from end. (default "latest")
     --partition int               The partition to consume from. Consumption starts from the end of the partition unless "--offset" is specified.
     --property stringArray        The properties to initialize the message formatter. Default properties include:
                                     print.timestamp=true|false
                                     print.key=true|false
                                     print.value=true|false
                                     key.separator=<key.separator>
                                     line.separator=<line.separator>
                                     key.deserializer=<key.deserializer>
                                     value.deserializer=<value.deserializer>
                                   Users can also pass in customized properties for their formatter; more specifically, users can pass in properties keyed with "key.deserializer." and "value.deserializer." prefixes to configure their deserializers.
     --skip-message-on-error       If there is an error when processing a message, skip it instead of halting.
     --timeout-ms int              If specified, exit if no messages are available for consumption for the specified interval.
     --value-deserializer string   
     --whitelist string            Regular expression specifying whitelist of topics to include for consumption.


### produce options
$ confluent local services kafka produce mytopic1 --value-format avro --property value.schema='{"type":"record","name":"myrecord","fields":[{"name":"f1","type":"string"}]}'

with --file--
confluent kafka topic produce orders-avro --value-format avro --schema orders-avro-schema.json

with --file and key--
confluent kafka topic produce orders-avro --value-format avro --schema orders-avro-schema.json --parse-key --delimiter ":"

Produce non-Avro data to a topic called `mytopic2` on a development Kafka cluster on localhost:

$ confluent local produce mytopic2
--cloud                            Consume from Confluent Cloud.
    --config string                    Change the Confluent Cloud configuration file. (default "/Users/joseph.jobbings/.confluent/config")
    --value-format string              Format output data: avro, json, or protobuf.

    --batch-size int                   Number of messages to send in a single batch if they are not being sent synchronously. (default 200)
    --bootstrap-server string          The server(s) to connect to. The broker list string has the form HOST1:PORT1,HOST2:PORT2.
    --compression-codec string         The compression codec: either "none", "gzip", "snappy", "lz4", or "zstd". If specified without value, then it defaults to "gzip".
    --line-reader string               The class name of the class to use for reading lines from stdin. By default each line is read as a separate message. (default "kafka.tools.ConsoleProducer$LineMessageReader")
    --max-block-ms int                 The max time that the producer will block for during a send request. (default 60000)
    --max-memory-bytes int             The total memory used by the producer to buffer records waiting to be sent to the server. (default 33554432)
    --max-partition-memory-bytes int   The buffer size allocated for a partition. When records are received which are small than this size, the producer will attempt to optimistically group them together until this size is reached. (default 16384)
    --message-send-max-retries int     This property specifies the number of retries before the producer gives up and drops this message. Brokers can fail receiving a message for multiple reasons, and being unavailable transiently is just one of them. (default 3)
    --metadata-expiry-ms int           The amount of time in milliseconds before a forced metadata refresh. This will occur independent of any leadership changes. (default 300000)
    --producer-property string         A mechanism to pass user-defined properties in the form key=value to the producer.
    --producer.config string           Producer config properties file. Note that [producer-property] takes precedence over this config.
    --property stringArray             A mechanism to pass user-defined properties in the form key=value to the message reader. This allows custom configuration for a user-defined message reader. Default properties include:
                                        parse.key=true|false
                                        key.separator=<key.separator>
                                        ignore.error=true|false
    --request-required-acks string     The required ACKs of the producer requests. (default 1)
    --request-timeout-ms int           The ACK timeout of the producer requests. Value must be positive. (default 1500)
    --retry-backoff-ms int             Before each retry, the producer refreshes the metadata of relevant topics. Since leader election takes a bit of time, this property specifies the amount of time that the producer waits before refreshing the metadata. (default 100)
    --socket-buffer-size int           The size of the TCP RECV size. (default 102400)
    --sync                             If set, message send requests to brokers arrive synchronously.
    --timeout int                      If set and the producer is running in asynchronous mode, this gives the maximum amount of time a message will queue awaiting sufficient batch size. The value is given in ms. (default 1000)

Global Flags:
-h, --help            Show help for this command.
-v, --verbose count   Increase verbosity (-v for warn, -vv for info, -vvv for debug, -vvvv for trace).

#### with dsv example topic
```bash
confluent local services kafka consume --from-beginning
confluent local services kafka produce dsv --property parse.key=true,key.separator='='
# then enter messages to "produce"
"980-1234567769"={"amount":2.22,"ean":1234567769,"location":"980"}
"980-1234567769"={"amount":223,"ean":1234567769,"location":"980"}
```

## produce to cloud

  $ confluent local produce mytopic2

Flags:
      --cloud                            Consume from Confluent Cloud.
      --config string                    Change the Confluent Cloud configuration file. (default "/Users/joseph.jobbings/.confluent/config")

## consume from cloud?
???


## Connect
where are configs stored?

Downloading component Debezium PostgreSQL CDC Connector 2.0.1, provided by Debezium Community from Confluent Hub and installing into /Users/joseph.jobbings/confluent-7.2.0/share/confluent-hub-components
Detected Worker's configs:
  1. Standard: /Users/joseph.jobbings/confluent-7.2.0/etc/kafka/connect-distributed.properties
  2. Standard: /Users/joseph.jobbings/confluent-7.2.0/etc/kafka/connect-standalone.properties
  3. Standard: /Users/joseph.jobbings/confluent-7.2.0/etc/schema-registry/connect-avro-distributed.properties
  4. Standard: /Users/joseph.jobbings/confluent-7.2.0/etc/schema-registry/connect-avro-standalone.properties
  5. Based on CONFLUENT_CURRENT: /var/folders/p1/99852nrd7xn0fjqkp8gfk6rh0000gn/T/confluent.878496/connect/connect.properties
  6. Used by Connect process with PID 45983: /var/folders/p1/99852nrd7xn0fjqkp8gfk6rh0000gn/T/confluent.878496/connect/connect.properties
