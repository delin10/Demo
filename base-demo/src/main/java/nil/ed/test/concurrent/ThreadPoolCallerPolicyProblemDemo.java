package nil.ed.test.concurrent;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author lidelin.
 */
public class ThreadPoolCallerPolicyProblemDemo {

    public static void main(String[] args) throws InterruptedException {
        threadLocalProblem();
    }

    public static void lowThroughOutput() throws InterruptedException {
        ThreadPoolExecutor exec = new ThreadPoolExecutor(2, 2, 2, TimeUnit.DAYS, new LinkedBlockingDeque<>(1), new BasicThreadFactory.Builder().namingPattern("DEMO-%s").build(), new ThreadPoolExecutor.CallerRunsPolicy());
        long start = System.currentTimeMillis();
        for (int i = 0; i < 5; ++i) {
            int finalI = i;
            TimeUnit.MILLISECONDS.sleep(100);
            exec.submit(() -> {
                try {
                    System.out.println(Thread.currentThread().getName());
                    TimeUnit.SECONDS.sleep(finalI + 2);
                    System.out.println(Thread.currentThread().getName() + "[" + (finalI + 2) + ": " + (System.currentTimeMillis() - start));
                } catch (InterruptedException ignored) {
                }
            });
        }
        exec.shutdown();
    }

    public static void threadLocalProblem() throws InterruptedException {
        ThreadPoolExecutor exec = new ThreadPoolExecutor(2, 2, 2, TimeUnit.DAYS, new LinkedBlockingDeque<>(1), new BasicThreadFactory.Builder().namingPattern("DEMO-%s").build(), new ThreadPoolExecutor.CallerRunsPolicy());
        ThreadLocal<String> local = new ThreadLocal<>();
        String val = "TEST";
        local.set(val);
        for (int i = 0; i < 5; ++i) {
            int finalI = i;
            TimeUnit.MILLISECONDS.sleep(100);
            exec.submit(() -> {
                try {
                    local.set(val);
                    TimeUnit.SECONDS.sleep(finalI);
                } catch (InterruptedException ignored) {
                } finally {
                    local.remove();
                }
            });
        }
        exec.shutdown();
        System.out.printf("final val: %s%n", local.get());
    }

}
