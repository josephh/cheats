# Schema Subject Demo
## Test schemas
### Value schema - person
```json
{"type":"record","name":"person","namespace":"com.kingfisher.person","fields":[{"name":"firstName","type":"string"}]}
```
### Value schema - incompatible with person - animal
```json
{"type":"record","name":"animal","namespace":"com.kingfisher.animal","fields":[{"name":"species","type":"string"}]}
```
### Value schema - BACKWARDS compatible (but name incompatible) with person - animal
```json
{
  "fields": [
    {
      "name": "firstName",
      "type": "string"
    },
    {
      "name":"species",
      "type": [
          "null",
          "string"
        ],
        "default": null
    }
  ],
  "name": "animal",
  "namespace": "com.kingfisher.person",
  "type": "record"
}
```
## Create a topic "things"
```
kafka-topics --create \
  --bootstrap-server localhost:9092 \
  --topic things \
  --partitions 3 \
  --replication-factor 1
```  
### Confirm topic has the default subject naming strategy "TopicNameStrategy"
```
kafka-configs --describe \
  --bootstrap-server localhost:9092 \
  --entity-name things \
  --entity-type topics \
  --all  | grep strategy
```
## Produce a record and auto-register person schema
```
kafka-avro-console-producer \
  --bootstrap-server localhost:9092 \
  --topic things \
  --property parse.key=true \
  --property key.separator="=" \
  --property key.serializer=org.apache.kafka.common.serialization.StringSerializer \
  --property value.schema.file=person.avsc 
```
```
curl -X GET http://localhost:8081/subjects/things-value/versions/1 |jq
```
## Amend subject naming strategy
```
kafka-configs --alter \
  --bootstrap-server localhost:9092  \
  --entity-type topics \
  --entity-name things \
  --add-config confluent.value.subject.name.strategy=io.confluent.kafka.serializers.subject.RecordNameStrategy
```
```
kafka-configs --describe \
  --bootstrap-server localhost:9092 \
  --entity-name things \
  --entity-type topics \
  --all  | grep strategy
```
## Try produce a record and auto-register animal schema
```
kafka-avro-console-producer \
  --bootstrap-server localhost:9092 \
  --topic things \
  --property parse.key=true \
  --property key.separator="=" \
  --property key.serializer=org.apache.kafka.common.serialization.StringSerializer \
  --property value.schema.file=animal.avsc 
```
input
```
321={"species":"cat"}
^^^^  IncompatibleSchemaException:IncompatibleSchemaException: [Incompatibility{type:NAME_MISMATCH,
```


