package nil.ed.test.util;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

/**
 * Created by allan on 2017/11/21.
 *
 * @author 江新
 * @date 2017/11/21
 */
@Component
public class ExportEnvConfig {

    private static ExportEnvConfig instance;

    private String env;

    public static ExportEnvConfig getInstance() {
        return instance;
    }

    @PostConstruct
    public void init() {
        env = System.getProperty("spring.profiles.active");
        instance = this;
    }

    public boolean isDev() {
        return "dev".equals(getEnv());
    }

    public boolean isDaily() {
        return "testing".equals(this.env) || "project".equals(this.env);
    }

    public boolean isProject() {
        return "project".equals(this.env);
    }

    public boolean isStaging2() {
        return "staging2".equals(this.env);
    }

    public boolean isStaging() {
        return "staging".equals(this.env);
    }

    public boolean isProduction() {
        return "production".equals(this.env);
    }

    public String getEnv() {
        if (isDaily() || isProject()) {
            return "daily";
        }
        if (isStaging()) {
            return "pre";
        }
        if (isStaging2()) {
            return "pre2";
        }
        if (isProduction()) {
            return "online";
        }
        return "dev";
    }
}
