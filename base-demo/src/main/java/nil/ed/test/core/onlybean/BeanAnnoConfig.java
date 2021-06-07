package nil.ed.test.core.onlybean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @author lidelin.
 */
@Configuration
public class BeanAnnoConfig {

    @Bean(value = {"beanName1", "beanName2"})
    public Object beanDef() {
        return new Object();
    }

    @Bean({"dupBean", "dupBean1"})
    public Object beanDefDup1() {
        return new Object();
    }

    @Bean(value = {"dupBean", "dupBean2"})
    @Primary
    public Object beanDefDup2() {
        return new Object();
    }

    @Bean(value = {"dupBean", "dupBean3"})
    @Primary
    public Object beanDefDup3() {
        return new Object();
    }

}
