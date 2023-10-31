# Consumer
 kafka-avro-console-consumer \
  --bootstrap-server pkc-41wq6.eu-west-2.aws.confluent.cloud:9092 \
  --consumer.config ~/confluent-dev.config \
  --group "order-management-dev-test.demo.joe4"  \
  --topic private.order-management.dev.sales-order \
  --from-beginning=true \
  --skip-message-on-error=true \
  --property print.key=true \
  --property key.deserializer=org.apache.kafka.common.serialization.StringDeserializer
