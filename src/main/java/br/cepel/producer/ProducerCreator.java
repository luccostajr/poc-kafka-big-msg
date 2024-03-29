package br.cepel.producer;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

import br.cepel.config.Constants;

public class ProducerCreator {
    public ProducerCreator() {
        super();
    }

    public static Producer<Long, String> createProducer() {
        Properties props = new Properties();

        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, Constants.KAFKA_BROKERS);
        props.put(ProducerConfig.CLIENT_ID_CONFIG, Constants.CLIENT_ID);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        // set the maximum size of a request in bytes
        props.setProperty(ProducerConfig.MAX_REQUEST_SIZE_CONFIG, Constants.MAX_MESSAGE_SIZE + "");

        return new KafkaProducer<>(props);
    }
}