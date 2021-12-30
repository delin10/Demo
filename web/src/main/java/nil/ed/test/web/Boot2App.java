package nil.ed.test.web;

import nil.ed.test.springboot.aop.AopConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author lidelin.
 */
@SpringBootApplication
@EnableTransactionManagement
@EnableAspectJAutoProxy(proxyTargetClass = true)
@Import({AopConfig.class})
public class Boot2App {

    public static void main(String[] args) {
        SpringApplication.run(Boot2App.class, args);
    }

}
