# Kafka schema registry notes
## Turn on schema validation for a topic
```
kafka-configs --alter --topic new --bootstrap-server localhost:9092 --add-config confluent.value.schema.validation=true
```
## Query the registry
### See registered subjects
curl --silent -X GET http://localhost:8081/subjects | jq
[
  "flow-key",
  "flow-value"
]


### See registered schemas
curl --silent -X GET http://localhost:8081/subjects | jq
[
  "flow-key",
  "flow-value"
]

### See registered schema versions (and details)
curl -X GET http://localhost:8081/subjects/flow-value/versions/1 |jq

### Top level config
curl -X GET http://localhost:8081/config


## REGISTER a schema



## DELETION - soft vs HARD
### Soft delete ALL registered schema versions for subject
curl -X DELETE http://localhost:8081/subjects/eric-value

### Soft delete specific registered schema versions for subject
curl -X DELETE http://localhost:8081/subjects/eric1-value/versions/1

### Hard delete all or specific schema - note quote around URL (as zshell gets upset with '?' character
curl -X DELETE "http://localhost:8081/subjects/bman-value/versions/1?permanent=true"
