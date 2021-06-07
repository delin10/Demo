package nil.ed.test.jni;

import java.time.LocalDateTime;

/**
 * @author lidelin.
 */
public class HelloWorld {

    {
        System.load("/Users/admin/delin/private-workspace/Demo/jni-demo/src/main/cpp/test.so");
    }

    public native String hello(LocalDateTime localDateTime);

    public static void main(String[] args) {
        System.out.println(new HelloWorld().hello(LocalDateTime.now()));
    }
}
