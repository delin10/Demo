package nil.ed.test.serialize;

import lombok.Data;

/**
 * @author lidelin.
 */
@Data
public class NonSerializableSuperClass {

    public static String aStatic;
    private static String bStatic;
    protected static String cStatic;
    static String dStatic;

    public String a;
    private String b;
    protected String c;
    String d;

    public static String getaStatic() {
        return aStatic;
    }

    public static void setaStatic(String aStatic) {
        NonSerializableSuperClass.aStatic = aStatic;
    }

    public static String getbStatic() {
        return bStatic;
    }

    public static void setbStatic(String bStatic) {
        NonSerializableSuperClass.bStatic = bStatic;
    }

    public static String getcStatic() {
        return cStatic;
    }

    public static void setcStatic(String cStatic) {
        NonSerializableSuperClass.cStatic = cStatic;
    }

    public static String getdStatic() {
        return dStatic;
    }

    public static void setdStatic(String dStatic) {
        NonSerializableSuperClass.dStatic = dStatic;
    }
}
