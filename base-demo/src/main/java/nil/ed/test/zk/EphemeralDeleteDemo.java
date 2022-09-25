package nil.ed.test.zk;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

import java.util.concurrent.TimeUnit;

/**
 * @author lidelin.
 */
public class EphemeralDeleteDemo {

    public static void main(String[] args) throws Exception {
        CuratorFramework framework = CuratorFrameworkFactory.builder()
                .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                .connectString("127.0.0.1:2181/test")
                .build();
        framework.start();
        CuratorFramework framework2 = CuratorFrameworkFactory.builder()
                .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                .connectString("127.0.0.1:2181/test")
                .build();
        framework2.start();

       String path = framework.create().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath("/EphemeralDeleteDemo");
        framework2.watchers().add().usingWatcher((CuratorWatcher) e -> {
            System.out.println(e);
            System.out.println(e);
        }).forPath(path);
        framework.close();
        TimeUnit.SECONDS.sleep(20);
    }

}
