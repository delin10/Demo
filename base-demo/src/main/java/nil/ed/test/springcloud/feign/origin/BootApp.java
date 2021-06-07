package nil.ed.test.springcloud.feign.origin;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author lidelin.
 */
@SpringBootApplication
public class BootApp {
    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(BootApp.class, args);
        Thread.sleep(1000);
    }
}
