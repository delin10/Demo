package nil.ed.test.zk;

import lombok.Getter;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.data.Stat;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author lidelin.
 */
public class BarrierDemo {

    public static void main(String[] args) throws Exception {
        CuratorFramework framework = CuratorFrameworkFactory.builder()
                .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                .connectString("127.0.0.1:2181/test")
                .build();
        framework.start();
        Runtime.getRuntime().addShutdownHook(new Thread(framework::close));
        String ret = framework.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath("/BarrierDemo/barrier");
        System.out.println("Create ret = " + ret);
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
                System.out.println("Deleting...");
                framework.delete().forPath("/BarrierDemo/barrier");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                try {
                    System.out.println("Barrier start");
                    new CustomBarrier(framework, "/BarrierDemo/CustomBarrier").barrier();
                    System.out.println("Barrier end");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }

        for (int i = 0; i < 6; i++) {
            final int finalI = i;
            new Thread(() -> {
                try {
                    System.out.println("Count down start");
                    TimeUnit.SECONDS.sleep(finalI);
                    new CountDownBarrier(framework, "/BarrierDemo/CountDownBarrier", 6).barrier();
                    System.out.println("Count down end");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }

        TimeUnit.SECONDS.sleep(10);
        for (int i = 0; i < 6; i++) {
            final int finalI = i;
            new Thread(() -> {
                try {
                    CycleBarrier cycleBarrier = new CycleBarrier(framework,  "/BarrierDemo/CycleBarrier", 6);
                    System.out.println("CycleBarrier start");
                    cycleBarrier.entry();
                    TimeUnit.SECONDS.sleep(finalI);
                    cycleBarrier.exit();
                    System.out.println("CycleBarrier end");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    public static class CycleBarrier {
        private final Object barrierObjectNotification = new Object();
        private final String barrierPath;
        private final CuratorFramework framework;
        private final CountDownBarrier enterBarrier;
        private String countDownPath;
        private final CuratorWatcher watcher = e -> {
            if (e.getType() == Watcher.Event.EventType.NodeChildrenChanged) {
                synchronized (barrierObjectNotification) {
                    barrierObjectNotification.notifyAll();
                }
            }
        };

        public CycleBarrier(CuratorFramework framework, String barrierPath, int water) {
            this.enterBarrier = new CountDownBarrier(framework, barrierPath, water);
            this.barrierPath = barrierPath;
            this.framework = framework;
        }

        public void entry() throws Exception {
            enterBarrier.barrier();
            this.countDownPath = enterBarrier.getCountDownPath();
        }

        public void exit() throws Exception {
            framework.delete().guaranteed().forPath(countDownPath);
            synchronized (barrierObjectNotification) {
                while (true) {
                    List<String> children = framework.getChildren().usingWatcher(watcher).forPath(barrierPath);
                    if (CollectionUtils.isNotEmpty(children)) {
                        // 一定要设置超时，不然可能会错过事件.
                        barrierObjectNotification.wait(1000);
                        continue;
                    }
                    break;
                }
            }
        }
    }

    public static class CountDownBarrier {
        private final Object barrierObjectNotification = new Object();
        private final String barrierPath;
        private final CuratorFramework framework;
        private final int count;
        private final CuratorWatcher watcher = e -> {
            if (e.getType() == Watcher.Event.EventType.NodeCreated) {
                synchronized (barrierObjectNotification) {
                    barrierObjectNotification.notifyAll();
                }
            }
        };
        @Getter
        private String countDownPath;

        public CountDownBarrier(CuratorFramework framework, String barrierPath, int count) {
            this.barrierPath = barrierPath;
            this.framework = framework;
            this.count = count;
        }

        public void barrier() throws Exception {
            this.countDownPath = framework.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath(barrierPath + "/CountDownBarrier-");
            synchronized (barrierObjectNotification) {
                while (true) {
                    List<String> children = framework.getChildren().usingWatcher(watcher).forPath(barrierPath);
                    System.out.println(children);
                    if (children != null && children.size() < count) {
                        // 一定要设置超时，不然可能会错过事件.
                        barrierObjectNotification.wait(1000);
                        framework.watchers().remove(watcher);
                        continue;
                    }
                    framework.watchers().remove(watcher);
                    break;
                }
            }
        }
    }

    public static class CustomBarrier {
        private final Object barrierObjectNotification = new Object();
        private final String barrierPath;
        private final CuratorFramework framework;

        public CustomBarrier(CuratorFramework framework, String barrierPath) {
            this.barrierPath = barrierPath;
            this.framework = framework;
        }

        public void barrier() throws Exception {
            synchronized (barrierObjectNotification) {
                while (true) {
                    Stat existRet = framework.checkExists().usingWatcher((CuratorWatcher) e -> {
                        if (e.getType() == Watcher.Event.EventType.NodeDeleted) {
                            synchronized (barrierObjectNotification) {
                                barrierObjectNotification.notifyAll();
                            }
                        }
                    }).forPath(barrierPath);
                    if (existRet != null) {
                        barrierObjectNotification.wait();
                        continue;
                    }
                    break;
                }
            }
        }
    }

}
