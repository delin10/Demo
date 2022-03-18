package nil.ed.test.easyexcel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.HeadFontStyle;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @author lidelin.
 */
@Data
@Accessors(chain = true)
@ColumnWidth(value = 50)
@HeadRowHeight(value = 15)
@HeadFontStyle(fontHeightInPoints = 10)
public class DemoModel {

    @ExcelProperty("第一列")
    private String col1;

    @ExcelProperty("第一列")
    private String col11;

    @ExcelProperty("第二列")
    private Long col2;

    @ExcelProperty("第三列")
    private Double col3;

    @ExcelProperty("第四列")
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    private LocalDateTime col4;

}
