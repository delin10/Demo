package nil.ed.test.es.condition;

import lombok.Getter;
import lombok.Setter;

/**
 * @author lidelin.
 */
public class PageCondition {

    @Getter
    @Setter
    private int pageNo = 1;
    @Getter
    @Setter
    private int pageSize = 20;

    public int getStart() {
        return (pageNo - 1) * pageSize;
    }
}
