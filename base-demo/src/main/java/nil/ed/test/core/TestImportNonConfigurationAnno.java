package nil.ed.test.core;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author lidelin.
 */
@Import(NonConfiguration.class)
@Configuration
public class TestImportNonConfigurationAnno {

}
