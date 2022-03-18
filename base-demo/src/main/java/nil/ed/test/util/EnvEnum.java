package nil.ed.test.util;

/**
 * 环境枚举
 * @author wwn
 */
public enum EnvEnum {

    /**
     *
     */
    DAILY("daily", "日常"),
    PRE2("pre2", "二套"),
    PRE6("pre2", "六套"),
    PRE("pre", "UAT"),
    HOST("host", "线上"),
    DEV("dev", "开发")
    ;

    private String code;
    private String desc;

    EnvEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static String getDescByCode(String code) {
        for (EnvEnum sceneEnum : values()) {
            if (sceneEnum.getCode().equals(code)) {
                return sceneEnum.getDesc();
            }
        }
        return null;
    }
}