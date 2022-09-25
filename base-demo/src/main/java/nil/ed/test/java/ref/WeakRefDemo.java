package nil.ed.test.java.ref;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author lidelin.
 */
public class WeakRefDemo {

    static Map<WeakReference<Object>, String> referenceStringMap = new ConcurrentHashMap<>();

    static ReferenceQueue<Object> queue = new ReferenceQueue<>();

    static AtomicBoolean isAlive = new AtomicBoolean(true);

    static List<List<String>> ls = new LinkedList<>();

    public static void main(String[] args) throws InterruptedException {
        new Thread(new QueueTaker()).start();
        referenceStringMap.put(new WeakReference<>(new Object(), queue), "TEST");
        TimeUnit.SECONDS.sleep(10);
    }

    public static class QueueTaker implements Runnable {
        @Override
        public void run() {
            while (isAlive.get()) {
                try {
                    System.gc();
                    Object obj = queue.remove(1000);
                    referenceStringMap.forEach((k, v) -> {
                        System.out.println(k.get());
                    });
                    if (obj == null) {
                        continue;
                    }

                    System.out.println("Ok take: " + obj);

                    System.out.println(referenceStringMap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
