package nil.ed.test.concurrent;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * @author lidelin.
 */
public class LockSupportDemo {

    public static void main(String[] args) {

        Object blocker = new Object();

        Thread thd1 = new Thread(() -> {
            try {
                // 成功唤醒
                int i = 0;
                LockSupport.park(blocker);
                System.out.printf("Park %s%n", i++);
                // 保证线程1先unpark
                TimeUnit.SECONDS.sleep(3);
                LockSupport.park(blocker);

                LockSupport.park(blocker);
                System.out.println(Thread.interrupted());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        Thread thd3 = new Thread(() -> {
            try {
                // 成功唤醒
                int i = 0;
                LockSupport.park(blocker);
                System.out.printf("Park %s%n", i++);
                // 保证线程1先unpark
                TimeUnit.SECONDS.sleep(3);
                LockSupport.park(blocker);

                LockSupport.park(blocker);
                System.out.println(Thread.interrupted());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        Thread thd2 = new Thread(() -> {
            try {
                int i = 0;
                // 保证线程1进入park
                TimeUnit.SECONDS.sleep(3);
                LockSupport.unpark(thd1);
                System.out.printf("UnPark %s%n", i++);
                LockSupport.unpark(thd1);
                System.out.printf("UnPark %s%n", i);
                TimeUnit.SECONDS.sleep(3);
                thd1.interrupt();
            } catch (Exception e) {
                e.printStackTrace();
            }


        });

        thd1.start();
        thd2.start();
    }

}
