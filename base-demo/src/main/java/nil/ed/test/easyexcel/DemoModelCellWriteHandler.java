package nil.ed.test.easyexcel;

import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.handler.AbstractCellWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.List;

/**
 * @author lidelin.
 */
public class DemoModelCellWriteHandler extends AbstractCellWriteHandler {

    private static final String COL_2_FIELD = "col2";
    private static final String COL_3_FIELD = "col3";

    @Override
    public void afterCellDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, List<CellData> cellDataList, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {
        if (isHead) {
            return;
        }

        Workbook workbook = writeSheetHolder.getParentWriteWorkbookHolder().getWorkbook();
        CellStyle cellStyle = workbook.createCellStyle();
        DataFormat format = workbook.createDataFormat();
        cellStyle.setAlignment(HorizontalAlignment.RIGHT);
        if (COL_2_FIELD.equals(head.getFieldName())) {
            cell.setCellType(CellType.NUMERIC);
            cellStyle.setDataFormat(format.getFormat("#,##0"));
        } else if (COL_3_FIELD.equals(head.getFieldName())) {
            cell.setCellType(CellType.NUMERIC);
            cellStyle.setDataFormat(format.getFormat("0.00000000"));
        }
        cell.setCellStyle(cellStyle);
    }
}
