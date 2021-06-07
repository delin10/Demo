package nil.ed.test.springboot.db.config;

import java.util.Map;

/**
 * @author lidelin.
 */
public interface ConfigSource {

    Map<String, Object> getConfigs();

}
