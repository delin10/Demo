package powermock;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.MockGateway;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import sun.reflect.ReflectionFactory;

import java.lang.reflect.Field;

@RunWith(PowerMockRunner.class)
@PrepareForTest(fullyQualifiedNames = "powermock.StaticMethodProvider")
public class PowerMockStaticMethodTest {

    static {
        System.out.println("load");
    }

    @Before
    public void init() {
        System.out.println(this.getClass().getClassLoader().getClass().getCanonicalName());
        PowerMockito.mockStatic(StaticMethodProvider.class);
        StaticMethodProvider.provide();
    }

    @Test
    public void test() {
        System.out.println("test");
    }
}
