# Confluent 3 Broker local setup
See the following web page where these instructions cribbed from,
https://docs.confluent.io/platform/current/kafka/kafka-basics.html#run-a-multi-broker-cluster

### install
installed under user home (i.e. downloading and extracting confluent-7.2.0.tar.gz)
### set Java v 11
Needs `Java 8` or `Java 11`, so... use a combination of `/usr/libexec/java_home` and `JAVA_HOME` environment variable. i.e.
> export JAVA_HOME=`/usr/libexec/java_home -v 11` -------// echo $JAVA_HOME  should show Java v11 now
### set confluent home
Needs `CONFLUENT_HOME` environment variable (and append to path's `/bin`), i.e.
> export CONFLUENT_HOME=~/confluent-7.2.0
> export PATH=$PATH:$CONFLUENT_HOME/bin
### start  confluent local

```
export CONFLUENT_HOME=~/confluent-7.2.0-multi && export PATH=$PATH:$CONFLUENT_HOME/bin && export JAVA_HOME=`/usr/libexec/java_home -v 11` ...
```
```
... && zookeeper-server-start etc/kafka/zookeeper.properties
&& kafka-server-start etc/kafka/server.properties
&& kafka-server-start etc/kafka/server-1.properties
&& kafka-server-start etc/kafka/server-2.properties
&& kafka-rest-start etc/kafka-rest/kafka-rest.properties
&& connect-distributed etc/kafka/connect-distributed.properties
&& ksql-server-start etc/ksqldb/ksql-server.properties
&& schema-registry-start etc/schema-registry/schema-registry.properties
&& control-center-start etc/confluent-control-center/control-center.properties
```

### Data
To refresh installation: delete folders from /tmp. E.g.s /tmp/confluent, /tmp/zookeeper etc.

