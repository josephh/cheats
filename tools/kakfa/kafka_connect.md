# Kafka Connect

## Connectors (Confluent)
### Sink
-> send data into a database from Kafka topics

### Source
-> send data from a database to Kafka topics


## Local
where is Kafka writing output to etc?
`confluent local current`
> e.g. /var/folders/p1/99852nrd7xn0fjqkp8gfk6rh0000gn/T/confluent.878496

## connector plugins?
`curl -H "Accept:application/json" localhost:8083/connector-plugins | jq`
or (local cli)
`confluent local services connect connector list`

## load plugin
...with json property input
`curl -i -X POST -H "Accept:application/json" -H  "Content-Type:application/json" http://localhost:8083/connectors/ -d @/Users/joseph.jobbings/confluent-7.2.0/etc/kafka/connect-fluent-sink.json`
... with  cli
`connect-standalone ./etc/schema-registry/connect-avro-standalone.properties  ./etc/kafka/connect-debezium-source.properties`
... or
`confluent local services connect connector load fluent-sink -c /Users/joseph.jobbings/confluent-7.2.0/etc/kafka/connect-fluent-sink.properties` # file format can also be json on command line

## update plugin config
`curl PUT -H "Accept:application/json" -H  "Content-Type:application/json" http://localhost:8083/connectors/fluent-sink-connector/config -d @/Users/joseph.jobbings/confluent-7.2.0/etc/kafka/connect-fluent-sink.json`

## connector status?
`curl localhost:8083/connectors| jq`
`curl localhost:8083/connectors/fluent-sink-connector/status | jq`

## logs
`cat $(confluent local current)/connect/connect.stdout` # cd to this directory to get to logs e.g. /var/folders/p1/99852nrd7xn0fjqkp8gfk6rh0000gn/T/confluent.878496/connect/logs
`confluent local services connect log -f`
`curl -Ss http://localhost:8083/admin/loggers | jq` # check log levels
`curl -Ss http://localhost:8083/admin/loggers/org.apache.kafka.connect.runtime.WorkerSourceTask | jq`
change log level for specific logger
`curl -s -X PUT -H "Content-Type:application/json" \
http://localhost:8083/admin/loggers/org.apache.kafka.connect.runtime.WorkerSourceTask \
-d '{"level": "TRACE"}' | jq '.'`
change log level for specific connector,
`curl -s -X PUT -H "Content-Type:application/json" \
http://localhost:8083/admin/loggers/inventory-debezium-connector \
-d '{"level": "INFO"}' | jq '.'`
connector stack trace
`curl localhost:8083/connectors/inventory-debezium-connector/status | jq`

## debug plugin?
run connect with debug switched on by setting environment variables, e.g.
`confluent local services connect stop`
`export CONNECT_DEBUG=y && export DEBUG_SUSPEND_FLAG=y`
`confluent local services connect start`
confirm that debug flags are set on the Connect java process, e.g.
`ps -ef | grep connect` should produce something like,
>  501 31241     1   0 11:47am ttys002    0:00.21 /opt/homebrew/Cellar/openjdk@11/11.0.15/libexec/openjdk.jdk/Contents/Home/bin/java -Xms256M -Xmx2G -server -XX:+UseG1GC -XX:MaxGCPauseMillis=20 .... -agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=5005 org.apache.kafka.connect.cli.ConnectDistributed /var/folders/p1/99852nrd7xn0fjqkp8gfk6rh0000gn/T/confluent.370600/connect/connect.properties


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
