package nil.ed.test.objenesis;

import org.springframework.objenesis.Objenesis;
import org.springframework.objenesis.ObjenesisStd;
import org.springframework.objenesis.instantiator.ObjectInstantiator;

import java.util.Arrays;

/**
 * @author lidelin.
 */
public class ObjenesisDemo {


    public static void main(String[] args) {
        Objenesis objenesis = new ObjenesisStd();
        // 不会调用初始化域
        ObjectInstantiator<DemoClass> instantiator = objenesis.getInstantiatorOf(DemoClass.class);
        System.out.println(instantiator.newInstance().getClass());
        System.out.println(Arrays.toString(DemoClass.class.getDeclaredConstructors()));
    }

    public static class DemoClass {
        public DemoClass(String arg) {
            System.out.println(new DemoClass(""));
            System.out.println(arg);
        }
    }

    public class CustomClassLoader extends ClassLoader {

    }
}
