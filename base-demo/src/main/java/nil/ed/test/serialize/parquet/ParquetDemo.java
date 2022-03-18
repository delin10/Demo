package nil.ed.test.serialize.parquet;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

import lombok.Getter;
import org.apache.hadoop.fs.Path;
import org.apache.parquet.example.data.Group;
import org.apache.parquet.example.data.simple.SimpleGroup;
import org.apache.parquet.hadoop.ParquetReader;
import org.apache.parquet.hadoop.ParquetWriter;
import org.apache.parquet.hadoop.example.ExampleParquetWriter;
import org.apache.parquet.hadoop.example.GroupReadSupport;
import org.apache.parquet.io.ParquetDecodingException;
import org.apache.parquet.schema.MessageType;


/**
 * @author lidelin.
 */
public class ParquetDemo {

    public static void main(String[] args) throws IOException, InterruptedException {
        BlockingQueue<Group> queue = new LinkedBlockingQueue<>(100000);
        AtomicLong counter = new AtomicLong();
        try {
            AtomicBoolean alive = new AtomicBoolean(true);
            new Thread(() -> {
                try {
                    readParquet(new File("data/parquet/part-3-6.parquet").getAbsolutePath(), 10000, groups -> {
                        for (Group g : groups) {
                            try {
                                queue.put(g);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        System.out.println("offer");
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    alive.set(false);
                }
            }).start();
            int size = 1;
            List<Future> fs = new LinkedList<>();
            for (int i = 0; i < size; i++) {
                final String outPath = new File("").getAbsolutePath() + "/data/parquet/tmp/part-3-6.parquet-correct-" + i;
                fs.add(CompletableFuture.runAsync(new WriteTask(queue, outPath, alive)));
            }
            for (Future f : fs) {
                f.get();
            }
            readParquet(new File("data/parquet/part-3-6.parquet-correct").getAbsolutePath(), 10000, groups -> {
                counter.addAndGet(groups.size());
            });
        } catch (ExecutionException e) {
            e.printStackTrace();
        } finally {
            System.out.println(counter.get());
        }

    }

    public static class WriteTask implements Runnable {

        private final WriteContextHolder holder = new WriteContextHolder();
        private final BlockingQueue<Group> queue;
        @Getter
        private final String outPath;
        private final AtomicBoolean readerStatus;
        public WriteTask(BlockingQueue<Group> queue, String outPath, AtomicBoolean readerStatus) {
            this.queue = queue;
            this.outPath = outPath;
            this.readerStatus = readerStatus;
        }

        @Override
        public void run() {
            try {
                while (readerStatus.get() || !queue.isEmpty()) {
                    List<Group> groups = new LinkedList<>();
                    int batchSize = 1000;
                    while (!queue.isEmpty() && batchSize-- > 0) {
                        Group group = null;
                        if ((group = queue.poll()) == null) {
                            break;
                        }
                        groups.add(group);
                    }
                    if (groups.isEmpty()) {
                        try {
                            TimeUnit.MILLISECONDS.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        continue;
                    }
                    System.out.println(groups.get(0).getType().getClass());
                    if (holder.groupType == null || holder.writer == null) {
                        Path outFile = new Path(outPath);
                        holder.groupType = (MessageType) groups.get(0).getType();
                        try {
                            holder.writer = ExampleParquetWriter.builder(outFile).withType((MessageType) groups.get(0).getType()).build();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    try {
                        writeParquet(groups, holder.writer);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("finished");
            } finally {
                if (holder.writer != null) {
                    try {
                        holder.writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static class WriteContextHolder {
        private MessageType groupType = null;
        private ParquetWriter<Group> writer = null;
    }

    public static void readParquet(String filePath, int batchSize, Consumer<List<Group>> consumer) throws IOException {
        Path inFile = new Path(filePath);
        ParquetReader.Builder<Group> builder = ParquetReader.builder(new GroupReadSupport(),inFile);
        List<Group> groups = new LinkedList<>();
        try (ParquetReader<Group> reader = builder.build()) {
            SimpleGroup group;
            int counter = 0;
            while ((group = (SimpleGroup) reader.read()) != null) {
                groups.add(group);
                if (groups.size() > batchSize) {
                    System.out.println(counter += groups.size());
                    consumer.accept(groups);
                    groups.clear();
                }
            }
        } catch (ParquetDecodingException decodingException) {
            decodingException.printStackTrace();
        } finally {
            if (groups.size() != 0) {
                consumer.accept(groups);
                groups.clear();
            }
        }
    }

    public static void writeParquet(List<Group> groups, ParquetWriter<Group> writer) throws IOException {
        for (Group group : groups) {
            writer.write(group);
        }
        System.out.println("writeParquet ok");
    }

}
