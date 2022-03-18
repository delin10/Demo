package nil.ed.test.concurrent;

import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;

import nil.ed.test.util.ThreadUtils;

/**
 * @author lidelin.
 */
public class ExchangerDemo {

    public static void main(String[] args) {
        Exchanger<String> exchanger = new Exchanger<>();
        new Thread(() -> {
            while (true) {
                try {
                    ThreadUtils.sleepQuietly(1, TimeUnit.SECONDS);
                    System.out.println("Thread A send A.");
                    String exchangeVal = exchanger.exchange("A");
                    System.out.printf("Thread A get %s.%n", exchangeVal);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(() -> {
            while (true) {
                try {
                    ThreadUtils.sleepQuietly(3, TimeUnit.SECONDS);
                    System.out.println("Thread B send B.");
                    String exchangeVal = exchanger.exchange("B");
                    System.out.printf("Thread B get %s.%n", exchangeVal);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(() -> {
            while (true) {
                try {
                    ThreadUtils.sleepQuietly(1, TimeUnit.SECONDS);
                    System.out.println("Thread C send C.");
                    String exchangeVal = exchanger.exchange("C");
                    System.out.printf("Thread C get %s.%n", exchangeVal);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
