package nil.ed.test.mq.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author lidelin.
 */
public class KafkaProducerDemo {

    public static void main(String[] args) throws InterruptedException {
        Map<String, Object> props = new HashMap<>();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("linger.ms", 1);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        Producer<String, String> producer = new KafkaProducer<>(props);
        try {
            while (true) {
                for (int i = 0; i < 100; i++) {
                    producer.send(new ProducerRecord<>("my-topic2", Integer.toString(i), IntStream.range(0, 100000).parallel().mapToObj(String::valueOf).collect(Collectors.joining())));
                    producer.send(new ProducerRecord<>("my-topic2", Integer.toString(i), Integer.toString(i)));
                }
                TimeUnit.SECONDS.sleep(1);
            }
        } finally {
            producer.close();
        }
    }

}
