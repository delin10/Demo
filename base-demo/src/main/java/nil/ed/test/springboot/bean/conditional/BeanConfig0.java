package nil.ed.test.springboot.bean.conditional;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author delin10
 * @since 2020/7/7
 **/
@Configuration
public class BeanConfig0 {

    @Bean
    @ConditionalOnBean(value = TestClass.class, name = "testClass")
    public TestClass testClass0() {
        return new TestClass("testClassConditionalOnBean:Name");
    }

    @Bean
    public ProxyConfig.TestObject testObjectTarget() {
        return new ProxyConfig.TestObject();
    }

    @Bean
    public ProxyConfig.TestInterceptor testInterceptor() {
        return new ProxyConfig.TestInterceptor();
    }

}
