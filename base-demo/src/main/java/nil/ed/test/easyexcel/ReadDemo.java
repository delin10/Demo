package nil.ed.test.easyexcel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.alibaba.excel.EasyExcel;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

/**
 * @author lidelin10
 * @date 2022/5/7 下午6:58
 */
public class ReadDemo {

    public static void main(String[] args) throws IOException {
        String header = "live_date_日,seller_id,seller_name,short_code,fst_order_date,venture,seller_main_category1,seller_main_cluster,global_seller_tag,company_name,seller_city,main_sc_location_code,visible_item_求和,content_score_求和,impression_item_l30d_求和,visit_item_l30d_求和,selling_item_l30d_求和,gmv_l1d_求和,gmv_l7d_求和,gmv_l30d_求和,orders_l1d_求和,orders_l7d_求和,orders_l30d_求和,ipv_l7d_求和,ipv_l1d_求和,ipv_l30d_求和,ipv,cr_l7d,ipv,cr_l30d,is_join_fs_max_求和,member_id";
        String from = "/Users/lidelin10/Downloads/未命名报表_新商家数据月度_20220507_D2021101100161301000006834392.xlsx";
        List<LinkedHashMap<Integer, Object>> all = EasyExcel.read(new File(from)).doReadAllSync();
        System.out.println(all.get(0));
        System.out.println(all.get(1));
        String to = "/Users/lidelin10/Downloads/test/export (1).csv";
        CSVParser parser = new CSVParser(new FileReader(new File(to)), CSVFormat.EXCEL);
        List<CSVRecord> csvRecords = parser.getRecords();
        Map<String, String> xx = csvRecords.stream().map(CSVRecord::toList).skip(1).collect(Collectors.toMap(ls -> ls.get(1).trim(), ls -> ls.get(3), (a, b) -> a));
        List<String> lines = all.stream()
                .map(a -> a.values().stream()
                        .map(String::valueOf)
                        .collect(Collectors.joining(","))
                        + ","
                        + xx.getOrDefault(String.valueOf(a.get(3)).trim(), "0"))
                .peek(s -> {
                    System.out.println("ok");
                    System.out.println(s);
                })
                .collect(Collectors.toList());

        try (OutputStream outputStream = Files.newOutputStream(Paths.get("/Users/lidelin10/Downloads/delin_out.csv"), StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE)) {
            outputStream.write(header.getBytes("GBK"));
            outputStream.write('\n');
            for (String line : lines) {
                outputStream.write(line.getBytes("GBK"));
                outputStream.write('\n');
            }
        }
    }

}
