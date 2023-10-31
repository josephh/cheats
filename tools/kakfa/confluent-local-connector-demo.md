# Confluent local demo - connector
1. start up confluent
1. create **demo** topic
1. install "data generator" connector, https://www.confluent.io/hub/confluentinc/kafka-connect-datagen
1. Use UI "connect" option to configure and connect up data generator to **demo** topic
   * key converter = org.apache.kafka.connect.storage.StringConverter
   * set 10 second interval
   * quickstart = clickstream  
   * view messages in UI
1. look at node consumer code & `npm start`
