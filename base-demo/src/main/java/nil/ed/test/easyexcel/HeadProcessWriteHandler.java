package nil.ed.test.easyexcel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.handler.SheetWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;

public class HeadProcessWriteHandler implements SheetWriteHandler {
    @Override
    public void afterSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {
        for (Head h : writeWorkbookHolder.getExcelWriteHeadProperty().getHeadMap().values()) {
            ExcelProperty excelProperty = h.getField().getAnnotation(ExcelProperty.class);
            // 通过ExcelProperty注解配置表头配置规则
            String[] headName = excelProperty.value();
            // 处理上下文，填充真实表头
            // process h
            // h.setHeadNameList(Collections.singletonList(""));
        }
    }
}
