package nil.ed.test.es.model;

import java.util.Date;

import lombok.Data;
import lombok.experimental.Accessors;

/**
* @author easywork.
*/
@Data
@Accessors(chain = true)
public class EsTable {

    /**
     * 无意义主键.
     */
    private Long id;

    /**
     * 内容.
     */
    private String content;

    /**
     * 开始时间.
     */
    private Long startTime;

    /**
     * 结束时间.
     */
    private Long endTime;

    /**
     * 大文本.
     */
    private String bigContent;

    /**
     * 测试时间.
     */
    private Date testTime;

}
