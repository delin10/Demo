package nil.ed.test.stream;

import java.util.stream.IntStream;

/**
 * @author lidelin.
 */
public class StreamDemo {

    public static void main(String[] args) {
        IntStream.range(0, 1000).mapToObj(Integer::new).forEach(e ->{
            System.out.println(e);
        });
    }

}
