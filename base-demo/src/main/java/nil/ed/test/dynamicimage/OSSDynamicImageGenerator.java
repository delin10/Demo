package nil.ed.test.dynamicimage;

import java.util.Base64;

/**
 * @author lidelin10
 * @date 2022/4/11 下午3:50
 */
public class OSSDynamicImageGenerator {

    public static void main(String[] args) {
        int x = 20;
        int y = 30;
        StringBuilder builder = new StringBuilder("image");
        generate(builder, x, y, 25, "FFA500", "Increase Budget to High ROI Campaign");
        generate(builder, x, y += 30, 15, "FFA500", "Previously ROI {Algo} ; Budget {XX}, Estimated Click {XX}");
        System.out.println(builder);
    }

    static final String TMP = "watermark,text_%s,size_%s,color_%s,type_ZmFuZ3poZW5naGVpdGk,g_nw,x_%s,y_%s";
    public static void generate(StringBuilder builder, int x, int y, int fontSize, String color, String text) {
        builder.append("/");
        String baseText = Base64.getEncoder().encodeToString(text.getBytes());
        builder.append(String.format(TMP, baseText, fontSize, color, x, y));
    }

}
