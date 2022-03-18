package nil.ed.test.java;

import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TransferQueue;

/**
 * @author lidelin.
 */
public class TransferQueueDemo {

    public static void main(String[] args) throws InterruptedException {
        TransferQueue<String> queue = new LinkedTransferQueue<>();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    System.out.println(queue.take());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
        new Thread(() -> {
            while (true) {
                try {
                    queue.transfer("Hello Transfer");
                    System.out.println("Transfer ok");
                    TimeUnit.SECONDS.sleep(1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(() -> {
            while (true) {
                try {
                    queue.offer("Hello offer");
                    System.out.println("offer ok");
                    TimeUnit.SECONDS.sleep(1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(() -> {
            while (true) {
                try {
                    queue.put("put offer");
                    System.out.println("put ok");
                    TimeUnit.SECONDS.sleep(1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
