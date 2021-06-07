package nil.ed.test.springboot.scope;

/**
 * @author lidelin.
 */
public class TestClassDependSingle {

    private TestClassSingle single;

    public TestClassDependSingle(TestClassSingle single) {
        this.single = single;
    }
}
