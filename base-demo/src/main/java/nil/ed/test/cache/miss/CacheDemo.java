package nil.ed.test.cache.miss;

import java.util.LongSummaryStatistics;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;

/**
 * @author lidelin.
 */
public class CacheDemo {

    private static final LoadingCache<String, Object> CACHE = Caffeine.newBuilder()
            .maximumSize(100)
            .expireAfterWrite(30, TimeUnit.SECONDS)
            .build(k -> load());

    private static final AtomicLong COUNTER = new AtomicLong();

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        int total = 3000;
        ThreadPoolExecutor executor = new ThreadPoolExecutor(total, total, 4, TimeUnit.DAYS, new LinkedBlockingQueue<>(total));
        CompletableFuture[] fs = new CompletableFuture[total];
        long[] cost = new long[total];
        for (int i = 0; i < total; i++) {
            int index = i;
            fs[i] = CompletableFuture.runAsync(() -> {
                long s = System.currentTimeMillis();
                CACHE.get("TEST");
                cost[index] = System.currentTimeMillis() - s;
            }, executor);
        }
        CompletableFuture.allOf(fs).get();
        LongSummaryStatistics statistics = LongStream.of(cost).summaryStatistics();
        System.out.printf("max: %s, min: %s, avg: %.2f%n", statistics.getMax(), statistics.getMin(), statistics.getAverage());
        System.out.printf("miss数: %s, miss率: %.2f ", COUNTER.get(), 1.0 * COUNTER.get() / total);
        executor.shutdownNow();
    }


    public static String load() {
        COUNTER.incrementAndGet();
        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "ok";
    }
}
