package nil.ed.test.easyexcel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupManualImportExcelTemplateDTO {

    @ExcelProperty(value = "sellerId", order = 1)
    private Long sellerId;
}
