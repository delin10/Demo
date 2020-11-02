package nil.ed.test.dubbo.provider;

import nil.ed.test.dubbo.api.CallMe;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.ServiceConfig;

import java.util.concurrent.CountDownLatch;

public class Application {
    public static void main(String[] args) throws InterruptedException {
        ServiceConfig<CallMe> service = new ServiceConfig<>();
        String zookeeperHost = "127.0.0.1";
        service.setApplication(new ApplicationConfig("first-dubbo-provider"));
        service.setRegistry(new RegistryConfig("zookeeper://" + zookeeperHost + ":2181"));
        service.setInterface(CallMe.class);
        service.setRef(new CallMeImpl());
        service.export();

        System.out.println("dubbo service started");
        new CountDownLatch(1).await();
    }
}
