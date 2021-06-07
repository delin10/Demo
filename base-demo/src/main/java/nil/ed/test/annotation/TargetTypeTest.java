package nil.ed.test.annotation;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author lidelin.
 */
public class TargetTypeTest {

    @Target({})
    @interface TestAnno {

    }

    @Retention(RetentionPolicy.RUNTIME)
    @interface Composite {
        TestAnno[] value();
    }

    @Composite(@TestAnno)
    static class TestClass {

    }

    /**
     * wse;
     * @author lidelin.
     */
    @Documented
    @Target(ElementType.TYPE_USE)
    @Retention(RetentionPolicy.RUNTIME)
    @interface TypeUse {
    }

    @TypeUse
    static class TypeUserTestClass {
        private Integer a;
        private String b;
        @TypeUse
        private TypeUserTestClass test;

        @TypeUse
        public Object returnTest(@TypeUse Integer a) {
            @TypeUse
            Integer b = 1;
            return null;
        }
    }

    static class TypeUserTestClass2 {
        private TypeUserTestClass test;
    }

    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    @interface TestField {
    }

    static class TestFieldClass {
        @TestField
        private static Integer a;
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @interface TestMethod {
    }

    static class TestMethodClass {

        public TestMethodClass(Integer b) {
        }
    }

    @Target(ElementType.PARAMETER)
    @Retention(RetentionPolicy.RUNTIME)
    @interface TestParam {
    }

    static class TestParamClass {
        public TestParamClass(Integer b) {
        }
    }

    @Target(ElementType.LOCAL_VARIABLE)
    @Retention(RetentionPolicy.RUNTIME)
    @interface TestLocalVar {
    }

    static class TestLocalVarClass {

        private Integer f;

        public TestLocalVarClass(Integer b) throws IOException {
            @TestLocalVar
            Integer a = 0;

            try (@TestLocalVar InputStream in = Files.newInputStream(Paths.get(""))){
            }
            for (@TestLocalVar int i = 0; i < 10; ++i ){

            }
        }
    }

    @Target(ElementType.PACKAGE)
    @Retention(RetentionPolicy.RUNTIME)
    @interface TestPkg {
    }




    public static void main(String[] args) {
        Composite composite = TestClass.class.getAnnotation(Composite.class);
        assert composite != null;
        assert composite.value() != null;

        Field[] fs = TypeUserTestClass.class.getDeclaredFields();
        assert TypeUserTestClass.class.getAnnotation(TypeUse.class) != null;
        for (Field f : fs) {
            System.out.println(f.getAnnotation(TypeUse.class));
        }
    }

}
