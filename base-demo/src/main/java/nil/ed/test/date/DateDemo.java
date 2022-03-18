package nil.ed.test.date;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.UUID;

/**
 * @author lidelin.
 */
public class DateDemo {

    public static void main(String[] args) {
        LocalDateTime ldt = LocalDateTime.parse("2022-02-14 10:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println(ldt);
        Date.from(ldt.toInstant(ZoneOffset.ofHours(8)));
//        System.out.println(ZoneOffset.of("Asia/Shanghai"));
        System.out.println(UUID.randomUUID().toString().length());
    }

}
