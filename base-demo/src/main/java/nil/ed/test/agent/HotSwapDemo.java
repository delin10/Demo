package nil.ed.test.agent;

import java.util.concurrent.TimeUnit;

/**
 * @author lidelin.
 */
public class HotSwapDemo {

    public static void main(String[] args) throws InterruptedException {
        HotSwapTestModel model = new HotSwapTestModel();
        Thread t = new Thread(() -> {
            while (true) {
                try {
                    TimeUnit.SECONDS.sleep(3);
                    System.out.println(model);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
        t.  join();
    }

}
