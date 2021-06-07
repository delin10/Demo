package nil.ed.test.jna;

import com.sun.jna.Library;
import com.sun.jna.Native;

/**
 * @author lidelin.
 */
public class HelloWorld {

    static {
        System.load("/Users/admin/delin/private-workspace/Demo/jni-demo/src/main/cpp/Test2.so");
    }

    public interface CLibrary extends Library {
        CLibrary INSTANCE = (CLibrary)
                Native.load("/Users/admin/delin/private-workspace/Demo/jni-demo/src/main/cpp/Test2.so",
                        CLibrary.class);

        void test(String echo);
    }

    public static void main(String[] args) {
        CLibrary.INSTANCE.test("asdfgh");
    }
}
