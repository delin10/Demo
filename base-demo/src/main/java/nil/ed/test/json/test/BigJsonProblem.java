package nil.ed.test.json.test;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author lidelin.
 */
public class BigJsonProblem {

    @Data
    @Accessors(chain = true)
    public static class TestObject {

        private Integer value1;
        private Integer value2;
        private Integer value3;
        private Integer value4;
        private Integer value5;
        private Integer value6;
        private Integer value7;
        private Integer value8;
        private Integer value9;
        private Integer value10;
        private Integer value11;
        private Integer value12;

    }

    public static void main(String[] args) throws InterruptedException {
//        Thread.sleep(30000L);
        long count = 5_000_000;
        List<TestObject> linkedList = new LinkedList<>();
        List<TestObject> linkedList2 = new LinkedList<>();
        List<TestObject> linkedList3 = new LinkedList<>();

        for (int i = 0; i < count; ++i) {
            TestObject to = new TestObject()
                    .setValue1(i)
                    .setValue2(i)
                    .setValue3(i)
                    .setValue4(i)
                    .setValue5(i)
                    .setValue6(i)
                    .setValue7(i)
                    .setValue8(i)
                    .setValue9(i)
                    .setValue10(i)
                    .setValue11(i)
                    .setValue12(i);
            linkedList.add(to);
            linkedList2.add(to);
            linkedList3.add(to);
        }
        Map<String, Object> put = new HashMap<>();
        put.put("list1", linkedList);
        put.put("list2", linkedList2);
        put.put("list3", linkedList3);
        String ret = JSON.toJSONString(linkedList);
        System.out.println(ret.length());
    }

}
