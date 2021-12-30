package nil.ed.test.concurrent;

import java.util.LinkedList;
import java.util.List;

/**
 * @author lidelin.
 */
public class FakeShareDemo {

    private static final int THD_CNT = 200;
    private static final int LOOP_CNT = 50_000_000;
    private static final long[] DATA = new long[THD_CNT];

    private static final long[] DATA2 = new long[THD_CNT * 50];

    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();
        List<Thread> thds = new LinkedList<>();
        for (int i = 0; i < THD_CNT; ++i) {
            final int finalVal = i;
            thds.add(new Thread(() -> {
                while(DATA[finalVal] < LOOP_CNT) {
                    DATA[finalVal]++;
                }
            }));
            thds.get(thds.size() - 1).start();
        }
        for (Thread thd : thds) {
            thd.join();
        }
        System.out.println("Cost: " + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        List<Thread> thds2 = new LinkedList<>();
        for (int i = 0; i < THD_CNT; ++i) {
            final int finalVal = i;
            thds2.add(new Thread(() -> {
                while(DATA2[finalVal * 50] < LOOP_CNT) {
                    DATA2[finalVal * 50]++;
                }
            }));
            thds2.get(thds2.size() - 1).start();
        }
        for (Thread thd2 : thds2) {
            thd2.join();
        }
        System.out.println("Cost: " + (System.currentTimeMillis() - start));
    }
}
