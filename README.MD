https://dzone.com/articles/kafka-producer-and-consumer-example

Before starting with an example, let's get familiar first with the common terms and some commands used in Kafka.

Record: Producer sends messages to Kafka in the form of records. A record is a key-value pair. It contains the topic name and partition number to be sent. Kafka broker keeps records inside topic partitions. Records sequence is maintained at the partition level. You can define the logic on which basis partition will be determined. 

Topic: Producer writes a record on a topic and the consumer listens to it. A topic can have many partitions but must have at least one.

Partition: A topic partition is a unit of parallelism in Kafka, i.e. two consumers cannot consume messages from the same partition at the same time. A consumer can consume from multiple partitions at the same time.

Offset: A record in a partition has an offset associated with it. Think of it like this: partition is like an array; offsets are like indexs.

Producer: Creates a record and publishes it to the broker.

Consumer: Consumes records from the broker.

Commands: In Kafka, a setup directory inside the bin folder is a script (kafka-topics.sh), using which, we can create and delete topics and check the list of topics. Go to the Kafka home directory.

 Execute this command to see the list of all topics. 

./bin/kafka-topics.sh --list --zookeeper localhost:2181 .

localhost:2181 is the Zookeeper address that we defined in the server.properties file in the previous article.

Execute this command to create a topic. 

./bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 100 --topic demo .

replication-factor: if Kafka is running in a cluster, this determines on how many brokers a partition will be replicated. The partitions argument defines how many partitions are in a topic.

After a topic is created you can increase the partition count but it cannot be decreased. demo, here, is the topic name.

Execute this command to delete a topic.

./bin/kafka-topics.sh --zookeeper localhost:2181 --delete --topic demo .

This command will have no effect if in the Kafka server.properties file, if delete.topic.enable is not set to be true.

Execute this command to see the information about a topic. 

./bin/kafka-topics.sh --describe --topic demo --zookeeper localhost:2181 . 

PRODUCER CREATORS

BOOTSTRAP_SERVERS_CONFIG: The Kafka broker's address. If Kafka is running in a cluster then you can provide comma (,) seperated addresses. For example:localhost:9091,localhost:9092 

CLIENT_ID_CONFIG: Id of the producer so that the broker can determine the source of the request.

KEY_SERIALIZER_CLASS_CONFIG: The class that will be used to serialize the key object. In our example, our key is Long, so we can use the LongSerializer class to serialize the key. If in your use case you are using some other object as the key then you can create your custom serializer class by implementing the Serializer interface of Kafka and overriding the serialize method.

VALUE_SERIALIZER_CLASS_CONFIG: The class that will be used to serialize the value object. In our example, our value is String, so we can use the StringSerializer class to serialize the key. If your value is some other object then you create your custom serializer class.

CUSTOMER PARTITIONER

PARTITIONER_CLASS_CONFIG: The class that will be used to determine the partition in which the record will go. In the demo topic, there is only one partition, so I have commented this property. You can create your custom partitioner by implementing the CustomPartitioner interface.

In the CustomPartitioner class, I have overridden the method partition which returns the partition number in which the record will go.

CONSUMER CREATOR

BOOTSTRAP_SERVERS_CONFIG: The Kafka broker's address. If Kafka is running in a cluster then you can provide comma (,) seperated addresses. For example: localhost:9091,localhost:9092.

GROUP_ID_CONFIG: The consumer group id used to identify to which group this consumer belongs.

KEY_DESERIALIZER_CLASS_CONFIG: The class name to deserialize the key object. We have used Long as the key so we will be using LongDeserializer as the deserializer class. You can create your custom deserializer by implementing the Deserializer interface provided by Kafka.

VALUE_DESERIALIZER_CLASS_CONFIG: The class name to deserialize the value object. We have used String as the value so we will be using StringDeserializer as the deserializer class. You can create your custom deserializer.

MAX_POLL_RECORDS_CONFIG: The max count of records that the consumer will fetch in one iteration.

ENABLE_AUTO_COMMIT_CONFIG: When the consumer from a group receives a message it must commit the offset of that record. If this configuration is set to be true then, periodically, offsets will be committed, but, for the production level, this should be false and an offset should be committed manually.

AUTO_OFFSET_RESET_CONFIG: This property is used to determine the offset from where the consumer will start consuming messages. There are three values for this property: earliest, latest, and none. If you set it to earliest then the consumer will start consuming messages from the beginning of the topic. If you set it to latest then the consumer will start consuming messages from the end of the topic. If you set it to none then the consumer will throw an exception if no offset is found for the consumer's group id. In our example, we have set it to earliest.

For each consumer group, the last committed offset value is stored. This configuration comes handy if no offset is committed for that group, i.e. it is the new group created. Setting this value to earliest will cause the consumer to fetch records from the beginning of offset i.e from zero. Setting this value to latest will cause the consumer to fetch records from the new records. By new records mean those created after the consumer group became active.

MAIN APPLICATION

The MAIN snippet explains how to produce and consume messages from a Kafka broker. If you want to run a producer then call the runProducer function from the main function. If you want to run a consumeer, then call the runConsumer function from the main function.

The offset of records can be committed to the broker in both asynchronous and synchronous ways. Using the synchronous way, the thread will be blocked until an offset has not been written to the broker.

CONCLUSION

We have seen how Kafka producers and consumers work. You can check out the whole project on my GitHub page. If you are facing any issues with Kafka, please ask in the comments. In next article, I will be discussing how to set up monitoring tools for Kafka using Burrow.
