package nil.ed.test.json.array;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author lidelin10
 * @date 2022/3/24 下午2:44
 */
public class ArrayErrorDemo {

    public static void main(String[] args) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = "{}";
        String json1 = "[{}]";
//        System.out.println(Arrays.toString(objectMapper.readValue(json, int[].class)));
        System.out.println(objectMapper.readValue(json1, ArrayErrorDemo.class));
    }

}
