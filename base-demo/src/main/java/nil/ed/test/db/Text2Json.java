package nil.ed.test.db;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

/**
 * @author lidelin.
 */
public class Text2Json {

    public static void main(String[] args) throws IOException {
        CSVParser parser = new CSVParser(new BufferedReader(new InputStreamReader(new FileInputStream(new File("/Users/lidelin10/lidelin/data/43.txt")))), CSVFormat.DEFAULT);
        Iterator<CSVRecord> recordIterator = parser.getRecords().iterator();
        CSVRecord head = recordIterator.next();
        List<String> headNames = head.toList();
        List<Map<String, String>> result = new LinkedList<>();
        while(recordIterator.hasNext()) {
            CSVRecord re = recordIterator.next();
            Map<String, String> lineContainer = new HashMap<>();
            List<String> lineData = re.toList();
            for (int i = 0; i < lineData.size(); ++i) {
                lineContainer.put(headNames.get(i), lineData.get(i));
            }
            result.add(lineContainer);
        }
        System.out.println(JSON.toJSONString(result));
    }

}
