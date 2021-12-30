package nil.ed.test.easyexcel;

import com.alibaba.excel.write.handler.AbstractCellWriteHandler;
import com.alibaba.excel.write.handler.context.CellWriteHandlerContext;
import org.apache.poi.ss.usermodel.*;

/**
 * @author lidelin.
 */
public class DemoModelCellWriteHandler extends AbstractCellWriteHandler {

    private static final String COL_2_FIELD = "col2";
    private static final String COL_3_FIELD = "col3";

    @Override
    public void afterCellDispose(CellWriteHandlerContext context) {
        Workbook workbook = context.getWriteSheetHolder().getParentWriteWorkbookHolder().getWorkbook();
        CellStyle cellStyle = workbook.createCellStyle();
        DataFormat format = workbook.createDataFormat();
        cellStyle.setAlignment(HorizontalAlignment.RIGHT);
        if (COL_2_FIELD.equals(context.getHeadData().getFieldName())) {
            context.getCell().setCellType(CellType.NUMERIC);
            cellStyle.setDataFormat(format.getFormat("#,##0"));
        } else if (COL_3_FIELD.equals(context.getHeadData().getFieldName())) {
            context.getCell().setCellType(CellType.NUMERIC);
            cellStyle.setDataFormat(format.getFormat("0.00000000"));
        }
        context.getCell().setCellStyle(cellStyle);
    }
}
