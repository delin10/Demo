package nil.ed.test.java;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;

/**
 * @author lidelin.
 */
public class TryWithResourceDemo {

    public static void main(String[] args) throws IOException {
        testDemo();
    }

    public static void testDemo() throws IOException {
        try (InputStream in = TryWithResourceDemo.class.getResourceAsStream("TryWithResourceDemo.class");
             InputStream in2 = TryWithResourceDemo.class.getResourceAsStream("TryWithResourceDemo.class");
             InputStream in3 = TryWithResourceDemo.class.getResourceAsStream("TryWithResourceDemo.class")){
            System.out.println(IOUtils.readLines(in, StandardCharsets.UTF_8));
        }
    }

}
