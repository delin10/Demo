package nil.ed.test.core.scope;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class ScopedBeanConfig {

    @Scope(scopeName = ConfigurableListableBeanFactory.SCOPE_PROTOTYPE)
    @Bean
    public Object scopedBean() {
        return new Object();
    }

}
