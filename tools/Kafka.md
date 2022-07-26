# Kafka cheat sheet
## Zookeeper
TL;DR Zookeeper is the coordination service for distributed apps
> ZooKeeper is a high-performance coordination service for distributed applications. It exposes common services - such as naming, configuration management, synchronization, and group services - in a simple interface so you don't have to write them from scratch. You can use it off-the-shelf to implement consensus, group management, leader election, and presence protocols. And you can build on it for your own, specific needs.
## Kafka
is the distributed, persistent (for configurable lengths of time), event-driven messaging system.
Emits events and topics; publish-subscribe to use or consume events.
In Confluent Platform, realtime streaming events are stored in a Kafka topic, which is essentially an append-only log.
A **topic** is the most fundamental unit of organisation in Kafka, analogour to a db table. 
### install
installed under user home
install datagen source connector via confluent hub
> confluent-hub install confluentinc/kafka-connect-datagen:0.5.3
### start kafka
> confluent local services start
> joseph.jobbings@JosephJobbingss-MacBook-Pro ~ % confluent local services start                                
The local commands are intended for a single-node development environment only,
NOT for production usage. https://docs.confluent.io/current/cli/index.html

Using CONFLUENT_CURRENT: /var/folders/p1/99852nrd7xn0fjqkp8gfk6rh0000gn/T/confluent.565137
Starting ZooKeeper
ZooKeeper is [UP]
Kafka is [UP]
Starting Schema Registry
Schema Registry is [UP]
Starting Kafka REST
Kafka REST is [UP]
Starting Connect
Connect is [UP]
Starting ksqlDB Server
ksqlDB Server is [UP]
Starting Control Center
Control Center is [UP]
### control centre
http://localhost:9021 - in local install.  use the control centre to add a topic (i.e. a grouping of related events).  n.b. topic name ARE case sensitive.  in the getting started guide, later on you create data generators that produce data to these topics.
connect topics to datagen source connector (for development illustrations)

## Concepts
### Stream
immutable, append-only events (or 'rows') - no delete, no update
### table
a mutable collection that models change over time.   Table uses row keys to display the most recent data for each key. All but the newest rows for each key are deleted periodically. Also, each row has a timestamp, so you can define a **windowed** table which enables controlling how to group records that have the same key for stateful operations – like aggregations and joins – into time spans. Windows are tracked by record key.  
Together, streams and tables comprise a fully realized database.
> When you register a stream or a table on a topic, you can use the stream/table in SQL statements.
### [ksqlDB](https://ksqldb.io)

use the ksqldb editor in the Confluent control centre to
1. run a query that creates a stream over a topic
1. use a SELECT query to confirm data is moving through the   
__alternatively (or additionally)__
1. create a table over a topic
