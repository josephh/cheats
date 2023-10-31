# Confluent / Apache Kafka
# 1. BLURB with pictorial
# 2. Source (dummy) Connector producer + consumer
# 3. Command line tools
# 4. Kingfisher use cases
* https://kafka.apache.org/
* https://www.confluent.io/
* https://avro.apache.org/

## What is Apache Kafka vs what is Confluent?
"Event streaming platform": Java-based, open source Apache project.  
* real-time event broadcast and consumption
* supports an asynchronous data processing model
* Confluent = commercial Kakfa offering... including a cloud offering ("3 trillion messages per day")
### Why are they using Confluent at Kingfisher?
* They want an "event-enabled" application landscape
* Predominantly a Java shop...
## "Event streaming" and Async processing", so what?
* Emitting events and reacting to events in real-time: mission critical applications
* data-processing scenarios with high-volumes of data and fluctuating load
* simplicity - decoupled applications
* scalability - have as many clients apps as you want
* efficiency - less latency waiting for a reply 
## Anything different about Kafka?
* publish/ subscribe to events (including via Kafka connectors)
* persistent storage
* additional processing of events as they happen, or once stored 
## What do Kafka events look like?
* key
* value
* timestamp
* _headers_
### Topics, partitions and consumers
* topics can be divided into partitions - each partition is written to a separate log file
* each partition can only be read by a single consumer in a consumer group - but topics and partitions can have multiple consumer groups
* consumers are allocated an ID and an offset is stored for each consumer (i.e. the next point in the log)
### Guarantees - message delivery: durability of published messages and consumer acknowledgement
* "at most once"
* "at least once"
* "exactly once"
### Data formats and schemas
* avro
* protobuf
* json schema
## Use Cases 
1. systems integration
1. stock updates - event notifications versus ETL
1. order workflow orchestration
