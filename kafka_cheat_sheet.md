# Kafka cheat sheet

Launching kafka :

```
docker run -p 2181:2181 -p 9092:9092 --env ADVERTISED_HOST=10.11.114.151 --env ADVERTISED_PORT=9092 spotify/kafka
```

Configuration : 

```
    private ConsumerConfig createConsumerConfig() {
        Properties props = new Properties();
        props.put("zookeeper.connect", "localhost");
        props.put("group.id", "0123456789");
        props.put("zookeeper.session.timeout.ms", ZK_SESSION_TIMEOUT);
        props.put("zookeeper.sync.time.ms", ZK_SYNC_TIME);
        props.put("auto.commit.interval.ms", AUTO_COMMIT8INTERVAL_MS);
        return new ConsumerConfig(props);
    }
```

Consuming : 

```
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
```

Then : 

```
this.consumer = kafka.consumer.Consumer.createJavaConsumerConnector(createConsumerConfig());
        getKafkaStreams(TOPIC).stream()
            .map(Consumer::new)
            .forEach(executor::submit);
```

with : 

```
    private List<KafkaStream<byte[], byte[]>> getKafkaStreams(String topic) {
        Map<String, Integer> topicCountMap = new HashMap<>();
        topicCountMap.put(topic, NUMBER_OF_CONSUMER_THREADS);
        Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumer.createMessageStreams(topicCountMap);
        return consumerMap.get(topic);
    }
```

Producing :

```
producer.send(new KeyedMessage<>(TOPIC, name.getBytes()));
```

And : 

```
    private Producer<String, byte[]> createProducer() {
        Producer<String, byte[]> producer;Properties props = new Properties();
        props.put("metadata.broker.list", "10.11.114.151:9092");
        props.put("serializer.class", "kafka.serializer.DefaultEncoder");
        props.put("request.required.acks", "1");
        ProducerConfig config = new ProducerConfig(props);
        producer = new Producer<>(config);
        return producer;
    }
```

Kafka imports used : 

```
import kafka.consumer.ConsumerConfig;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.javaapi.producer.Producer;
import kafka.message.MessageAndMetadata;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
```

Creating a Thread pool (reminder) : 

```
        Executors.newFixedThreadPool(4);
```
