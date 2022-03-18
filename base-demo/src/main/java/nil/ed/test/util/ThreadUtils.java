package nil.ed.test.util;

import java.util.concurrent.TimeUnit;

import nil.ed.test.util.function.ExceptionRunnable;

/**
 * @author lidelin.
 */
public class ThreadUtils {

    public static Thread startThread(Runnable runnable) {
        Thread t = new Thread(runnable);
        t.start();
        return t;
    }

    public static Thread startThreadWithExceptionAutoCatch(ExceptionRunnable runnable) {
        Thread t = new Thread(() -> {
            try {
                runnable.run();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        t.start();
        return t;
    }

    public static void sleepQuietly(long timeout, TimeUnit unit) {
        try {
            unit.sleep(timeout);
        } catch (InterruptedException ignored) {

        }
    }

    public static void main(String[] args) {
        System.out.println(System.getProperty("java.io.tmpdir"));
    }

}
