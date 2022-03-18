package nil.ed.test.concurrent;

import java.util.concurrent.TimeUnit;

/**
 * @author lidelin.
 */
public class ObjectWaitNotifyDemo {

    public static void main(String[] args) {

        Object blocker = new Object();

        Thread thd1 = new Thread(() -> {
            try {
                // 成功唤醒
                int i = 0;
                synchronized (blocker) {
                    blocker.wait();
                }
                System.out.printf("Park %s%n", i++);
                // 保证线程1先unpark
                TimeUnit.SECONDS.sleep(3);
                synchronized (blocker) {
                    blocker.wait();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        Thread thd2 = new Thread(() -> {
            try {
                int i = 0;
                // 保证线程1进入park
                TimeUnit.SECONDS.sleep(3);
                synchronized (blocker) {
                    blocker.notifyAll();
                }
                System.out.printf("UnPark %s%n", i++);
                synchronized (blocker) {
                    blocker.notifyAll();
                }
                System.out.printf("UnPark %s%n", i);
            } catch (Exception e) {
                e.printStackTrace();
            }


        });

        thd1.start();
        thd2.start();
    }

}
