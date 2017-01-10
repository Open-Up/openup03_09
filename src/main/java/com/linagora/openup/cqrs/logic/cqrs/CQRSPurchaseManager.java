package com.linagora.openup.cqrs.logic.cqrs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.google.common.base.Charsets;
import com.linagora.openup.cqrs.logic.PurchaseManager;
import com.linagora.openup.cqrs.logic.SimplePurchaseManager;

import kafka.consumer.ConsumerConfig;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.javaapi.producer.Producer;
import kafka.message.MessageAndMetadata;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

public class CQRSPurchaseManager implements PurchaseManager {

    private class Consumer implements Runnable {

        private final KafkaStream<byte[], byte[]> m_stream;

        Consumer(KafkaStream<byte[], byte[]> a_stream) {
            m_stream = a_stream;
        }

        public void run() {
            for (MessageAndMetadata<byte[], byte[]> aM_stream : m_stream) {
                System.out.println("Receiving message " + toString(aM_stream));
                simplePurchaseManager.purchaseProduct(toString(aM_stream));
            }
        }

        private String toString(MessageAndMetadata<byte[], byte[]> aM_stream) {
            return new String(aM_stream.message(), Charsets.UTF_8);
        }
    }

    private static final String ZK_SESSION_TIMEOUT = "400";
    private static final String ZK_SYNC_TIME = "200";
    private static final String AUTO_COMMIT8INTERVAL_MS ="1000";

    private static final int NUMBER_OF_CONSUMER_THREADS = 4;
    private static final String TOPIC = "purchase";

    private final SimplePurchaseManager simplePurchaseManager;
    private final Producer<String, byte[]> producer;
    private final ExecutorService executor;
    private final ConsumerConnector consumer;

    public CQRSPurchaseManager(SimplePurchaseManager simplePurchaseManager) {
        this.simplePurchaseManager = simplePurchaseManager;
        this.producer = createProducer();
        this.executor = Executors.newFixedThreadPool(4);
        this.consumer = kafka.consumer.Consumer.createJavaConsumerConnector(createConsumerConfig());
        getKafkaStreams(TOPIC).stream()
            .map(Consumer::new)
            .forEach(executor::submit);
    }

    private Producer<String, byte[]> createProducer() {
        Producer<String, byte[]> producer;Properties props = new Properties();
        props.put("metadata.broker.list", "10.11.114.151:9092");
        props.put("serializer.class", "kafka.serializer.DefaultEncoder");
        props.put("request.required.acks", "1");
        ProducerConfig config = new ProducerConfig(props);
        producer = new Producer<>(config);
        return producer;
    }

    private ConsumerConfig createConsumerConfig() {
        Properties props = new Properties();
        props.put("zookeeper.connect", "localhost");
        props.put("group.id", "0123456789");
        props.put("zookeeper.session.timeout.ms", ZK_SESSION_TIMEOUT);
        props.put("zookeeper.sync.time.ms", ZK_SYNC_TIME);
        props.put("auto.commit.interval.ms", AUTO_COMMIT8INTERVAL_MS);
        return new ConsumerConfig(props);
    }

    private List<KafkaStream<byte[], byte[]>> getKafkaStreams(String topic) {
        Map<String, Integer> topicCountMap = new HashMap<>();
        topicCountMap.put(topic, NUMBER_OF_CONSUMER_THREADS);
        Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumer.createMessageStreams(topicCountMap);
        return consumerMap.get(topic);
    }

    @Override
    public void purchaseProduct(String name) {
        producer.send(new KeyedMessage<>(TOPIC, name.getBytes()));
    }
}
