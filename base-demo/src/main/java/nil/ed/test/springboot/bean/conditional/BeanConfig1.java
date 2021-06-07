package nil.ed.test.springboot.bean.conditional;

import com.google.common.annotations.Beta;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import nil.ed.anno.MyCondition;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.util.DigestUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author delin10
 * @since 2020/7/7
 **/
@Order(value = 99)
@Configuration
public class BeanConfig1 {

    @Bean
    @MyCondition
    public TestClass testClass() {
        return new TestClass("testClass");
    }

    @SuppressWarnings("UnstableApiUsage")
    public static void main(String[] args) throws IOException, DecoderException {
        /*
         bit：21676028
         mb: 2.58
         */
        BloomFilter<String> filter = BloomFilter.create(Funnels.stringFunnel(StandardCharsets.UTF_8), 1_0000, 0.00001);
        filter.put("123456");
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        filter.writeTo(outputStream);
        System.out.println("Bloom过滤器序列化后的大小：" + outputStream.size());
        System.out.println("转成16进制串的长度：" + new String(Hex.encodeHex(outputStream.toByteArray())).getBytes().length);
        System.out.println("1_0000条素材MD5长度：" + DigestUtils.md5DigestAsHex("dasd".getBytes()).getBytes().length * 1_0000);
        BloomFilter<String> f = BloomFilter.readFrom(new ByteArrayInputStream(Hex.decodeHex(new String(Hex.encodeHex(outputStream.toByteArray())).toCharArray())), Funnels.stringFunnel(StandardCharsets.UTF_8));
        System.out.println(f.mightContain("123456"));
        for (int i = 0; i < 100_0000; i++) {
            filter.put(ThreadLocalRandom.current().nextInt(1000) + "");
        }
        /*
        756 milli
         */
        String[] arr = new String[100_0000];
        for (int i = 0; i < 100_0000; i++) {
            arr[i] = ThreadLocalRandom.current().nextInt(1000) + "";
        }
        long start = System.currentTimeMillis();

        for (String e : arr) {
            filter.mightContain(e);
        }

        System.out.println(System.currentTimeMillis() - start);

        String text = "快爽小才华,诊股,旁趣,YY,爱奇艺阅读,皱纹,百度阅读,腐漫画,秒拍,大龄,追书神器,小红书,最右极速版,微信读书,探探,刷宝,克拉克拉,快看点,酒,红袖读书,17k小说,快点阅读,猫耳,起点读书,五粮液,会所,皮皮虾,轻之文库,借钱,网易云阅读,花椒直播,老花镜,快看阅读,听伴,对象,酷漫,yoo视频,QQ阅读,陌陌,清言,花倚,茅台,美白,离异,交友,深层清洁,麻将机,爱优漫,相亲,微鲤,漫客栈,模拟当官,饭团追书,捕鱼,迷说,知音漫客,小说,皮皮搞笑,语戏,多看阅读,分期乐,咚漫,淡斑,泸州老窖,网易蜗牛读书,掌阅,借款,抗皱,最右,趣专享,娇妻,虎牙直播,离婚,白头发,七猫,米读,哔哩,波波视频,翡翠,减肥,植发,茶叶,祛痘,色斑,雀斑,漫画,缺牙,小龅牙,岳父,岳母,女婿,离婚,董事长,入赘,主人,小说,捕鱼,交友,老公,豪车,老婆,豪门,落魄,公子,王爷,总裁,逆袭,家族,徒儿,徒弟,傻子,伪装,小产,废后,少爷,废婿,爽文,休书,退婚,岳风,师傅,师娘,陷害,适合男生,都市,婆婆,微商,闺蜜,佣兵,嫡女,战神,龙血,仙尊,穿越,外公,装穷,继承,将军,喜脉,身孕,太医,结婚,碎片,宠文,宠妻,黑帝,嘲笑,震惊,同学,大结局,官场,失踪,小神医,山村,劳动力,太子爷,村医,冷宫,重生,追妻,虐妻,玉玺,男主,下跪,皇上,本宫,龙袍,朕,酒,习包子, 天安门, 坦克, 共产夫妻, 万字符, 共惨党, 共产党, 自焚, 撒钱帝, 老黑公民, 借钱, 麻将机";

//        System.out.println(Arrays.stream(text.split(","))
//                .map(v -> "\"" + v.trim() + "\"")
//                .collect(Collectors.joining(",")));
        int count = 0;
        for (String word : text.split(",")) {
            if ((++count % 5) == 0) {
                count = 0;
                System.out.println();
            }
            System.out.print("\"" + word.trim() + "\",");
        }
    }

}
