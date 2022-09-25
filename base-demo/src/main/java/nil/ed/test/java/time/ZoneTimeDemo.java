package nil.ed.test.java.time;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.internal.bind.TypeAdapters;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lombok.Data;

/**
 * @author lidelin10
 * @date 2022/3/29 上午11:44
 */
public class ZoneTimeDemo {
    public static void main(String[] args) {
        Map<String, ZoneId> zoneIdMap = new HashMap<>(8, 1);
        ZoneId sevenZone = ZoneId.of("+07:00");
        ZoneId eightZone = ZoneId.of("+08:00");
        zoneIdMap.put("SG", eightZone);
        zoneIdMap.put("MY", eightZone);
        zoneIdMap.put("PH", eightZone);
        zoneIdMap.put("ID", sevenZone);
        zoneIdMap.put("VN", sevenZone);
        zoneIdMap.put("TH", sevenZone);
        // 获取越南的当前时间：2022-03-30 16:13:58
        ZonedDateTime zonedDateTime = ZonedDateTime.now(zoneIdMap.get("VN"));
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        // 格式化越南时间：2022-03-30 16:13:58
        System.out.println("格式化越南时间: " + zonedDateTime.format(dateTimeFormatter));
        // 将越南时间格式化为新加坡时间：2022-03-30 17:13:58
        System.out.println("将越南时间格式化为新加坡时间：" + zonedDateTime.format(dateTimeFormatter.withZone(zoneIdMap.get("SG"))));
        ZonedDateTime myParsedDt = ZonedDateTime.parse("2022-03-30 17:11:41", dateTimeFormatter.withZone(zoneIdMap.get("MY")));
        // 2022-03-30T17:11:41+08:00
        System.out.println("字符串时间转换成马来西亚时间：" + myParsedDt);
        // 获取某天0点
        System.out.println("获取当天0点：" + myParsedDt.truncatedTo(ChronoUnit.DAYS));
        // 获取某天最后1分钟：23:59:59
        System.out.println("获取某天最后1分钟" + myParsedDt.withHour(23).withMinute(59).withSecond(59));
        // 获取跟马来西亚相同字面时间（2022-03-30T17:11:41+08:00）的泰国时间：2022-03-30T17:11:41+07:00
        System.out.println("获取跟马来西亚相同字面时间（2022-03-30T17:11:41+08:00）的泰国时间：" + myParsedDt.withZoneSameLocal(zoneIdMap.get("TH")));
        // 进行时间加减法
        System.out.println("进行时间加减法： " + myParsedDt.plus(1, ChronoUnit.DAYS).minusDays(1).plusHours(1).minusHours(1).plusMinutes(1).minusMinutes(1).plusSeconds(1).minusSeconds(1));
        // 转换成long类型：秒级别
        System.out.println("转换成long类型（秒级别）：" + myParsedDt.toEpochSecond());
        // 转换成long类型：毫秒级别 = 秒 * 1000
        System.out.println("转换成long类型（毫秒级别）：" + myParsedDt.toInstant().toEpochMilli());
        // 精准当前时间的时间戳获取（秒级别）
        System.out.println("精准当前时间的时间戳获取（秒级别））：" + Instant.now().getEpochSecond());
        // 精准当前时间的时间戳获取（毫秒级别）
        System.out.println("精准当前时间的时间戳获取（毫秒级别））：" + Instant.now().toEpochMilli());
        // 时间戳转换
        System.out.println("时间戳转换（秒）：" + Instant.ofEpochSecond(myParsedDt.toEpochSecond()).atZone(zoneIdMap.get("MY")));
        System.out.println("时间戳转换（毫秒）：" + Instant.ofEpochMilli(myParsedDt.toInstant().toEpochMilli()).atZone(zoneIdMap.get("MY")));
        // Instant和ZonedDateTime的转换
        System.out.println(Instant.now().atZone(zoneIdMap.get("MY")));

        // 转换时区
        System.out.println(myParsedDt.withZoneSameInstant(zoneIdMap.get("TH")));
        frontFormat(zoneIdMap);
    }

    static void zoneTrans(Map<String, ZoneId> zoneIdMap) {
        ZonedDateTime zonedDateTime = ZonedDateTime.now(zoneIdMap.get("VN"));
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        // 将越南时区转换成新加坡时区
        zonedDateTime = zonedDateTime.withZoneSameInstant(zoneIdMap.get("SG"));
        // 将越南时区的字面时间转换成新加坡字面时间
        zonedDateTime = zonedDateTime.withZoneSameLocal(zoneIdMap.get("SG"));
        // 将越南时间格式化新加坡时间
        String result = zonedDateTime.format(dateTimeFormatter.withZone(zoneIdMap.get("SG")));
    }

    static void timeFormat(Map<String, ZoneId> zoneIdMap) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        ZonedDateTime zonedDateTime = ZonedDateTime.now(zoneIdMap.get("VN"));
        // 格式化越南时间
        String result = zonedDateTime.format(dateTimeFormatter);
        // 格式化越南时间为新加坡时间
        zonedDateTime.format(dateTimeFormatter.withZone(zoneIdMap.get("SG")));
    }

    static void timeOps(Map<String, ZoneId> zoneIdMap) {
        ZonedDateTime zonedDateTime = ZonedDateTime.now(zoneIdMap.get("VN"));
        // 获取某天0点
        zonedDateTime.truncatedTo(ChronoUnit.DAYS);
        // 获取某天最后1分钟：23:59:59
        zonedDateTime.withHour(23).withMinute(59).withSecond(59);
        // 进行时间加减法
        zonedDateTime.plus(1, ChronoUnit.DAYS).minusDays(1).plusHours(1).minusHours(1).plusMinutes(1).minusMinutes(1).plusSeconds(1).minusSeconds(1);
    }

    static void timestampOps(Map<String, ZoneId> zoneIdMap) {
        ZonedDateTime zonedDateTime = ZonedDateTime.now(zoneIdMap.get("VN"));
        // 转换成long类型：秒级别
        zonedDateTime.toEpochSecond();
        // 转换成long类型：毫秒级别 = 秒 * 1000
        zonedDateTime.toInstant().toEpochMilli();
        // 精准当前时间的时间戳获取（秒级别）
        Instant.now().getEpochSecond();
        // 精准当前时间的时间戳获取（毫秒级别）
        Instant.now().toEpochMilli();
        // 时间戳转时区时间　(秒级别)
        Instant.ofEpochSecond(Instant.now().getEpochSecond()).atZone(zoneIdMap.get("MY"));
        // 时间戳转时区时间　(毫秒级别)
        Instant.ofEpochSecond(Instant.now().toEpochMilli()).atZone(zoneIdMap.get("MY"));
    }

    static void frontFormat(Map<String, ZoneId> zoneIdMap) {
        TestModel testModel = new TestModel();
        testModel.setZonedDateTime(ZonedDateTime.now());
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String venture = "VN";
        String result;
        Gson gson = new GsonBuilder().registerTypeAdapter(ZonedDateTime.class, new TypeAdapter<ZonedDateTime>() {
                    @Override
                    public void write(JsonWriter jsonWriter, ZonedDateTime zonedDateTime) throws IOException {
                        if (zonedDateTime == null) {
                            jsonWriter.nullValue();
                            return;
                        }
                        jsonWriter.value(zonedDateTime.format(dateTimeFormatter));
                    }

                    @Override
                    public ZonedDateTime read(JsonReader jsonReader) throws IOException {
                        String dateTime = jsonReader.nextString();
                        return ZonedDateTime.parse(dateTime, dateTimeFormatter.withZone(zoneIdMap.get(venture)));
                    }
                }
        ).create();
        System.out.println(result = gson.toJson(testModel));
        System.out.println(gson.fromJson(result, TestModel.class));
    }

    @Data
    public static class TestModel {
        private ZonedDateTime zonedDateTime;
    }
}
