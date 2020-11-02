package nil.ed.test.springboot.bean.conditional;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import nil.ed.anno.MyCondition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author delin10
 * @since 2020/7/7
 **/
@Order(value = 99)
@Configuration
public class BeanConfig1 {

    @Bean
    @MyCondition
    public TestClass testClass() {
        return new TestClass("testClass");
    }

    public static void main(String[] args) {
        /*
         bit：21676028
         mb: 2.58
         */
        BloomFilter<String> filter = BloomFilter.create(Funnels.stringFunnel(StandardCharsets.UTF_8), 100_0000, 0.00003);
        for (int i = 0; i < 100_0000; i++) {
            filter.put(ThreadLocalRandom.current().nextInt(1000) + "");
        }
        /*
        756 milli
         */
        String[] arr = new String[100_0000];
        for (int i = 0; i < 100_0000; i++) {
            arr[i] = ThreadLocalRandom.current().nextInt(1000) + "";
        }
        long start = System.currentTimeMillis();

        for (String e : arr) {
            filter.mightContain(e);
        }

        System.out.println(System.currentTimeMillis() - start);
    }
}
