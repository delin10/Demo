package nil.ed.test.image;

import sun.font.FontDesignMetrics;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class BufferedImageDemo {

    public static void main(String...args) throws IOException {
        // 1. BufferImaged必须指定宽高，我们可以直接读取图片
        // 2. 通过BufferedImage对象获取Graphics2D对象
        // 3.
        BufferedImage image = ImageIO.read(Objects.requireNonNull(BufferedImageDemo.class.getResourceAsStream("/xx.png")));
        Graphics2D graphics = (Graphics2D) image.getGraphics();
        Map<TextAttribute,Object> attrs = new HashMap<>();
        attrs.put(TextAttribute.FAMILY, "Robot");
        attrs.put(TextAttribute.WEIGHT, 1.0f);
        attrs.put(TextAttribute.SIZE, 24);
        graphics.setFont(new Font(attrs));
        // 开启抗锯齿
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        graphics.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        JTextField field = new JTextField();
        field.setText("XXXX");
        field.paint(graphics);
        field.getUI().paint(graphics, field);
        graphics.setStroke(new BasicStroke(1.5f));
        int rectX = 10;
        int rectY = 10;
        int rectWidth = 200;
        int rectHeight = 200;
        graphics.drawRect(rectX, rectY, rectWidth, rectHeight);
        graphics.setStroke(new BasicStroke(1.0f));
        String text = "sdadadasda";
        int textWidth = FontDesignMetrics.getMetrics(graphics.getFont()).stringWidth(text);
        graphics.drawString(text, (rectX + rectWidth/2 - textWidth/2), rectY + rectHeight / 2);
        ImageIO.write(image, "png", new File("/Users/lidelin/delin/workspace/Demo/data/image/xx-output.png"));
    }

}
