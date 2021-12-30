package nil.ed.test.concurrent;

import java.util.concurrent.TimeUnit;

/**
 * @author lidelin.
 */
public class VolatileMultiRead {

    public static volatile int a = 100;

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            int tmp = a;
            a = tmp + 1;
        }).start();

        new Thread(() -> {
            int tmp = a;
            a = tmp + 1;
        }).start();
        TimeUnit.SECONDS.sleep(2);
        System.out.println(a);
    }

}
