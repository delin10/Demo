package nil.ed.test.deadlock;

import java.time.Instant;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.annotation.Nonnull;

import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import nil.ed.test.util.FutureUtils;
import nil.ed.test.util.ThreadUtils;
import retrofit2.http.GET;

/**
 * @author lidelin.
 */
public class ConcurrentHashMapDeadLock {

    private static LoadingCache<String, Object> cache = Caffeine.newBuilder()
            .maximumSize(100)
            .expireAfterWrite(30, TimeUnit.SECONDS)
            .refreshAfterWrite(30, TimeUnit.SECONDS)
            .build(new CacheLoader<String, Object>() {
                @Override
                public Object load(@Nonnull String key) throws Exception {
                    return ConcurrentHashMapDeadLock.load(key);
                }

                @Override
                public Object reload(@Nonnull String key, Object oldValue) throws Exception {
                    return load(key);
                }
            });
    private static final BlockingQueue<String> MSG_EXCHANGER = new LinkedBlockingQueue<>();
    private static final AtomicLong CLOCK = new AtomicLong(System.currentTimeMillis());
    private static final AtomicLong GET_RUNNING = new AtomicLong(0);
    private static final AtomicLong LOAD_RUNNING = new AtomicLong(0);

    public static Object load(String key) {
        try {
            FutureUtils.makeFutureLoad(IntStream.range(0, 1000).mapToObj(Integer::valueOf).collect(Collectors.toList()),
                    i -> {
                        String id = key + "[" + i + "]";
                        System.out.println("Load Entry " + id + " At " + Instant.now());
                        GET_RUNNING.incrementAndGet();
                        Thread.currentThread().setName("Load-Thread-" + id);
                        ThreadUtils.sleepQuietly(1, TimeUnit.MILLISECONDS);
                        CLOCK.set(System.currentTimeMillis());
                        MSG_EXCHANGER.offer(id);
                        Thread.currentThread().setName("Free-Thread");
                        GET_RUNNING.decrementAndGet();
                        System.out.println("Load exit "  + id + " At " + Instant.now());
                        return i;
                    }, System.out::println, ForkJoinPool.commonPool()).exec();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return key;
    }

    public static Object get(String key) {
        return cache.get(key);
    }

    public static void testDeadLock() {
        IntStream.range(0, 1000).mapToObj(i -> String.valueOf('a' + i))
                .parallel()
                .map(i -> {
                    System.out.println("Get Entry " + i + " At " + Instant.now());
                    Thread.currentThread().setName("Get-Thread");
                    GET_RUNNING.incrementAndGet();
                    Object obj = ConcurrentHashMapDeadLock.get(i);
                    GET_RUNNING.decrementAndGet();
                    Thread.currentThread().setName("Free-Thread");
                    System.out.println("Get Exit " + i + " At " + Instant.now());
                    return obj;
                })
                .forEach(System.out::println);
        System.out.println("x");
    }

    public static void testException() throws ExecutionException, InterruptedException {
        FutureUtils.makeFutureLoad(IntStream.range(0, 1000).mapToObj(Integer::valueOf).collect(Collectors.toList()),
                i -> {
                    System.out.println("[" + Thread.currentThread().getName() + "] " + "(" + i + ")" + ForkJoinPool.commonPool().getRunningThreadCount());
                    System.out.println("[" + Thread.currentThread().getName() + "] " + "(" + i + ")" + ForkJoinPool.commonPool().getStealCount());
                    System.out.println("[" + Thread.currentThread().getName() + "] " + "(" + i + ")" + ForkJoinPool.commonPool().getQueuedTaskCount());
                    throw new RuntimeException("12345t67890");
                }, System.out::println, ForkJoinPool.commonPool()).exec();
    }

    public static void startCheckClock() {
        new Thread(() -> {
            try {
                String ret;
                while (true) {
                    if (TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - CLOCK.get()) > 10) {
                        System.out.println("卡住了！");
                    }
                    ret = MSG_EXCHANGER.poll(1000, TimeUnit.MILLISECONDS);
                    if (ret != null) {
                        System.out.println("Poll: " + ret);
                    } else {
                        System.out.println("Get running: " + GET_RUNNING.get() + ", Load running: " + LOAD_RUNNING.get());
                        System.out.println("common pool attrs: " + ForkJoinPool.commonPool().getRunningThreadCount() + ", " + ForkJoinPool.commonPool().getStealCount() + ", " + ForkJoinPool.commonPool().getQueuedTaskCount());
                    }
                }
            } catch (Exception ignored) {

            }
        }).start();
    }



    public static void main(String[] args) throws ExecutionException, InterruptedException {
        startCheckClock();
        testDeadLock();
    }

}
