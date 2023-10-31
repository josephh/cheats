# CLI steps for DEMO
## Set local env variables
```export JAVA_HOME=`/usr/libexec/java_home -v 11` && export CONFLUENT_HOME=~/confluent-7.2.0 && export PATH=$PATH:$CONFLUENT_HOME/bin```
## Configure the kafka server to use schema registry
1. Set schema registry location in server properties before starting kafka
In file $CONFLUENT_HOME/etc/kafka/server.properties, set e.g.,
> confluent.schema.registry.url=http://localhost:8081
(to avoid having to set that value in consumer and producer commands).

## Create a tropic without schema validation
```kafka-topics --bootstrap-server localhost:9092 --topic flow --create --partitions 3 --replication-factor 1```  
Confirm topic now in place,  
```kafka-topics --list --bootstrap-server localhost:9092 | grep -v -e _confluent```  
and,  
```kafka-configs --describe --entity-name flow --entity-type topics --bootstrap-server localhost:9092 --all```
## Produce a serialized record (with the default serializer)
```kafka-console-producer --broker-list localhost:9092 --topic flow --property parse.key=true --property key.separator=,```
> 1,{"foo":"bar"}
## Consume (deserialize) the new record
```kafka-console-consumer --topic flow --bootstrap-server localhost:9092 --from-beginning --property print.key=true```
## Produce a message via avro producer, including schema
```kafka-avro-console-producer --bootstrap-server localhost:9092 --topic flow --property key.separator=:  --property parse.key=true --property key.serializer=org.apache.kafka.common.serialization.StringSerializer --property value.schema.file=demo.avsc```
> abc:{"first":33, "second": "foo"}
## Confirm value schema auto-registered
```bash
curl --silent -X GET http://localhost:8081/subjects | jq
[
  "flow-value"
]
```
## Consume (deserialize) the new avro record (skipping the non-avro message)
```kafka-avro-console-consumer --bootstrap-server localhost:9092 --topic flow --from-beginning=true --skip-message-on-error --property print.key=true --property key.deserializer=org.apache.kafka.common.serialization.StringDeserializer```
## Turn on schema validation for topic
```kafka-configs --bootstrap-server localhost:9092 --alter --entity-type topics --entity-name flow --add-config confluent.value.schema.validation=true```
Completed updating config for topic flow.
1. Create a schema with an unqualified name - in schema registry
```curl -v -H "Content-Type: application/json" --data @./message_flow_schema.avsc http://localhost:8081/subjects/flow/versions```



>>>>>>>>>>>>>>>>
demo.avsc -------
{ "type": "record", "name": "demoRecord",
  "fields": [
    { "name": "first", "type": "int" },
    { "name": "second", "type": "string" }
  ]
}

>>>>>>>>>>>>>>>>
1. Create a new topic using the GUI - call it "new"

create topic with defaults



[21/03 17:08] Jobbings, Joseph

2. Start an avro consumer - listening to the "new" topic (and including the keys in the output)

(this command assumes the programs like kafka-avro-console-consumer are on your path)

  kafka-avro-console-consumer --topic new --bootstrap-server localhost:9092 --property print.key=true

...nothing to see here  - no messages produced yet!



[21/03 17:11] Jobbings, Joseph

3. Start an avro producer, specifying the key and value schemas

(this command assumes your key and value avro schemas are now in the local folder to wherever you are running this program...)

kafka-avro-console-producer --topic new --bootstrap-server localhost:9092 --property value.schema.file=v.avsc --property key.schema.file=k.avsc --property  parse.key=true

...now the producer is waiting for some input in the same terminal - a key, separated by 'tab' character (the default) and a value...



[21/03 17:12] Jobbings, Joseph

4. Input a key and value for the producer to send into Kafka

{"externalOrderId": "order_123", "locationRef": "abc_loc_ref"} <-- tab character here! --->  { "e": "12345", "firstName": "john", "email": "john@gmail.com", "promotionOptIn": true, "timezone": "America/Los_Angeles", "retailerId": 999, "items": [ {"itemRef": "item123", "productRef": "product123", "quantity": 98}, {"itemRef": "item123", "productRef": "product123", "quantity": 23}, {"itemRef": "item123", "productRef": "product123", "quantity": 3} ] }



[21/03 17:13] Jobbings, Joseph

5. Key and message appear in consumer output...

  {"ean":"order_123", "locationRef": "abc_loc_ref"} {"retailerId":"12345","opco":"opco-abc","quantity":23}

  "Retailer Id, unicode character sequence.",
                        "name": "retailerId",
                        "type": "string"
                },
                {
                        "doc": "opco is short for Operational Company and synonymous with Banner. (typically acronyms like bquk, bqie, tpuk) , unicode character sequence.",
                        "name": "opco",
                        "type": "string"
                },
                {
                        "doc": "The stock level, which may include negative values",
                        "name": "quantity",
                        "type": "int"




[21/03 17:16] Jobbings, Joseph

6. and looking in the GUI or command line we see the schemas have been auto-added against the "new" topic

curl http://localhost:8081/subjects/new-key/versions/1 | jq
...
curl http://localhost:8081/subjects/new-value/versions/1 | jq
...

## Demo - "optional" fields

1. start producer for new topic - with single field schema
```
kafka-avro-console-producer --bootstrap-server localhost:9092 --topic bman --property key.separator=:  --property parse.key=true --property key.serializer=org.apache.kafka.common.serialization.StringSerializer --property value.schema='{ "type": "record", "name": "ericRecord", "fields": [ { "name": "address1", "type": "string" }]}'[

# produce a compliant message
123:{"address1":"39"}```
1. (confirm registered schema version 1)
```
curl --silent -X GET http://localhost:8081/subjects/bman-value/versions | jq
[
  1
]
```
```
curl --silent -X GET http://localhost:8081/subjects/bman-value/versions/1 | jq

{
  "subject": "bman-value",
  "version": 1,
  "id": 1,
  "schema": "{\"type\":\"record\",\"name\":\"ericRecord\",\"fields\":[{\"name\":\"address1\",\"type\":\"string\"}]}"
}
```
1. Consume message compliant with version 1
```
kafka-avro-console-consumer --topic bman --bootstrap-server localhost:9092 --property print.key=true --from-beginning --property key.deserializer=org.apache.kafka.common.serialization.StringDeserializer

123	{"address1":"39"}
```
1. restart the producer - adding a 2nd message (and auto-register version 2 of schema - with a 2nd address field)
```
kafka-avro-console-producer --bootstrap-server localhost:9092 --topic bman --property key.separator=:  --property parse.key=true --property key.serializer=org.apache.kafka.common.serialization.StringSerializer --property value.schema='{ "type": "record", "name": "ericRecord", "fields": [ { "name": "address1", "type": "string" }, { "name": "address2", "type": ["null", "string"], "default":"null"}]}'

1.  produce a compliant message
```
234:{"address1":"39", "address2": {"string": "acacia avenue"}}
```
1. (confirm registered schema version 2)
```
curl --silent -X GET http://localhost:8081/subjects/bman-value/versions | jq
[
  1,
  2
]
```
```
curl --silent -X GET http://localhost:8081/subjects/bman-value/versions/2 |jq
{
  "subject": "bman-value",
  "version": 2,
  "id": 3,
  "schema": "{\"type\":\"record\",\"name\":\"ericRecord\",\"fields\":[{\"name\":\"address1\",\"type\":\"string\"},{\"name\":\"address2\",\"type\":[\"null\",\"string\"],\"default\":\"null\"}]}"
}
```
1. produce a new message that includes null = ok
```
kafka-avro-console-producer --bootstrap-server localhost:9092 --topic bman --property key.separator=:  --property parse.key=true --property key.serializer=org.apache.kafka.common.serialization.StringSerializer --property value.schema='{ "type": "record", "name": "ericRecord", "fields": [ { "name": "address1", "type": "string" }, { "name": "address2", "type": ["null", "string"], "default":"null"}]}'
```
1. produce a compliant message, including null value
```
345:{"address1":"39", "address2": null}
```
```
kafka-avro-console-consumer --topic bman --bootstrap-server localhost:9092 --property print.key=true --from-beginning --property key.deserializer=org.apache.kafka.common.serialization.StringDeserializer
123	{"address1":"39"}
234	{"address1":"39","address2":{"string":"acacia avenue"}}
345	{"address1":"39","address2":null}
```
1. 'HARD' delete schema v 1
```
curl --silent -X DELETE http://localhost:8081/subjects/bman-value/versions/1?
curl -X DELETE "http://localhost:8081/subjects/bman-value/versions/1?permanent=true"
```
1. consume all messages - message written against version 1 includes the default "address2" field NULL
1. produce a new message that DOESN'T include all fields in version 2 = NOT ok

## demo.avsc all-on-one-line
{ "type": "record", "name": "ericRecord", "fields": [ { "name": "address1", "type": "string" }, { "name": "address2", "type": ["null", "string"], "default": "null"}]}


# Order Payment
## sample message
1098765620:{ 
  "erpSalesOrderId": "1098765620", 
  "paymentStatus" : "AUTHORISED", 
  "tenant": "bquk", 
  "currency" : "GBP",
  "payments": [
    {
      "amount" : "120.80", 
      "transactionCode" : {"string": "778997876767886876"},
      "paymentMethod" : "CREDITCARD",
      "cardType" : {"string": "VISA"},
      "paymentProvider" : "VERIFONE"
    },
    {
      "amount" : "20.20",
      "transactionCode" : {"string": "3242424546464767577"},
      "paymentMethod" : "GIFT_CARD",
      "paymentProvider" : "SVS"
    }
  ]
 }
 
 1098765620:{ "erpSalesOrderId": "1098765620", "paymentStatus" : "AUTHORISED", "tenant": "bquk", "currency" : "GBP" }
