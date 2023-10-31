# Kafka CLI

## Topics
### create
```kafka-topics --create --topic cool-topic --bootstrap-server localhost:9092```
### list
```kafka-topics --list --bootstrap-server localhost:9092```
### describe
```kafka-topics --describe --topic cool-topic --bootstrap-server localhost:9092```
### alter
```kafka-topics --alter --topic hot-topic --partitions 9 --bootstrap-server localhost:9092```
Note, to turn on schema validation on a topic, the broker needs to know how to connect to the schema registry.  In the case of local confluent it was enough to add to etc/kafka/server.properties the entry, ``
```kafka-configs --alter --topic new --bootstrap-server localhost:9092 --add-config confluent.value.schema.validation=true```
### delete
```kafka-topics --delete --topic warm-topic --bootstrap-server localhost:9092```

## Consume from dev
...
```
kafka-avro-console-consumer \
  --bootstrap-server pkc-41wq6.eu-west-2.aws.confluent.cloud:9092 \
  --consumer.config ~/confluent-dev.config \
  --group "order-management-dev-test.demo.joe2"  \
  --topic private.order-management.dev.sales-order \
  --formatter kafka.tools.DefaultMessageFormatter \
  --property print.timestamp=true \
  --property print.key=true \
  --property print.value=true \
  --from-beginning
 ```
```
kafka-avro-console-consumer \
  --bootstrap-server pkc-41wq6.eu-west-2.aws.confluent.cloud:9092 \
  --consumer.config ~/confluent-dev.config \
  --group "order-management-dev-test.demo.joe3"  \
  --topic private.order-management.dev.sales-order \
  --formatter kafka.tools.DefaultMessageFormatter \
  --property print.timestamp=true \
  --property print.key=true \
  --property print.value=true \
  --from-beginning
 ```

 
