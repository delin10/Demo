package nil.ed.test.core;

import org.springframework.context.annotation.Bean;

/**
 * @author lidelin.
 */
public class NonConfiguration {

    @Bean("definedInNonConfiguration")
    public Object definedInNonConfiguration() {
        return new Object();
    }
    
}
