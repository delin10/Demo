package nil.ed.test.easyexcel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.context.AnalysisContext;
import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author niexiao
 * @since 2021/1/31 3:28 下午
 */
public class UploadExcelListener extends BaseExcelListener<GroupManualImportExcelTemplateDTO> {

    private static final Logger LOGGER = LoggerFactory.getLogger(UploadExcelListener.class);

    /**
     * 模版首部集合.
     */
    private static final Set<String> TEMPLATE_HEAD_SET;

    /**
     * 符合条件的记录
     */
    private Set<Long> existentSellerIdSet = Sets.newConcurrentHashSet();

    /**
     * 上传的总数量
     */
    private Integer totalCount = 0;

    static {
        TEMPLATE_HEAD_SET = Stream.of(GroupManualImportExcelTemplateDTO.class.getDeclaredFields())
                .map(f -> f.getAnnotation(ExcelProperty.class))
                .filter(Objects::nonNull)
                .filter(ep -> ep.value().length > 0)
                .map(ep -> ep.value()[0])
                .filter(StringUtils::isNotBlank)
                .collect(Collectors.toSet());

    }
    public UploadExcelListener() {

    }
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        // 保证上传文件与模版一致
        if (CollectionUtils.intersection(headMap.values(), TEMPLATE_HEAD_SET).size() != TEMPLATE_HEAD_SET.size()) {
            // 到外层的时候日志会被封装成ExcelAnalysisException异常
            throw new IllegalArgumentException("Please check your file template！");
        }
    }

    @Override
    public void invoke(GroupManualImportExcelTemplateDTO data, AnalysisContext analysisContext) {
        System.out.println(data);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
    }

    @Override
    public boolean hasNext(AnalysisContext context) {
        System.out.println("ss");
        return true;
    }



    public static void main(String[] args) throws FileNotFoundException {
        // /Users/lidelin10/Downloads/import.xlsx
        EasyExcel.read(new FileInputStream(new File("/Users/lidelin10/Downloads/import.xlsx")), GroupManualImportExcelTemplateDTO.class, new UploadExcelListener())
                .sheet().doRead();
        EasyExcel.read(new FileInputStream(new File("/Users/lidelin10/lidelin/data/excel/test2.xls")), GroupManualImportExcelTemplateDTO.class, new UploadExcelListener())
                .sheet().doRead();
    }
}
