package nil.ed.test.easyexcel;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.HeadFontStyle;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.alibaba.excel.write.handler.AbstractSheetWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

/**
 * @author lidelin.
 */
public class LatervalDemo {

    /**
     * @author lidelin.
     */
    @Data
    @Accessors(chain = true)
    @ColumnWidth(value = 50)
    @HeadRowHeight(value = 15)
    @HeadFontStyle(fontHeightInPoints = 10)
    public static class DemoModel {
        @ExcelProperty(converter = DemoConverter.class)
        private Map<String, String> map;
    }

    public static void main(String[] args) {
        DemoModel model = new DemoModel();
        Map<String, String> map = new HashMap<>(4);
        map.put("k1", "v1");
        map.put("k2", "v1");
        map.put("k3", "v1");
        model.map = map;
        List<String> addonHead = Arrays.asList("k1", "k2", "k3");
        try (OutputStream outputStream = Files.newOutputStream(Paths.get("/Users/lidelin10/lidelin/data/excel/test2.xls"), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
            EasyExcel.write(outputStream)
                    .head(DemoModel.class)
                    .sheet("sheet-1")
                    .registerWriteHandler(new AddOnHeadSheetWriteHandler(addonHead, "map"))
                    .doWrite(Collections.singletonList(model));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static class AddOnHeadSheetWriteHandler extends AbstractSheetWriteHandler {

        /**
         * 添加的head列表.
         */
        private List<String> addOnHead = Collections.emptyList();

        /**
         * 需要打平的field.
         */
        private final String field;

        public AddOnHeadSheetWriteHandler(List<String> addOnHead, String field) {
            this.addOnHead = addOnHead;
            this.field = field;
        }

        @Override
        public void beforeSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {
            Map<Integer, Head> headMap = writeSheetHolder.getExcelWriteHeadProperty().getHeadMap();
            Map<Integer, ExcelContentProperty> propertyMap = writeSheetHolder.getExcelWriteHeadProperty().getContentPropertyMap();
            Map.Entry<Integer, Head> hitEntry = null;
            for (Map.Entry<Integer, Head> entry : headMap.entrySet()) {
                if (entry.getValue().getFieldName().equals(field)) {
                    hitEntry = entry;
                    break;
                }
            }
            if (hitEntry == null) {
                return;
            }
            headMap.remove(hitEntry.getKey());
            ExcelContentProperty property = propertyMap.remove(hitEntry.getKey());
            Head hitHead = hitEntry.getValue();
            for (String newHead : addOnHead) {
                Head head = new Head(headMap.size(), hitHead.getFieldName(), Collections.singletonList(newHead), hitHead.getForceIndex(), hitHead.getForceName());
                headMap.put(head.getColumnIndex(), head);
                ExcelContentProperty prop = new ExcelContentProperty();
                BeanUtils.copyProperties(property, prop);
                prop.setHead(head);
                propertyMap.put(head.getColumnIndex(), prop);
            }
        }
    }

    public static class DemoConverter implements Converter<Map<String, String>> {

        @Override
        public Class supportJavaTypeKey() {
            throw new UnsupportedOperationException();
        }

        @Override
        public CellDataTypeEnum supportExcelTypeKey() {
            return CellDataTypeEnum.STRING;
        }

        @Override
        public Map<String, String> convertToJavaData(CellData cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
            throw new UnsupportedOperationException();
        }

        @Override
        public CellData convertToExcelData(Map<String, String> value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
            /*
            从map中取值.
             */
            CellData cellData = new CellData(CellDataTypeEnum.STRING);
            // cellData.setData(value.get(contentProperty.getHead().getHeadNameList().get(0)));
            cellData.setStringValue(value.get(contentProperty.getHead().getHeadNameList().get(0)));
            return cellData;
        }
    }

}
