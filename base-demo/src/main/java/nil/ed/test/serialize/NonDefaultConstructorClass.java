package nil.ed.test.serialize;

import lombok.Data;

/**
 * @author lidelin.
 */
@Data
public class NonDefaultConstructorClass {

    public String a;
    private String b;
    protected String c;
    String d;

    public NonDefaultConstructorClass(String a) {
        this.a = a;
    }
}
