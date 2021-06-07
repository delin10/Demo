package nil.ed.test.dynamic.definition.test;

import nil.ed.test.dynamic.definition.DynamicBean;
import nil.ed.test.dynamic.definition.RefreshableSingletonBeanSource;
import nil.ed.test.dynamic.definition.SimpleBeanSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lidelin.
 */
@Configuration
public class DynamicBeanConfig {

    @Bean
    public DynamicBean dynamicBean0() {
        SimpleBeanSource beanSource = new SimpleBeanSource(Object.class);
        return new DynamicBean(beanSource);
    }

    @Bean
    public DynamicBean dynamicBean1() {
        return new DynamicBean(refreshableSingletonBeanSource());
    }

    @Bean
    public RefreshableSingletonBeanSource refreshableSingletonBeanSource() {
        return new RefreshableSingletonBeanSource(ClosableBean.class, "destroy");
    }

}
