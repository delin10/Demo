package nil.ed.test.easyexcel;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.converters.date.DateStringConverter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author lidelin.
 */
public class LocalDateTimeConverter implements Converter<LocalDateTime> {

    private static final DateStringConverter DATE_STRING_CONVERTER = new DateStringConverter();

    @Override
    public Class<LocalDateTime> supportJavaTypeKey() {
        return LocalDateTime.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public LocalDateTime convertToJavaData(CellData cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        Date date = DATE_STRING_CONVERTER.convertToJavaData(cellData, contentProperty, globalConfiguration);
        if (date != null) {
            return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        }
        return null;
    }

    @Override
    @SuppressWarnings("rawtypes")
    public CellData convertToExcelData(LocalDateTime value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        String text = "";
        if (value != null) {
            if (contentProperty == null || contentProperty.getDateTimeFormatProperty() == null) {
                text = value.toString();
            } else {
                text = DateTimeFormatter.ofPattern(contentProperty.getDateTimeFormatProperty().getFormat()).format(value);
            }
        }
        return new CellData(text);
    }
}
