# Schema Validation Demo
## Test schemas
### Value schema - version 1 
```json
{"type":"record","name":"exampleRecord","namespace":"com.kingfisher","doc":"Example schema.","fields":[{"name":"age","type":"int","doc":"The int type is a 32-bit signed integer."},{"name":"firstName","type":{"type":"string","avro.java.string":"String"},"doc":"Unicode character sequence."},{"name":"lastName","type":{"type":"string","avro.java.string":"String"},"doc":"Unicode character sequence."}]}
```
## Create a topic "test2" with schema validation enabled
```
kafka-topics --create \
  --bootstrap-server localhost:9092 \
  --topic test2 \
  --partitions 3 \
  --replication-factor 1
```  
### Confirm topic exists 
```
kafka-topics --list \
  --bootstrap-server localhost:9092 \
  | grep -v -e _confluent
```  
## publish disparate records with schema validation off
```
kafka-console-producer \
  --broker-list localhost:9092 \
  --topic test2 \
  --property parse.key=true \
  --property key.separator=,
```
input
```
>123,{"foo":"bar}
>321,{"another-foo":"baa"}
```
### Consume new messages 
```
kafka-console-consumer \
  --bootstrap-server localhost:9092 \
  --topic test2 \
  --from-beginning=true \
  --skip-message-on-error \
  --property print.key=true  
```
## Enable schema validation
```
kafka-configs --alter \
  --bootstrap-server localhost:9092 \
  --entity-type topics \
  --entity-name test2 \
  --add-config confluent.value.schema.validation=true
```
### Confirm schema validation is enabled
```
kafka-configs --describe \
  --bootstrap-server localhost:9092 \
  --entity-name test2 \
  --entity-type topics \
  --all
```
### Try and publish a record 
```
kafka-console-producer \
  --broker-list localhost:9092 \
  --topic test2 \
  --property parse.key=true \
  --property key.separator=,
```
input 
```
abc,{"foo":"bar"}
^^^^^ fails with error, "... rejected by the record interceptor..."
```
### Try and publish a record with avro producer and schema
```
kafka-avro-console-producer \
  --bootstrap-server localhost:9092 \
  --topic test2 \
  --property parse.key=true \
  --property key.separator="=" \
  --property key.serializer=org.apache.kafka.common.serialization.StringSerializer \
  --property value.schema.file=example-value.avsc \
  --property auto.register.schemas=false \
  --property use.latest.version=true
```
input
```
123={"age":33,"firstName":"John","lastName":"smith"}
^^^^^ even if this input is valid against the schema in the local file input to the avro producer, schema auto-registration is specified as false, so this schema cannot be written to the schema registry (or matched to a schema in the registry)
```
### Register schema against test2 value subject
```
curl -X POST -H "Content-Type: application/vnd.schemaregistry.v1+json" \
 --data '{"schema": "{\"type\": \"record\", \"name\": \"exampleRecord\", \"namespace\": \"com.kingfisher\", \"doc\": \"Example schema.\", \"fields\": [{ \"name\": \"age\", \"type\": \"int\", \"doc\": \"The int type is a 32-bit signed integer.\" }, { \"name\": \"firstName\", \"type\": { \"type\": \"string\", \"avro.java.string\": \"String\" }, \"doc\": \"Unicode character sequence.\" }, { \"name\": \"lastName\", \"type\": { \"type\": \"string\", \"avro.java.string\": \"String\" }, \"doc\": \"Unicode character sequence.\" }]}"}' \
 http://localhost:8081/subjects/test2-value/versions
 ```
### Confirm registered schema
 ` GET http://localhost:8081/subjects/test2-value/versions/1 | jq`
 
## Produce good message for schema
```
kafka-avro-console-producer \
  --bootstrap-server localhost:9092 \
  --topic test2 \
  --property parse.key=true \
  --property key.separator="=" \
  --property key.serializer=org.apache.kafka.common.serialization.StringSerializer \
  --property value.schema.file=example-value.avsc \
  --property auto.register.schemas=false \
  --property use.latest.version=true
```
input
```
123={"age":3,"firstName":"joe","lastName":"bob"}
```
### consume 
```
kafka-avro-console-consumer \
  --bootstrap-server localhost:9092 \
  --topic test2 \
  --from-beginning=true \
  --skip-message-on-error 
  --property print.key=true \
  --property key.deserializer=org.apache.kafka.common.serialization.StringDeserializer
```
## Try produce with standard consumer and different (unregistered) schema
```
kafka-console-producer \
  --bootstrap-server localhost:9092 \
  --topic test2 \
  --property parse.key=true \
  --property key.separator="=" \
  --property key.serializer=org.apache.kafka.common.serialization.StringSerializer \
  --property value.schema.file=example-value-extra.avsc
  --property auto.register.schemas=false \                                          
  --property use.latest.version=false   
```
input
```
123={"age":3,"firstName":"joe","lastName":"bob","gender":"male"}
^^^^^ fails with error, "... rejected by the record interceptor..."
```
## Try produce with standard consumer and different (unregistered) schema
```
kafka-console-producer \
  --bootstrap-server localhost:9092 \
  --topic test2 \
  --property parse.key=true \
  --property key.separator="=" \
  --property key.serializer=org.apache.kafka.common.serialization.StringSerializer \
  --property value.schema.id=1
```
input
```
123={"age":3,"firstName":"joe","lastName":"bob","gender":"male"}
^^^^^ fails with error, "... rejected by the record interceptor..."
```

## Produce good message for schema - with avro producer
```
kafka-avro-console-producer \
  --bootstrap-server localhost:9092 \
  --topic test2 \
  --property parse.key=true \
  --property key.separator="=" \
  --property key.serializer=org.apache.kafka.common.serialization.StringSerializer \
  --property value.schema.id=5

```
input
```
123={"age":3,"firstName":"joe","lastName":"bob"}

```
