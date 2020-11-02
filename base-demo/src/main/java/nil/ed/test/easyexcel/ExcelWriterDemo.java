package nil.ed.test.easyexcel;

import com.alibaba.excel.EasyExcel;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

/**
 * @author lidelin.
 */
public class ExcelWriterDemo {

    public static void main(String[] args) throws IOException {
        List<DemoModel> modelList = new LinkedList<>();
        DemoModel model = new DemoModel();
        model.setCol1("col1").setCol2(2000000L).setCol3(3.2345678D).setCol4(LocalDateTime.now());
        modelList.add(model);
        try (OutputStream outputStream = Files.newOutputStream(Paths.get("/Users/admin/delin/nil.ed.test/excel/nil.ed.test.xls"))) {
            EasyExcel.write(outputStream, DemoModel.class)
                    .sheet("sheet-1")
                    .registerConverter(new LocalDateTimeConverter())
                    .registerWriteHandler(new DemoModelCellWriteHandler())
                    .doWrite(modelList);
        }
    }

}
