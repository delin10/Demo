package nil.ed.test.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author lidelin.
 */
public class InheritedTest {

    @Inherited
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    public @interface Anno {
    }

    @Anno
    public static class TestClass {

    }

    @Anno
    public interface TestInterface {

    }

    public static class SubClass extends TestClass {

    }

    public static class SubClass2 implements TestInterface {

    }

    public static void main(String[] args) {
        Anno anno = SubClass.class.getAnnotation(Anno.class);
        if (anno == null) {
            throw new IllegalStateException("Expect NOT NULL.");
        }

        Anno anno2 = SubClass2.class.getAnnotation(Anno.class);
        if (anno2 != null) {
            throw new IllegalStateException("Expect NULL.");
        }
    }
}
