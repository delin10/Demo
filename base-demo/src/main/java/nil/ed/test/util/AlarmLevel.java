package nil.ed.test.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 报警等级.
 * @author lidelin,
 */
@Getter
@AllArgsConstructor
public enum AlarmLevel {
    /**
     * 报警等级.
     */
    RED("#FF0000"),
    GREEN("#008B45"),
    BLACK("NIL") {
        @Override
        public String render(Object val) {
            return val == null ? "" : String.valueOf(val);
        }
    }
    ;
    private final String color;

    public String render(Object val) {
        if (val == null) {
            return "";
        }
        return wrapFontColor(String.valueOf(val), color);
    }

    private String wrapFontColor(String text, String color) {
        return String.format("<font color=%s>%s</font>", color, text);
    }
}