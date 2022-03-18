package nil.ed.test.easyexcel;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import com.alibaba.excel.EasyExcel;

/**
 * @author lidelin.
 */
public class ExcelWriterDemo {

    public static void main(String[] args) throws IOException {
        List<DemoModel> modelList = new LinkedList<>();
        DemoModel model = new DemoModel();
        model.setCol1("col1").setCol2(2000000L).setCol3(3.2345678D).setCol4(LocalDateTime.now());
        modelList.add(model);
        try (OutputStream outputStream = Files.newOutputStream(Paths.get("/Users/lidelin10/lidelin/data/excel/test.xls"), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
            EasyExcel.write(outputStream)
                    .head(DemoModel.class)
                    .sheet("sheet-1")
                    .registerConverter(new LocalDateTimeConverter())
                    .registerWriteHandler(new DemoModelCellWriteHandler())
                    .registerWriteHandler(new CustomSheetWriteHandler())
                    .doWrite(modelList);
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream(300000512);
        EasyExcel.write(out)
                .head(DemoModel.class)
                .sheet("sheet-1")
                .registerConverter(new LocalDateTimeConverter())
                .registerWriteHandler(new DemoModelCellWriteHandler())
                .registerWriteHandler(new CustomSheetWriteHandler())
                .doWrite(modelList);
        System.out.println(out.size());
    }

}
