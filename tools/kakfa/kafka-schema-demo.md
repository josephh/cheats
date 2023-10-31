# Schema Registry Demo
## Test schemas
### Value schema - version 1 
```json
{"type":"record","name":"exampleRecord","namespace":"com.kingfisher","doc":"Example schema.","fields":[{"name":"age","type":"int","doc":"The int type is a 32-bit signed integer."},{"name":"firstName","type":{"type":"string","avro.java.string":"String"},"doc":"Unicode character sequence."},{"name":"lastName","type":{"type":"string","avro.java.string":"String"},"doc":"Unicode character sequence."}]}
```
### Value schema with extra field - version 2
```
// schema with additional optional field (FORWARD compatible)  
{"type": "record", "name": "exampleRecord", "namespace": "com.kingfisher", "doc": "Example schema.", "fields": [{ "name": "age", "type": "int", "doc": "The int type is a 32-bit signed integer." }, { "name": "firstName", "type": { "type": "string", "avro.java.string": "String" }, "doc": "Unicode character sequence." }, { "name": "lastName", "type": { "type": "string", "avro.java.string": "String" }, "doc": "Unicode character sequence." }, { "name": "gender", "type": [ "null", "string" ], "default": null }]}
```  
## Create a topic "test" and turn on schema validation
```
kafka-topics --create \
  --bootstrap-server localhost:9092 \
  --topic test \
  --partitions 3 \ 
  --replication-factor 1
```  
### Confirm topic exists 
```
kafka-topics --list \
  --bootstrap-server localhost:9092 \
  | grep -v -e _confluent
```  
### Enable schema validation
```
kafka-configs --alter \
  --bootstrap-server localhost:9092  \
  --entity-type topics \
  --entity-name test \
  --add-config confluent.value.schema.validation=true
```  
```
kafka-configs --describe \
  --bootstrap-server localhost:9092 \
  --entity-name test \
  --entity-type topics \
  --all
```
### Confirm schema validation is switched on for topic - publish message fails in absence of registered schema
! note schema auto-registration is disabled
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
input:  
`id-123={"age":32,"firstName":"john","lastName":"smith"}`  
### Schema is not yet registered
`curl --silent -X GET http://localhost:8081/subjects | jq`
## Register the schema for topic test
```
curl -X POST -H "Content-Type: application/vnd.schemaregistry.v1+json" --data '{"schema": "{\"type\":\"record\",\"name\":\"exampleRecord\",\"namespace\":\"com.kingfisher\",\"doc\":\"Example schema.\",\"fields\":[{\"name\":\"age\",\"type\":\"int\",\"doc\":\"The int type is a 32-bit signed integer.\"},{\"name\":\"firstName\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"},\"doc\":\"Unicode character sequence.\"},{\"name\":\"lastName\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"},\"doc\":\"Unicode character sequence.\"}]}"}' http://localhost:8081/subjects/test-value/versions
```
### Schema now registered - against subject name
`curl --silent -X GET http://localhost:8081/subjects/test-value/versions | jq`  
`curl --silent -X GET http://localhost:8081/subjects/test-value/versions/1 | jq`
### Publishing a message now works - provided the schema is available in the registry and matches the supplied schema  
```
kafka-avro-console-producer\
  --bootstrap-server localhost:9092 \
  --topic test \
  --property parse.key=true \
  --property key.separator="=" \
  --property key.serializer=org.apache.kafka.common.serialization.StringSerializer \
  --property value.schema.file=example-value.avsc \
  --property auto.register.schemas=false \
  --property use.latest.version=true
```
input:  
`id-123={"age":32,"firstName":"john","lastName":"smith"}`
### Consume new message
```
kafka-avro-console-consumer \ 
  --bootstrap-server localhost:9092 \ 
  --topic test \ 
  --from-beginning=true \ 
  --skip-message-on-error \
  --property print.key=true \ 
  --property key.deserializer=org.apache.kafka.common.serialization.StringDeserializer

```
### Try produce a message with an unregistered schema
```
kafka-avro-console-producer\
  --bootstrap-server localhost:9092 \
  --topic test \
  --property parse.key=true \
  --property key.separator="=" \
  --property key.serializer=org.apache.kafka.common.serialization.StringSerializer \
  --property value.schema.file=example-value-spurious.avsc \
  --property auto.register.schemas=false \
  --property use.latest.version=true
```
input:  
`id-123={"age":32,"firstName":"john","lastName":"smith", "new": "foo"}`

## Set schema compatibility
```
curl -X PUT -H "Content-Type: application/vnd.schemaregistry.v1+json" --data '{"compatibility": "FORWARD"}' http://localhost:8081/config/test-value
```
### query the setting
`curl -X GET http://localhost:8081/config/test-value`
### Test new version against compatibility and register
```
curl -X POST -H "Content-Type: application/vnd.schemaregistry.v1+json" \
 
  --data '{"schema": "{\"type\": \"record\", \"name\": \"exampleRecord\", \"namespace\": \"com.kingfisher\", \"doc\": \"Example schema.\", \"fields\": [{ \"name\": \"age\", \"type\": \"int\", \"doc\": \"The int type is a 32-bit signed integer.\" }, { \"name\": \"firstName\", \"type\": { \"type\": \"string\", \"avro.java.string\": \"String\" }, \"doc\": \"Unicode character sequence.\" }, { \"name\": \"lastName\", \"type\": { \"type\": \"string\", \"avro.java.string\": \"String\" }, \"doc\": \"Unicode character sequence.\" }, { \"name\": \"gender\", \"type\": [ \"null\", \"string\" ], \"default\": null }]}"}' \
 
  http://localhost:8081/compatibility/subjects/test-value/versions/latest?verbose=true
 ```
 ```
 curl -X POST -H "Content-Type: application/vnd.schemaregistry.v1+json" \
  --data '{"schema": "{\"type\": \"record\", \"name\": \"exampleRecord\", \"namespace\": \"com.kingfisher\", \"doc\": \"Example schema.\", \"fields\": [{ \"name\": \"age\", \"type\": \"int\", \"doc\": \"The int type is a 32-bit signed integer.\" }, { \"name\": \"firstName\", \"type\": { \"type\": \"string\", \"avro.java.string\": \"String\" }, \"doc\": \"Unicode character sequence.\" }, { \"name\": \"lastName\", \"type\": { \"type\": \"string\", \"avro.java.string\": \"String\" }, \"doc\": \"Unicode character sequence.\" }, { \"name\": \"gender\", \"type\": [ \"null\", \"string\" ], \"default\": null }]}"}' \
  http://localhost:8081/subjects/test-value/versions
  ```
 
confluent.key.subject.name.strategy=io.confluent.kafka.serializers.subject.TopicNameStrategy sensitive=false synonyms={}

confluent.value.subject.name.strategy=io.confluent.kafka.serializers.subject.TopicNameStrategy sensitive=false synonyms={}


# NOTES
1. each schema gets a global id - this is the magic byte(?) in the messages
1. every registered schema version gets a unique id in schema registry
1. view a schema via the schema reg rest API 
`curl --silent -X GET http://localhost:8081/schemas/ids/4 | jq`
1. confluent.value.schema.validation means, if a message is produced to a topic a valid schema for the value of the message, an error is returned to the producer, and the message is discarded.
1. kafka avro console producer:  either value.schema, value.schema.id, or value.schema.file


validation = true ; worked example
rest api schema registry compatibility check with verbose output example
notes : naming strategy
notes : one topic multiple events?
DPS support for schema deploy and project
spring test - what happens to a consumer with an old version of schema?

