package nil.ed.test.enum_demo;

import lombok.Getter;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Arrays;

/**
 * @author lidelin.
 */
public class EnumTest {

    /**
     * 枚举管理前缀.
     * @author lidelin.
     */
    @Getter
    public enum Prefix {
        PREFIX0("[PREFIX0]")
        ;
        private final String prefix;

        Prefix(String prefix) {
            this.prefix = prefix;
        }
    }

    /**
     * 枚举实例1.
     * @author lidelin.
     */
    @Getter
    public enum EnumDemo {
        TEST0(1, "1"),
        TEST1(2, "2")
        ;

        private static final int PREFIX_CODE = 100;
        private static final String PREFIX_MESSAGE = "[MSG]: ";

        private int code;

        private String message;

        EnumDemo(int code, String message) {
            this.code = getPrefixCode() + code;
            this.message = getPrefixMessage() + message;
        }

        public static int getPrefixCode() {
            return PREFIX_CODE;
        }

        public static String getPrefixMessage() {
            return PREFIX_MESSAGE;
        }
    }

    public EnumTest(String a, String b) {
        int x = 0;
        System.out.println(x);
    }

    /**
     * 枚举实例2.
     * @author lidelin.
     */
    @Getter
    public enum EnumDemo2 {
        TEST0(1, "1"),
        TEST1(2, "2")
        ;

        private static final int PREFIX_CODE = 100;
        public static final String PREFIX_MESSAGE = Prefix.PREFIX0.getPrefix();

        private final int code;

        private final String message;

        EnumDemo2(int code, String message) {
            this.code = getPrefixCode() + code;
            this.message = getPrefixMessage() + message;
        }

        public String getPrefixMessage() {
            return PREFIX_MESSAGE;
        }

        public static int getPrefixCode() {
            return PREFIX_CODE;
        }
    }

    public static void main(String[] args) throws IOException {
        System.out.println(EnumDemo.TEST0.getMessage());//  ==> [MSG]: 1
        System.out.println(EnumDemo2.TEST0.getMessage());// ==> null1
        System.out.println(Arrays.toString(EnumDemo2.class.getDeclaredConstructors()[0].getParameters()));
        MethodVisitor visitor = new MethodVisitor(Opcodes.ASM4) {
            @Override
            public void visitLocalVariable(String name, String desc, String signature, Label start, Label end, int index) {
                System.out.printf("%10s%10s%10s%10s%10s%10s%10s%n",
                        name, desc, signature.toString(), start.toString(), end,toString(), index);
            }
        };
        ObjectOutputStream stream = new ObjectOutputStream(System.out);
        stream.writeObject(EnumDemo2.TEST0);
        System.out.println(TestAnonymous.TEST0.getClass());
        System.out.println(TestAnonymous.TEST0.getDeclaringClass());
        ClassReader reader = new ClassReader(ClassLoader.getSystemResourceAsStream("nil/ed/test/enum_demo/EnumTest$EnumDemo2.class"));
        ClassVisitor classVisitor = new ClassVisitor(Opcodes.ASM4) {
            @Override
            public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
                return visitor;
            }
        };
        reader.accept(classVisitor, ClassReader.SKIP_CODE);
    }

    public enum TestAnonymous {
        TEST0{}, TEST1{};
    }

}
