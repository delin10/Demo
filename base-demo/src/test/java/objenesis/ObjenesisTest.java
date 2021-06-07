package objenesis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.objenesis.Objenesis;
import org.springframework.objenesis.ObjenesisStd;
import org.springframework.objenesis.instantiator.ObjectInstantiator;
import sun.misc.Unsafe;

import java.util.Arrays;

/**
 * @author lidelin.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(fullyQualifiedNames = "sun.misc.Unsafe")
public class ObjenesisTest {

    @Test
    public void test() {
        Unsafe unsafe = Mockito.spy(Unsafe.class);
        Mockito.when(Unsafe.getUnsafe()).thenReturn(unsafe);
        Objenesis objenesis = new ObjenesisStd();
        // 不会调用初始化域

        Mockito.doAnswer(inv -> {
            String name = inv.getArgument(0);
            System.out.println(name);
            return null;
        }).when(unsafe).defineClass(Mockito.anyString(),
                Mockito.any(), Mockito.any(),
                Mockito.any(), Mockito.any(),
                Mockito.any());
        ObjectInstantiator<DemoClass> instantiator = objenesis.getInstantiatorOf(DemoClass.class);
        System.out.println(instantiator.newInstance().getClass());
        System.out.println(new DemoClass());
        System.out.println(Arrays.toString(DemoClass.class.getDeclaredConstructors()));
    }

    public static class DemoClass {
        {
            System.out.println("ok");
        }

        public DemoClass() {
            System.out.println("sdfga21");
        }
        public DemoClass(String arg) {
            System.out.println(arg);
        }
    }

    public class CustomClassLoader extends ClassLoader {

    }
}
