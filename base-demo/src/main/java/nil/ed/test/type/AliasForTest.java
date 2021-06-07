package nil.ed.test.type;

import nil.ed.test.springboot.bean.conditional.BootApp;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.annotation.AliasFor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.MergedAnnotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author lidelin.
 */
@SpringBootApplication
@AliasForTest.TestAliasForA
@AliasForTest.TestAliasForB(b = "success")
@AliasForTest.TestAliasFor(value = "12345",alias = "12345", a = "xxx")
public class AliasForTest {

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    @Documented
    public  @interface TestAliasFor {
        @AliasFor("alias")
        String value() default "NULL";
        @AliasFor("value")
        String alias() default "NULL";

        @AliasFor("b")
        String a() default "NULL";
        @AliasFor("a")
        String b() default "NULL";
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    @Documented
    @TestAliasForB
    public  @interface TestAliasForA {
        @AliasFor(value = "b", annotation = TestAliasForB.class)
        String a() default "NULL";
//        @AliasFor(value = "b", annotation = TestAliasForB.class)
//        String b() default "NULL";
//        @AliasFor(value = "b", annotation = TestAliasForB.class)
//        String c() default "NULL";
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    @Documented
    @TestAliasForA
    public  @interface TestAliasForB {
        @AliasFor(value = "a", annotation = TestAliasForA.class)
        String b() default "NULL";
    }

    public static void main(String[] args) {
        SpringApplication.run(BootApp.class);
        TestAliasFor testAliasFor = AnnotationUtils.findAnnotation(AliasForTest.class, TestAliasFor.class);
        System.out.println(testAliasFor.alias());
        System.out.println(testAliasFor.value());
        System.out.println(testAliasFor.a());
        System.out.println(testAliasFor.b());
        TestAliasFor aliasFor1 = AliasForTest.class.getAnnotation(TestAliasFor.class);
        System.out.println(aliasFor1.value());
        System.out.println(aliasFor1.alias());
        System.out.println(aliasFor1.a());
        System.out.println(aliasFor1.b());
        TestAliasForA testAliasForA = AnnotationUtils.findAnnotation(AliasForTest.class, TestAliasForA.class);
        TestAliasForB testAliasForB = AnnotationUtils.findAnnotation(AliasForTest.class, TestAliasForB.class);
        System.out.println(testAliasForA.a());
//        System.out.println(testAliasForA.b());
//        System.out.println(testAliasForA.c());
        System.out.println(testAliasForB.b());
        MergedAnnotations mergedAnnotations = MergedAnnotations.from(AliasForTest.class);
        System.out.println(mergedAnnotations.get(TestAliasForA.class));
        System.out.println(mergedAnnotations.get(TestAliasForB.class));
    }

}
