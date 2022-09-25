//package nil.ed.test.zk;
//
//import com.alibaba.fastjson.JSON;
//import lombok.Data;
//import org.apache.commons.collections4.CollectionUtils;
//import org.apache.curator.framework.CuratorFramework;
//import org.apache.curator.framework.recipes.queue.DistributedQueue;
//import org.apache.curator.framework.recipes.queue.QueueBuilder;
//import org.apache.zookeeper.AddWatchMode;
//import org.apache.zookeeper.CreateMode;
//import org.apache.zookeeper.KeeperException;
//import org.apache.zookeeper.Watcher;
//
//import java.util.List;
//
///**
// * @author lidelin.
// */
//public class QueueDemo {
//
//    public static class ZkQueue {
//
//        private final CuratorFramework framework;
//
//        private final String basePath;
//
//        private volatile List<String> childrenCache;
//
//        private final Watcher watcher;
//
//        public ZkQueue(CuratorFramework framework, String basePath) {
//            this.framework = framework;
//            this.basePath = basePath;
//            this.watcher = event -> {
//                try {
//                    if (event.getType() == Watcher.Event.EventType.NodeChildrenChanged) {
//                        fetchCache();
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            };
//            this.framework.watchers().add().withMode(AddWatchMode.PERSISTENT).usingWatcher(watcher);
//        }
//
//        public Elem getFirst() {
//            if (CollectionUtils.isEmpty(childrenCache)) {
//                return null;
//            }
//
//            for (String child : childrenCache) {
//                try {
//                    DistributedQueue<String> queue = QueueBuilder.(framework, basePath);
//                    this.framework.delete().guaranteed().forPath(child);
//
//                } catch (KeeperException.NoNodeException e) {
//                    e.printStackTrace();
//                } catch (Exception e) {
//                    throw new RuntimeException(e);
//                }
//
//            }
//        }
//
//        public String push(Elem elem) throws Exception {
//            return framework.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath(basePath + "/queue", elem.getBytes());
//        }
//
//        public void fetchCache() throws Exception {
//            this.childrenCache = framework.getChildren().forPath(basePath);
//        }
//
//    }
//
//    @Data
//    public static class Elem {
//        private String val;
//        private String timestamp;
//
//        public byte[] getBytes() {
//            return JSON.toJSONBytes(this);
//        }
//    }
//
//}
