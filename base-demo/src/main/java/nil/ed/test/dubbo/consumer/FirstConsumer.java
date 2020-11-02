package nil.ed.test.dubbo.consumer;

import nil.ed.test.dubbo.api.CallMe;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;

public class FirstConsumer {
    public static void main(String[] args) {
        String zookeeperHost = "127.0.0.1";
        ReferenceConfig<CallMe> reference = new ReferenceConfig<>();
        reference.setApplication(new ApplicationConfig("first-dubbo-consumer"));
        reference.setRegistry(new RegistryConfig("zookeeper://" + zookeeperHost + ":2181"));
        reference.setInterface(CallMe.class);
        CallMe service = reference.get();
        int message = service.add(1, 2);
        System.out.println(message);
    }

}
