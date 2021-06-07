package nil.ed.test.jni;

import java.time.LocalDateTime;

/**
 * @author lidelin.
 */
public class HelloWorld2 {

    {
        System.load("/Users/admin/delin/private-workspace/Demo/jni-demo/src/main/cpp/test.so");
    }

    public static native String hello(LocalDateTime localDateTime);

    public static void main(String[] args) {
        System.out.println(new HelloWorld2().hello(LocalDateTime.now()));
    }
}
