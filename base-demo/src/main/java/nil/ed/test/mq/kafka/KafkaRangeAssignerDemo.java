package nil.ed.test.mq.kafka;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Kafka 分段分区分配器算法Demo.
 * @author lidelin.
 */
public class KafkaRangeAssignerDemo {

    public static void main(String[] args) {

        String[] consumers = new String[] {"A"};
        assign(consumers, 1);
        assign(consumers, 2);

        consumers = new String[] {"A", "B"};
        assign(consumers, 1);
        assign(consumers, 2);
        assign(consumers, 3);
        assign(consumers, 4);


        consumers = new String[] {"A", "B", "C"};
        assign(consumers, 1);
        assign(consumers, 2);
        assign(consumers, 3);
        assign(consumers, 4);
        assign(consumers, 5);
    }

    public static void assign(String[] consumers, int partitions) {
        int numPartitionsPerConsumer = partitions / consumers.length;
        int consumersWithExtraPartition = partitions % consumers.length;
        List<Integer> ps = IntStream.range(0, partitions).boxed().collect(Collectors.toList());
        Map<String, List<Integer>> result = new HashMap<>(consumers.length);
        for (int i = 0, n = consumers.length; i < n; i++) {
            int start = numPartitionsPerConsumer * i + Math.min(i, consumersWithExtraPartition);
            int length = numPartitionsPerConsumer + (i + 1 > consumersWithExtraPartition ? 0 : 1);
            result.put(consumers[i], ps.subList(start, start + length));
        }
        System.out.printf("consumers=%s, partitions=%s, result=%s%n", Arrays.toString(consumers), partitions, result);
    }

}
