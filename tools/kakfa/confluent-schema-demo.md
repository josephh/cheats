Avsc schemas used in the examples
person.avsc
{
  "fields": [
    {
      "name":"firstName",
      "type": "string"
    }
  ],
  "name": "person",
  "namespace": "com.kingfisher",
  "type": "record"
}
person.avsc version 2 : FORWARD compatible
{
    "fields": [{
            "name": "firstName",
            "type": "string"
        },
        {
            "name": "lastName",
            "type": "string"
        }
    ],
    "name": "person",
    "namespace": "com.kingfisher",
    "type": "record"
}
person.avsc version X : FORWARD incompatible
{
    "fields": [
        {
            "name": "lastName",
            "type": "string"
        }
    ],
    "name": "person",
    "namespace": "com.kingfisher",
    "type": "record"
}
animal.avsc
{
  "fields": [
    {
      "name":"species",
      "type": "string"
    }
  ],
  "name": "animal",
  "namespace": "com.kingfisher",
  "type": "record"
}


Enable Schema Validation Collapse source
# create topic
kafka-topics --create \
  --bootstrap-server localhost:9092 \
  --topic persons \
  --partitions 3 \
  --replication-factor 1
> Created topic persons.
 
# publish messages, without constraints...
kafka-console-producer \
  --broker-list localhost:9092 \
  --topic persons \
  --property parse.key=true \
  --property key.separator=,
> 2,bar
> 3,foo
 
# consume messages
kafka-console-consumer \
  --bootstrap-server localhost:9092 \
  --topic persons \
  --from-beginning=true \
  --skip-message-on-error \
  --property print.key=true 
> b  bar
> a  foo
 
# enable validation
kafka-configs --alter \
  --bootstrap-server localhost:9092 \
  --entity-type topics \
  --entity-name persons \
  --add-config confluent.value.schema.validation=true
> Completed updating config for topic persons.
 
# try again to publish messages
kafka-console-producer \
  --broker-list localhost:9092 \
  --topic persons \
  --property parse.key=true \
  --property key.separator=,
> c,new
>[2023-09-24 11:31:39,182] ERROR Error when sending message to topic persons with key: 1 bytes, value: 3 bytes with error: (org.apache.kafka.clients.producer.internals.ErrorLoggingCallback)
org.apache.kafka.common.InvalidRecordException: Log record DefaultRecord(offset=0, timestamp=1695551498140, key=1 bytes, value=3 bytes) is rejected by the record interceptor io.confluent.kafka.schemaregistry.validator.RecordSchemaValidator




Publish message with schema validation Collapse source
# register person schema - note topic uses default TopicNameStrategy
curl -X POST -H "Content-Type: application/vnd.schemaregistry.v1+json" \
 --data '{"schema": "{ \"type\": \"record\", \"name\": \"person\", \"namespace\": \"com.kingfisher\", \"fields\": [{ \"name\": \"firstName\", \"type\": \"string\" }] }"}' \
 http://localhost:8081/subjects/persons-value/versions
> {"id":1}
 
# confirm version of newly-registered schema
curl --silent -X GET http://localhost:8081/subjects/persons-value/versions/1 | jq
{
  "subject": "persons-value",
  "version": 1,
  "id": 1,
  "schema": "{\"type\":\"record\",\"name\":\"person\",\"namespace\":\"com.kingfisher\",\"fields\":[{\"name\":\"firstName\",\"type\":\"string\"}]}"
}
 
# start an avro consumer
kafka-avro-console-consumer \
  --bootstrap-server localhost:9092 \
  --topic persons \
  --property print.key=true \
  --property key.deserializer=org.apache.kafka.common.serialization.StringDeserializer
 
# publish person message - note auto schema registration is DISABLED
kafka-avro-console-producer \
  --bootstrap-server localhost:9092 \
  --topic persons \
  --property parse.key=true \
  --property key.separator="=" \
  --property key.serializer=org.apache.kafka.common.serialization.StringSerializer \
  --property value.schema.id=1
  --property auto.register.schemas=false  
> 66={"firstName":"jack"}
 
# try publish an avro message with an unregistered schema - note auto schema registration is DISABLED 
kafka-avro-console-producer \
  --bootstrap-server localhost:9092 \
  --topic persons \
  --property parse.key=true \
  --property key.separator="=" \
  --property key.serializer=org.apache.kafka.common.serialization.StringSerializer \
  --property value.schema='{"type": "record", "name": "animal", "namespace": "com.kingfisher", "fields": [{ "name": "species", "type": "string" }] }"}' \
  --property auto.register.schemas=false
> 1={"species":"cat"}
Schema not found io.confluent.rest.exceptions.RestNotFoundException: Schema not found




Configure schema evolution compatiblity Collapse source
# amend subject - persons-value - schema compatibility policy
curl -X PUT \
  -H "Content-Type: application/vnd.schemaregistry.v1+json" \
  --data '{"compatibility": "FORWARD"}' \
  http://localhost:8081/config/persons-value
{"compatibility":"FORWARD"}%
 
# try and register incompatible schema version (FORWARD compatible schema versions cannot delete mandatory field)  
curl -X POST -H "Content-Type: application/vnd.schemaregistry.v1+json" \
 --data '{"schema": "{ \"fields\": [ { \"name\": \"lastName\", \"type\": \"string\" } ], \"name\": \"person\", \"namespace\": \"com.kingfisher\", \"type\": \"record\" }"}' \
"http://localhost:8081/compatibility/subjects/persons-value/versions/latest?verbose=true"
> {"is_compatible":false,"messages":["Incompatibility{type:READER_FIELD_MISSING_DEFAULT_VALUE, location:/fields/0, message:firstName, reader:{\"type\":\"record\",\"name\":\"person\",\"namespace\":\"com.kingfisher\",\"fields\":[{\"name\":\"firstName\",\"type\":\"string\"}]}, writer:{\"type\":\"record\",\"name\":\"person\",\"namespace\":\"com.kingfisher\",\"fields\":[{\"name\":\"lastName\",\"type\":\"string\"}]}}"]}%      
 
# register FORWARD compatible schema version
curl -X POST -H "Content-Type: application/vnd.schemaregistry.v1+json" \
 --data '{"schema": "{ \"fields\": [{ \"name\": \"firstName\", \"type\": \"string\" }, { \"name\": \"lastName\", \"type\": \"string\" } ], \"name\": \"person\", \"namespace\": \"com.kingfisher\", \"type\": \"record\" }"}' \
"http://localhost:8081/subjects/persons-value/versions"
> {"id":2}%




Amend subject name strategy Collapse source
# ensure schema validation is on for topic persons
kafka-configs --describe \
  --bootstrap-server localhost:9092 \
  --entity-name persons \
  --entity-type topics \
  --all \
| grep validation
>   confluent.value.schema.validation=true ...
 
# try publish an avro message to topic persons with animal schema - note auto schema registration is DISABLED
kafka-avro-console-producer \
  --bootstrap-server localhost:9092 \
  --topic persons \
  --property parse.key=true \
  --property key.separator="=" \
  --property key.serializer=org.apache.kafka.common.serialization.StringSerializer \
  --property value.schema='{"type": "record", "name": "animal", "namespace": "com.kingfisher", "fields": [{ "name": "species", "type": "string" }] }"}'  
  --property auto.register.schemas=false
> 3={"species":"mouse"}
 
# amend subject naming strategy
kafka-configs --alter \
  --bootstrap-server localhost:9092  \
  --entity-type topics \
  --entity-name persons \
  --add-config confluent.value.subject.name.strategy=io.confluent.kafka.serializers.subject.RecordNameStrategy
> Completed updating config for topic persons.
 
# publish a message - note, auto-registration is enabled (by default) AND client producer MUST specify strategy
kafka-avro-console-producer \
  --bootstrap-server localhost:9092 \
  --topic persons \
  --property parse.key=true \
  --property key.separator="=" \
  --property key.serializer=org.apache.kafka.common.serialization.StringSerializer \
  --property value.schema.file=animal.avsc \
  --property value.subject.name.strategy=io.confluent.kafka.serializers.subject.RecordNameStrategy
> 3={"species":"mouse"}
 
# confirm new animal record name subject is in place
curl --silent -X GET http://localhost:8081/subjects| jq
[
  "com.kingfisher.animal",
  "persons-value"
]
 
# add a person record - note, auto-registration is enabled AND client producer MUST specify strategy
kafka-avro-console-producer \
  --bootstrap-server localhost:9092 \
  --topic persons \
  --property parse.key=true \
  --property key.separator="=" \
  --property key.serializer=org.apache.kafka.common.serialization.StringSerializer \
  --property value.schema.file=person.avsc \
  --property value.subject.name.strategy=io.confluent.kafka.serializers.subject.RecordNameStrategy
> 9={"firstName":"john"}
 
# confirm new person record name subject is in place
curl --silent -X GET http://localhost:8081/subjects| jq
[
  "com.kingfisher.animal",
  "com.kingfisher.person",
  "persons-value"
]
 
# consume latest persons messages
kafka-avro-console-consumer \
  --bootstrap-server localhost:9092 \
  --topic persons \
  --property print.key=true \
  --skip-message-on-error \
  --property key.deserializer=org.apache.kafka.common.serialization.StringDeserializer \
  --from-beginning
> 3  {"species":"mouse"}
> 9  {"firstName":"john"}
