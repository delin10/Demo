package nil.ed.test.mq.kafka;

import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
import java.util.stream.IntStream;

/**
 * Kafka 分段分区分配器算法Demo.
 * @author lidelin.
 */
public class KafkaRoundRobinAssignerDemo {

    public static void main(String[] args) {
        /*
        topic: tp1(3), tp2(5), tp3(7)
        consumer:
         */
        Map<String, Set<String>> subscriptions = new HashMap<>();
        List<Pair<String, String>> tps = new LinkedList<>();
        repartition(tps, "tp1", 7);
        repartition(tps, "tp2", 5);
        repartition(tps, "tp3", 3);

        subscribe(subscriptions, "A", "tp1");
        subscribe(subscriptions, "A", "tp2");
        subscribe(subscriptions, "A", "tp3");

        subscribe(subscriptions, "B", "tp1");
        subscribe(subscriptions, "B", "tp2");

        subscribe(subscriptions, "C", "tp1");

        assign(subscriptions, tps);
    }

    public static void subscribe(Map<String, Set<String>> subscriptions, String consumer, String topic) {
        subscriptions.computeIfAbsent(consumer, k -> new TreeSet<>());
        subscriptions.get(consumer).add(topic);
    }

    public static void repartition(List<Pair<String, String>> tps, String topic, int partition) {
        tps.removeIf(p -> p.getKey().equals(topic));
        IntStream.range(0, partition).mapToObj(i -> Pair.of(topic, String.valueOf(i))).forEach(tps::add);
    }

    public static void assign(Map<String, Set<String>> subscriptions, List<Pair<String, String>> tps) {
        tps.sort(Map.Entry.comparingByKey());
        Map<String, List<String>> result = new HashMap<>(subscriptions.size());
        Iterator<String> iterator = subscriptions.keySet().iterator();
        for (Pair<String, String> tp : tps) {
            if (!iterator.hasNext()) {
                iterator = subscriptions.keySet().iterator();
            }
            String consumer = iterator.next();
            while (!subscriptions.get(consumer).contains(tp.getKey())) {
                if (!iterator.hasNext()) {
                    iterator = subscriptions.keySet().iterator();
                }
                consumer = iterator.next();
            }

            result.computeIfAbsent(consumer, k -> new LinkedList<>());
            result.get(consumer).add(String.format("%s-%s", tp.getKey(), tp.getValue()));
        }

        System.out.printf("consumers=%s%npartitions=%s%nresult=%s%n", subscriptions, tps, result);
    }

}
