package nil.ed.test.dynamicimage;

import java.awt.*;
import java.awt.font.TextAttribute;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import com.freewayso.image.combiner.ImageCombiner;
import com.freewayso.image.combiner.element.TextElement;
import com.freewayso.image.combiner.enums.OutputFormat;

/**
 * @author lidelin10
 * @date 2022/8/23 上午7:33
 */
public class JCombiner {

        public static void main(String[] args) throws Exception {

            GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
            String[] fontName = environment.getAvailableFontFamilyNames();
            for (int i = 0; i < fontName.length; i++) {
                System.out.println(fontName[i]);
            }
            String workdir = "a-tmp/imagecombiner";
            Path fullWorkdir = Paths.get(new File("").getAbsolutePath(), workdir);
            dynamicWidthDemoTest(fullWorkdir.toUri().toURL().getFile());
        }


        public static void dynamicWidthDemoTest(String savePath) throws Exception {
            ImageCombiner combiner = new ImageCombiner(1336, 864, OutputFormat.PNG);

            String str1 = "您出征";
            String str2 = "某城市";     //外部传参，内容不定，宽度也不定
            String str3 = "，共在前线战斗了";
            String str4 = "365";       //外部传参，内容不定，宽度也不定
            String str5 = "天！";
            String str6 = "หากดำเนินการต่อ แสดงว่าคุณยอมรับข้อกำหนดและเงื่อนไขของเครื่องมือโปรโมทสินค้าในลาซาด้า";
            int fontSize = 20;
            int xxxFontSize = 40;

            int offsetX = 20;   //通过计算前一个元素的实际宽度，并累加这个偏移量，得到后一个元素正确的x坐标值
            int y = 20;

            //第一段
            Map<TextAttribute, Object> attrs = new HashMap<>();
            attrs.put(TextAttribute.WEIGHT, 1);
            attrs.put(TextAttribute.FAMILY, "Robot");
            attrs.put(TextAttribute.SIZE, 20);
            Font font = Font.getFont(attrs);

            Map<TextAttribute, Object> attrs40 = new HashMap<>();
            attrs40.put(TextAttribute.WEIGHT, 2);
            attrs40.put(TextAttribute.FAMILY, "Robot");
            attrs40.put(TextAttribute.SIZE, 40);
            Font font40 = Font.getFont(attrs40);
            TextElement element1 = combiner.addTextElement(str1, fontSize, offsetX, y).setFont(font);
            offsetX += element1.getWidth();     //计算宽度，并累加偏移量

            //第二段（内容不定，宽度也不定）
            TextElement element2 = combiner.addTextElement(str2, xxxFontSize, offsetX, y - 20)
                    .setColor(Color.red).setFont(font40);
            offsetX += element2.getWidth();

            //第三段
            TextElement element3 = combiner.addTextElement(str3, fontSize, offsetX, y).setFont(font);
            offsetX += element3.getWidth();

            //第四段（内容不定，宽度也不定）

            // 以左上角为定位基点
            TextElement element4 = combiner.addTextElement(str4, xxxFontSize, offsetX, y - 20)
                    .setColor(Color.red).setFont(font40);
            offsetX += element4.getWidth();

            //第五段
            TextElement element5 = combiner.addTextElement(str5, fontSize, offsetX, y).setFont(font);

            offsetX += element5.getWidth();

            Map<TextAttribute, Object> attrsTH = new HashMap<>();
            attrsTH.put(TextAttribute.WEIGHT, 1);
            attrsTH.put(TextAttribute.FAMILY, "DB Helvethaica X");
            attrsTH.put(TextAttribute.SIZE, 20);
            Font fontTH = Font.getFont(attrsTH);
            combiner.addTextElement(str6, 20, offsetX, y)
                    .setColor(Color.BLACK).setFont(fontTH);

            combiner.combine();
            combiner.save(savePath + "/demo.jpg");
        }
}
