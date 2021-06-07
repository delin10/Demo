package nil.ed.test.core.scope;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author lidelin.
 */
@Component
@Scope(scopeName = ConfigurableListableBeanFactory.SCOPE_PROTOTYPE)
public class ScopeComponent {

    {
        System.out.println("ok");
    }
}
