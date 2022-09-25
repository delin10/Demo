package nil.ed.test.redis;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.concurrent.TimeUnit;

/**
 * @author lidelin.
 */
public class RedissionDemo {

    public static void main(String[] args) throws InterruptedException {
        // 1. Create config object
        Config config = new Config();
        config.useSingleServer()
                // use "rediss://" for SSL connection
                .setAddress("redis://127.0.0.1:6379");
        RedissonClient redisson = Redisson.create(config);
        for (int i = 0; i < 3; i++) {
            int finalI = i;
            new Thread(() -> {
                RLock rLock = redisson.getLock("test");
                try {
                    rLock.lock(5, TimeUnit.SECONDS);
                    System.out.println("Locked..." + finalI);
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    rLock.unlock();
                    System.out.println("unlocked..." + finalI);
                }
            }).start();
        }
        TimeUnit.SECONDS.sleep(10);
        redisson.shutdown();
    }

}
