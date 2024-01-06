# kafka configs for large messages
Insert the lines below at the bottom of server.properties file, located in kafka config directory

ex.: */usr/shared/kafka/config/server.properties* (sudo needed to edit this file)

Remember to restart kafka after changing these configs: 

```
sudo systemctl restart kafka.service 
```
```
sudo systemctl restart kafka-zookeeper.service
```

**Insert the quoted lines in server.properties (the kafka.server.properties.addendum file contains the lines to copy and paste)**

## Consumer side:
*This will determine the largest size of a message that can be fetched by the consumer. 
The tested value here is 104857600 (100MB).*

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

### References: 
> https://www.conduktor.io/kafka/how-to-send-large-messages-in-apache-kafka/

> https://dzone.com/articles/kafka-producer-and-consumer-example
