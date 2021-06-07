package nil.ed.test.core;

import nil.ed.test.springcloud.feign.origin.BootApp;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * @author lidelin.
 */
@SpringBootApplication(scanBasePackages = "nil.ed.test.core")
public class NonConfigurationBoot {
    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(NonConfigurationBoot.class, args);
        System.out.println(applicationContext.getBean("definedInNonConfiguration"));
    }
}
