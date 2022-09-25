package nil.ed.test.easyexcel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.metadata.data.CellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.handler.RowWriteHandler;
import com.alibaba.excel.write.handler.SheetWriteHandler;
import com.alibaba.excel.write.handler.WorkbookWriteHandler;
import com.alibaba.excel.write.handler.context.CellWriteHandlerContext;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author lidelin.
 */
public class ExcelWriterLifeCycleDemo {

    public static void main(String[] args) throws IOException {
        List<DemoModel> modelList = new LinkedList<>();
        DemoModel model = new DemoModel();
        model.setCol1("col1").setCol2(2000000L).setCol3(3.2345678D).setCol4(LocalDateTime.now());
        modelList.add(model);
        try (OutputStream outputStream = Files.newOutputStream(Paths.get("/Users/lidelin/delin/data/excel/nil.ed.test.xls"), StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE)) {
            EasyExcel.write(outputStream, DemoModel.class)
                    .sheet("sheet-1")
                    .registerConverter(new LocalDateTimeConverter())
                    .registerWriteHandler(new DemoWorkBookWriteHandler())
                    .registerWriteHandler(new DemoSheetWriteHandler())
                    .registerWriteHandler(new DemoRowWriteHandler())
                    .registerWriteHandler(new DemoCellWriteHandler())
                    .doWrite(modelList);
        }
    }

    public static String tab(int num) {
        return IntStream.range(0, num * 4).mapToObj(i -> " ").collect(Collectors.joining());
    }

    public static class DemoWorkBookWriteHandler implements WorkbookWriteHandler {

        @Override
        public void beforeWorkbookCreate() {
            System.out.println("【Workbook】beforeWorkbookCreate...");
        }

        @Override
        public void afterWorkbookCreate(WriteWorkbookHolder writeWorkbookHolder) {
            System.out.println("【Workbook】afterWorkbookCreate...");
        }

        @Override
        public void afterWorkbookDispose(WriteWorkbookHolder writeWorkbookHolder) {
            System.out.println("【Workbook】afterWorkbookDispose...");
        }
    }

    public static class DemoSheetWriteHandler implements SheetWriteHandler {
        @Override
        public void beforeSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {
            System.out.println(tab(1) + "【Sheet】beforeSheetCreate");
        }

        @Override
        public void afterSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {
            System.out.println(tab(1) + "【Sheet】beforeSheetCreate");
        }
    }

    public static class DemoRowWriteHandler implements RowWriteHandler {
        @Override
        public void beforeRowCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Integer rowIndex, Integer relativeRowIndex, Boolean isHead) {
            System.out.println(tab(2) + "【ROW】beforeRowCreate");
        }

        @Override
        public void afterRowCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row, Integer relativeRowIndex, Boolean isHead) {
            System.out.println(tab(2) + "【Row】afterRowCreate");
        }

        @Override
        public void afterRowDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row, Integer relativeRowIndex, Boolean isHead) {
            System.out.println(tab(2) + "【Row】afterRowDispose");
        }
    }

    public static class DemoCellWriteHandler implements CellWriteHandler {
        @Override
        public void beforeCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row, Head head, Integer columnIndex, Integer relativeRowIndex, Boolean isHead) {
            System.out.println(tab(3) + "【Cell】beforeCellCreate");
        }

        @Override
        public void afterCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {
            System.out.println(tab(3) + "【Cell】afterCellCreate");
        }

        @Override
        public void afterCellDataConverted(CellWriteHandlerContext context) {
            System.out.println(tab(3) + "【Cell】afterCellDataConverted");
        }

        @Override
        public void afterCellDataConverted(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, WriteCellData<?> cellData, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {
            System.out.println(tab(3) + "【Cell】afterCellDataConverted");        }

        @Override
        public void afterCellDispose(CellWriteHandlerContext context) {
            System.out.println(tab(3) + "【Cell】afterCellDispose");
        }

        @Override
        public void afterCellDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, List<WriteCellData<?>> cellDataList, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {
            System.out.println(tab(3) + "【Cell】afterCellDispose");
        }

    }

}
