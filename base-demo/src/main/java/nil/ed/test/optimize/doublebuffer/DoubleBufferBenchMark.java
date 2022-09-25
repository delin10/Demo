package nil.ed.test.optimize.doublebuffer;

import lombok.Getter;

import java.util.IntSummaryStatistics;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author lidelin.
 */
public class DoubleBufferBenchMark {

    private Queue<Integer> writeQueue = new LinkedList<>();
    private Queue<Integer> readQueue = new LinkedList<>();
    private final Lock writeLock = new ReentrantLock();
    private final List<ProduceTask> taskList = new LinkedList<>();

    private void swap() {
        if (writeQueue.size() > 100 || checkProduceTaskOk()) {
            writeLock.lock();
            try {

                Queue<Integer> tmp = writeQueue;
                writeQueue = readQueue;
                readQueue = tmp;
            } finally {
                writeLock.unlock();
            }
        }
    }

    private void produce(Integer e) {
        writeLock.lock();
        try {
            writeQueue.offer(e);
        } finally {
            writeLock.unlock();
        }
    }

    private Integer consume() {
        return readQueue.poll();
    }

    private Integer consumeWithLock() {
        writeLock.lock();
        try {
            return writeQueue.poll();
        } finally {
            writeLock.unlock();
        }
    }

    private boolean checkProduceTaskOk() {
        return !taskList.isEmpty() && taskList.stream().allMatch(ProduceTask::isOk);
    }

    /**
     * @author lidelin.
     */
    private class ProduceTask implements Runnable {

        private int count = 10000;
        @Getter
        private boolean ok = false;

        private final CountDownLatch latch;

        public ProduceTask(CountDownLatch latch, int count) {
            this.latch = latch;
            this.count = count;
        }

        @Override
        public void run() {
            int loop = 0;
            while (loop++ < count) {
                try {
                    TimeUnit.MILLISECONDS.sleep(1);
                } catch (InterruptedException ignored) {

                }
                produce(ThreadLocalRandom.current().nextInt());
            }
            ok = true;
            latch.countDown();
        }
    }

    private class ConsumeTask implements Runnable {

        private final CountDownLatch latch;

        private final boolean lock;
        private final List<Long> summary = new LinkedList<>();

        public ConsumeTask(CountDownLatch latch, boolean lock) {
            this.latch = latch;
            this.lock = lock;
        }

        @Override
        public void run() {

            while (true) {
                long start = System.currentTimeMillis();
                Integer e = lock ? consumeWithLock() : consume();
                if (e == null) {
                    if (!lock) {
                        swap();
                    }
                    e = lock ? consumeWithLock() : consume();
                    if (e == null && checkProduceTaskOk() && readQueue.isEmpty() && writeQueue.isEmpty()) {
                        break;
                    }
                }
                if (e != null) {
                    summary.add(System.currentTimeMillis() - start);
                }
            }
            latch.countDown();
        }

        public void printCost() {
            StringBuilder builder = new StringBuilder();
            IntSummaryStatistics stats = summary.stream().mapToInt(Long::intValue).summaryStatistics();
            builder.append("summary = ")
                    .append(stats.getSum())
                    .append("\n")
                    .append("max = ")
                    .append(stats.getMax())
                    .append("\n")
                    .append("min = ")
                    .append(stats.getMin())
                    .append("\n")
                    .append("avg = ")
                    .append(stats.getAverage()).append("\n");
            System.out.println(builder);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        DoubleBufferBenchMark doubleBufferBenchMark = new DoubleBufferBenchMark();
        int produceCount = 10;
        CountDownLatch latch = new CountDownLatch(produceCount + 1);
        ConsumeTask consumeTask = doubleBufferBenchMark.new ConsumeTask(latch, false);
        new Thread(consumeTask).start();

        for (int i = 0; i < produceCount; i++) {
            ProduceTask produceTask = doubleBufferBenchMark.new ProduceTask(latch, 10000);
            doubleBufferBenchMark.taskList.add(produceTask);
            new Thread(produceTask).start();
        }
        latch.await();
        consumeTask.printCost();

        latch = new CountDownLatch(produceCount + 1);
        consumeTask = doubleBufferBenchMark.new ConsumeTask(latch, true);
        new Thread(consumeTask).start();

        for (int i = 0; i < produceCount; i++) {
            ProduceTask produceTask = doubleBufferBenchMark.new ProduceTask(latch, 10000);
            doubleBufferBenchMark.taskList.add(produceTask);
            new Thread(produceTask).start();
        }
        latch.await();
        consumeTask.printCost();

    }

}
