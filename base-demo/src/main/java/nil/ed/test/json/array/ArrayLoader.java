package nil.ed.test.json.array;

import java.io.IOException;
import java.util.List;

import com.alibaba.fastjson.JSON;
import nil.ed.test.io.IoUtilsX;

/**
 * @author lidelin.
 */
public class ArrayLoader {

    public static <T> List<T> load(String file, Class<T> clazz) throws IOException {
        String json = IoUtilsX.readFullText(file);
        return JSON.parseArray(json, clazz);
    }

}
