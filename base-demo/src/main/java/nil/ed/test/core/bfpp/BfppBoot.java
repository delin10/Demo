package nil.ed.test.core.bfpp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * @author lidelin.
 */
@SpringBootApplication(scanBasePackages = "nil.ed.test.core.bfpp")
public class BfppBoot {
    public static void main(String[] args) {
        SpringApplication.run(BfppBoot.class, args);
    }
}
