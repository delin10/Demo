package nil.ed.test.springboot.bean.conditional;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Indexed;

@Component(value = "ssss")
@Indexed
public class CommandLineTest implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
    }
}
