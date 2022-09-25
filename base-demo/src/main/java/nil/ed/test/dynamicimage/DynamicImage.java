package nil.ed.test.dynamicimage;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.Date;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * @author lidelin10
 * @date 2022/4/7 上午9:35
 */
public class DynamicImage {

    public static void main(String[] args) throws IOException, MessagingException {
        ImageInputStream imageInputStream = ImageIO.createImageInputStream(new File(DynamicImage.class.getResource("/DualQueue-6.png").getFile()));
        System.out.println(imageInputStream);
        ImageReader reader = ImageIO.getImageReaders(imageInputStream).next();
        reader.setInput(imageInputStream);
        BufferedImage image = reader.read(0);
        Graphics graphics = image.getGraphics();
        int fontSize = 100;
        Font font = new Font("楷体", Font.PLAIN, fontSize);
        graphics.setFont(font);
        graphics.setColor(Color.BLACK);
        int strWidth = graphics.getFontMetrics().stringWidth("好");
        graphics.drawString("好", fontSize - (strWidth / 2), fontSize + 30);

        ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "gif", byteOutputStream);
        String base64String = Base64.getEncoder().encodeToString(byteOutputStream.toByteArray());
        System.out.println(base64String);
        // 1. 创建一封邮件
        Properties props = new Properties();
        props.setProperty("mail.smtp.host", "smtp-mail.outlook.com");
        props.setProperty("mail.smtp.port", "587");

        // 用于连接邮件服务器的参数配置（发送邮件时才需要用到）
        Session session= Session.getInstance(props);        // 根据参数配置，创建会话对象（为了发送邮件准备的）
        MimeMessage message = new MimeMessage(session);     // 创建邮件对象

        /*
         * 也可以根据已有的eml邮件文件创建 MimeMessage 对象
         * MimeMessage message = new MimeMessage(session, new FileInputStream("MyEmail.eml"));
         */

        // 2. From: 发件人
        //    其中 InternetAddress 的三个参数分别为: 邮箱, 显示的昵称(只用于显示, 没有特别的要求), 昵称的字符集编码
        //    真正要发送时, 邮箱必须是真实有效的邮箱。
        message.setFrom(new InternetAddress("lidelin10@outloo.com", "USER_AA", "UTF-8"));

        // 3. To: 收件人
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress("lidelin10@outloo.com", "USER_CC", "UTF-8"));
//        //    To: 增加收件人（可选）
//        message.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress("dd@receive.com", "USER_DD", "UTF-8"));
//        //    Cc: 抄送（可选）
//        message.setRecipient(MimeMessage.RecipientType.CC, new InternetAddress("ee@receive.com", "USER_EE", "UTF-8"));
//        //    Bcc: 密送（可选）
//        message.setRecipient(MimeMessage.RecipientType.BCC, new InternetAddress("ff@receive.com", "USER_FF", "UTF-8"));

        // 4. Subject: 邮件主题
        message.setSubject("TEST邮件主题", "UTF-8");

        // 5. Content: 邮件正文（可以使用html标签）
        message.setContent("TEST这是邮件正文。。。", "text/html;charset=UTF-8");

        // 6. 设置显示的发件时间
        message.setSentDate(new Date());

        // 7. 保存前面的设置
        message.saveChanges();
        Transport.send(message);
    }

}
