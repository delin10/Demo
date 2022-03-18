package nil.ed.test.algo.utils;

/**
 * @author lidelin.
 */
public class SampleResolver {

    public static String arrayResolver(String array) {
        return array.replaceAll("\\[", "{").replaceAll("]", "}");
    }

    public static void main(String[] args) {
        System.out.println(arrayResolver("[[1]]"));
    }

}
