//package nil.ed.test.zk;
//
//import org.apache.curator.framework.CuratorFramework;
//import org.apache.curator.framework.CuratorFrameworkFactory;
//import org.apache.curator.framework.api.CuratorWatcher;
//import org.apache.curator.framework.recipes.locks.InterProcessMutex;
//import org.apache.curator.retry.ExponentialBackoffRetry;
//import org.apache.zookeeper.AddWatchMode;
//import org.apache.zookeeper.CreateMode;
//import org.apache.zookeeper.WatchedEvent;
//
//import java.nio.charset.StandardCharsets;
//import java.util.concurrent.TimeUnit;
//
///**
// * @author lidelin.
// */
//public class CuratorDemo {
//
//    public static void main(String[] args) throws Exception {
//        System.setProperty("zookeeper.sasl.client", "false");
//        CuratorFramework framework = CuratorFrameworkFactory.builder()
//                .retryPolicy(new ExponentialBackoffRetry(1000, 3))
//                .connectString("127.0.0.1:2181/test")
//                .build();
//        framework.start();
//        framework.watchers().add().withMode(AddWatchMode.PERSISTENT).usingWatcher((CuratorWatcher) (WatchedEvent e) -> {
//            System.out.println(e);
//            System.out.println(e);
//        }).forPath("/xx");
//
//        String path = framework.create().withMode(CreateMode.PERSISTENT_SEQUENTIAL).forPath("/tt");
//        String path2 = framework.create().withMode(CreateMode.PERSISTENT_SEQUENTIAL).forPath("/tt");
//        System.out.println("pth = " + path);
//        System.out.println("pth2 = " + path2);
//        framework.getChildren().forPath("/").forEach(pth -> {
//            try {
//                if (!pth.startsWith("tt")) {
//                    return;
//                }
//                System.out.println("delete " + pth);
//                framework.delete().forPath("/" + pth);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        });
//        for (int i = 0; i < 2; i++) {
//        final int finalIndex = i;
//            System.out.println("First Create: " + finalIndex);
//            try {
//                String ret = framework.create().creatingParentContainersIfNeeded().withProtection().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath("/test/tt/lck/lock-");
//                System.out.println("ret: " + ret);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            System.out.println("First Created: " + finalIndex);
//        }
//
//        TimeUnit.SECONDS.sleep(10);
//        // 分布式锁
//        for (int i = 0; i < 2; i++) {
//            int finalI = i;
//            new Thread(() -> {
//                InterProcessMutex lock = new InterProcessMutex(framework, "/lock");
//                try {
//                    lock.acquire();
//                    System.out.println("lock: " + finalI);
//                    TimeUnit.SECONDS.sleep(20);
//                } catch (Exception e) {
//
//                } finally {
//                    try {
//                        lock.release();
//                    } catch (Exception e) {
//
//                    }
//                }
//            }).start();
//        }
//        TimeUnit.SECONDS.sleep(10);
//    }
//
//}
