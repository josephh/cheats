# Kafka Producer notes
* create topic called `flow`
* > confluent local services kafka produce flow
... prompts for message, e.g.
```JSON
{ "foo": "bar" }
```
:information: Note! mention " ...--value-format avro... " and Kafka will insist on a schema being provided along with the produce, so,
> confluent local services kafka produce flow --value-format avro --property value.schema.file=message_flow_schema.avsc
```JSON
{"retailerId":"1234","tenant":"ukbq","quantity":-2,"type":"TOTAL","date":1676995022829}
{"retailerId":"666","tenant":"ukbq","quantity":2223,"type":"TOTAL","date":1676995022829}
```
* run a consumer to see the output, mentioning avro...
> confluent local services kafka consume flow --value-format avro

* add a key to the schema
> confluent local services kafka produce flow --value-format avro --property value.schema.file=message_flow_schema.avsc --property parse.key=true --property key.separator="=" --property key.schema.file=key_flow_schema.avsc
```JSON
{"ean": "abcean123", "locationRef":"store-123"}={"retailerId":"999","t":"joeco","quantity":111,"type":"TOTAL","date":1676995022829}
{"ean": "abcean999", "locationRef":"store-123"}={"retailerId":"123","tenant":"tpfr","quantity":111,"type":"TOTAL","date":1676995022829}
```

// key_flow_schema.avsc
{
  "doc": "Key for Stock level change events intended for inventory management.",
  "fields": [
    {
      "doc": "Location reference identifies where this stock can be found, e.g. a store. ",
      "name": "locationRef",
      "type": "string"
    },
    {
      "doc": "ean stands for European Article Number, provides a unique identifier for products and is similar to a stock keeping unit.",
      "name": "ean",
      "type": "string"
    }
  ],
  "name": "StockLevelChangeEventKey",
  "namespace": "com.kingfisher.customerfulfilment.inventoryManagement",
  "type": "record"
}

// message_flow_schema.avsc
{
    "doc": "Stock level change events intended for inventory management",
    "fields": [{
            "doc": "Retailer Id, unicode character sequence.",
            "name": "retailerId",
            "type": "string"
        },
        {
            "doc": "tenant is short for Operational Company and synonymous with Banner. (typically acronyms like bquk, bqie, tpuk) , unicode character sequence.",
            "name": "tenant",
            "type": "string"
        },
        {
            "doc": "The stock level, which may include negative values",
            "name": "quantity",
            "type": "int"
        },
        {
          "name": "type",
          "type": {
            "doc": "An enumerated value: TOTAL indicates that the stock posiation quantity is the new level to record; ADJUSTMENT indicates that the stock position quantity is an increment or decrement to the existing stock level.",
            "name": "type",
            "default": "TOTAL",
            "symbols": [
              "TOTAL",
              "ADJUSTMENT"
            ],
            "type": "enum"
          }
        }
    ],
    "name": "StockLevelChangeEvent",
    "namespace": "com.kingfisher.customerfulfilment.inventoryManagement",
    "type": "record"
}

OMX-2248 dead letter topic
==========================
kafka-avro-console-producer --bootstrap-server localhost:9092 --topic public.bquk.order-management.local.sales-order --property key.separator=:  --property parse.key=true --property key.serializer=org.apache.kafka.common.serialization.StringSerializer --property value.schema.file=order-placed.avsc

message all on one line...
2345:
{ "externalOrderId": "ct12345", "customer": { "externalId": "12345", "firstName": "john", "email": "john@gmail.com", "timezone": "America/Los_Angeles" }, "tenant": "bquk", "lineItems": [ { "lineRef": "1", "productRef": "product123", "quantity": 98, "fulfilmentChoiceRef": "001" }, { "lineRef": "2", "productRef": "product456", "quantity": 23, "fulfilmentChoiceRef": "001" }, { "lineRef": "3", "productRef": "product789", "quantity": 3, "fulfilmentChoiceRef": "001" } ], "fulfilmentChoices": [ { "ref": "001", "type": "HD", "deliveryType": "STANDARD", "deliveryAddress":{ "ref":"add-1", "name":"Test", "street": {"string":"street 1"}, "street2": {"string":"street 2"}, "city":"Reading", "state": null, "postcode":"RG24FG", "country":"UK", "email": {"string":"test@gmail.com"} }, "sourceLocationRef":"12345" } ] }

{
  "externalOrderId": "ct12345",
  "customer": {
    "externalId": "12345",
    "firstName": "john",
    "email": "john@gmail.com",
    "timezone": "America/Los_Angeles"
  },
  "tenant": "bquk",
  "lineItems": [
    {
      "lineRef": "1",
      "productRef": "product123",
      "quantity": 98,
      "fulfilmentChoiceRef": "001"
    },
    {
      "lineRef": "2",
      "productRef": "product456",
      "quantity": 23,
      "fulfilmentChoiceRef": "001"
    },
    {
      "lineRef": "3",
      "productRef": "product789",
      "quantity": 3,
      "fulfilmentChoiceRef": "001"
    }
  ],
  "fulfilmentChoices": [
    {
      "ref": "001",
      "type": "HD",
      "deliveryType": "STANDARD",
      "deliveryAddress":{
          "ref":"add-1",
          "name":"Test",
          "street": "street 1",
          "street2": {"string":"street 2"},
          "city":"Reading",
          "state": null,
          "postcode":"RG24FG",
          "country":"UK",
          "email": {"string":"test@gmail.com"}
      },
      "sourceLocationRef":"12345"
    }
  ]
}

working with e2e


OR-100008-3bc8cd06-d1e9-4e2f-ad8f-5ba244d7a527:{"externalOrderId": "OR-100008-3bc8cd06-d1e9-4e2f-ad8f-5ba244d7a527", "customer": { "externalId": "37837872892", "firstName": "Priasma", "email": "testEmail@example.com", "timezone": "Europe/London" }, "tenant": "bquk", "lineItems": [{ "lineRef": "1", "productRef": "5030478563504", "quantity": 12, "fulfilmentChoiceRef": "001" }, { "lineRef": "2", "productRef": "5052931670802", "quantity": 10, "fulfilmentChoiceRef": "001" }], "fulfilmentChoices": [{ "ref": "001", "type": "HD", "deliveryType": "NORMAL", "sourceLocationRef": "1260", "deliveryAddress": { "ref": "001", "name": "test-full-name", "street": { "string": "test-building-number, test-building-name" }, "street2": { "string": "test-street-name" }, "city": "test-city-name", "state": { "string": "test-sub-division-code" }, "postcode": "test-postal-code", "country": "test-country-code", "email": { "string": "test@test.com" } } }]}


OR-100008-3bc8cd06-d1e9-4e2f-ad8f-5ba244d7a527
{
	"externalOrderId": "OR-100008-3bc8cd06-d1e9-4e2f-ad8f-5ba244d7a527",
	"customer": {
		"externalId": "37837872892",
		"firstName": "Priasma",
		"email": "testEmail@example.com",
		"timezone": "Europe/London"
	},
	"tenant": "bquk",
	"lineItems": [{
		"lineRef": "1",
		"productRef": "5030478563504",
		"quantity": 12,
		"fulfilmentChoiceRef": "001"
	}, {
		"lineRef": "2",
		"productRef": "5052931670802",
		"quantity": 10,
		"fulfilmentChoiceRef": "001"
	}],
	"fulfilmentChoices": [{
		"ref": "001",
		"type": "HD",
		"deliveryType": "NORMAL",
		"sourceLocationRef": "1260",
		"deliveryAddress": {
			"ref": "001",
			"name": "test-full-name",
			"street": {
				"string": "test-building-number, test-building-name"
			},
			"street2": {
				"string": "test-street-name"
			},
			"city": "test-city-name",
			"state": {
				"string": "test-sub-division-code"
			},
			"postcode": "test-postal-code",
			"country": "test-country-code",
			"email": {
				"string": "test@test.com"
			}
		}
	}]
}


   curl --silent -X GET http://localhost:8081/subjects | jq
   kafka-topics --create --topic fbq --bootstrap-server localhost:9092
   kafka-topics --create --topic twt --bootstrap-server localhost:9092
   kafka-topics --create --topic ftp --bootstrap-server localhost:9092
   kafka-topics --create --topic atg --bootstrap-server localhost:9092


non-avro producer
 kafka-console-producer --bootstrap-server localhost:9092 --topic fbq --property key.separator=:  --property parse.key=true

message
bcks:{"first":"last"}

{"@timestamp":"2023-06-05T16:29:12.617+01:00","@version":"1","message":"Failed to deserialize value in record from topic fbq","logger_name":"com.kingfisher.customerfulfilment.ordermediator.kafka.function.FailedDeserializationFunction","thread_name":"org.springframework.kafka.KafkaListenerEndpointContainer#0-0-C-1","level":"ERROR","level_value":40000,"stack_trace":"org.apache.kafka.common.errors.SerializationException: Unknown magic byte!\n\tat ...




[
  {
    "doc": "Name for superhero",
    "fields": [
      {
        "name": "name",
        "type": "string"
      }
    ],
    "name": "SuperheroName",
    "namespace": "marvel",
    "type": "record"
  },
  {
    "doc": "Ref for superhero planet.",
    "fields": [
      {
        "name": "ref",
        "type": "string"
      },
      {
        "name": "name",
        "type": "names.SuperheroName"
      }
    ],
    "name": "Superhero",
    "namespace": "marvel",
    "type": "record"
  }
]

x4:{"ref":"krypton","name":"superman"}

x4:{ "names.SuperheroName":{ "name": "superman" }, "marvel.Superhero":{"ref":"krypton"}}

x4:{ "marvel.SuperheroName":{ "name": "superman" }, "ref":"krypton"}



