# Kafka configurations for large messages transport in SOMA / GAMMA applications
This POC aims to validate a Kafka configuration which enables huge messages transport between microservices. This is considered essential for Partial Discharge (PD) data transportation in data bus, using Apache Kafka messaging broker.

**Insert the instructions in server.properties file.** 

Example of file location (sudo needed to edit the file): 

> */usr/shared/kafka/config/server.properties*

Remember to restart kafka after changing these configs: 

```
sudo systemctl restart kafka.service 
```
```
sudo systemctl restart kafka-zookeeper.service
```

## Consumer side:
*This will determine the largest size of a message that can be fetched by the consumer. 
The tested value in this POC is 104857600 (100MB).*

```
fetch.message.max.bytes = 104857600
```

## Broker side:
*This will allow for the replicas in the brokers to send messages within the cluster and 
make sure the messages are replicated correctly. If this is too small, then the message 
will never be replicated, and therefore, the consumer will never see the message because 
the message will never be committed (fully replicated).*

```
replica.fetch.max.bytes = 104857600
```

## Broker side:
*This is the largest size of the message that can be received by the broker from a producer.*

```
message.max.bytes = 104857600
```

## Broker side (per topic): 
*This is the largest size of the message the broker will allow to be appended to the topic. 
This size is validated pre-compression. (Defaults to broker's message.max.bytes.)*

```
max.message.bytes = 104857600
```

### Notes: 
The *kafka.server.properties.addendum* file contains the lines to copy and paste.

### References: 
> https://www.conduktor.io/kafka/how-to-send-large-messages-in-apache-kafka/

> https://dzone.com/articles/kafka-producer-and-consumer-example
