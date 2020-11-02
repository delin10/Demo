package nil.ed.test.springboot.bean.conditional;

/**
 * @author delin10
 * @since 2020/7/7
 **/
public class TestClass {

    public String name;

    public TestClass(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return TestClass.class.getCanonicalName() + "[" + name + "]";
    }
}
