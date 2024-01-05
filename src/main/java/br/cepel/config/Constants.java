package br.cepel.config;

public interface Constants {
    public static String KAFKA_BROKERS = "localhost:9092";

    public static Integer MESSAGE_COUNT=100;

    public static String CLIENT_ID="big-message-client";

    public static String TOPIC_NAME="big-message-topic";

    public static String GROUP_ID_CONFIG="big-message-consumer";

    public static String OFFSET_RESET_LATEST="latest";

    public static String OFFSET_RESET_EARLIER="earliest";

    public static Integer MAX_POLL_RECORDS=1;

    public static Integer MAX_NO_MESSAGE_FOUND_COUNT=30;

    public static Integer CONSUMER_POOL_TIMEOUT=1000; // 1s

    public static Integer MAX_MESSAGE_SIZE=104857600; // 100MB
}