package nil.ed.test.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.Collections;

public class SeleniumTest {

    public static void main(String[] args) throws URISyntaxException, IOException {
        //参数配置
        System.setProperty("webdriver.chrome.driver",
                "/Users/lidelin/delin/workspace/Demo/base-demo/src/main/resources/chromedriver");
        ChromeDriver driver;
        ChromeOptions option = new ChromeOptions();
        option.addArguments("no-sandbox");//禁用沙盒
        option.setHeadless(true);
        //通过ChromeOptions的setExperimentalOption方法，传下面两个参数来禁止掉谷歌受自动化控制的信息栏
        option.setExperimentalOption("useAutomationExtension", true);
        option.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
        driver = new ChromeDriver(option);
        driver.get("file:///Users/lidelin/delin/workspace/Demo/data/svg/test.svg");
        byte[] bytes = driver.findElement(By.tagName("svg")).getScreenshotAs(OutputType.BYTES);
        System.out.println(ImageIO.read(new ByteArrayInputStream(bytes)).getWidth());
        try (OutputStream outputStream = new FileOutputStream("/Users/lidelin/delin/workspace/Demo/data/svg/test-out.png")) {
            outputStream.write(bytes);
        }

        System.out.println(ImageIO.read(new ByteArrayInputStream(bytes)).getWidth());
        driver.close();
    }
}
