package nil.ed.test.java;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @author lidelin10
 * @date 2022/3/30 下午3:35
 */
public class SimpleDateTimeFormatDemo {

    public static void main(String[] args) {
        SimpleDateFormat sdf =  new SimpleDateFormat("''yyyy-MM-dd HH:mm:ss", Locale.US);
        sdf.setTimeZone(TimeZone.getTimeZone("+8"));
        System.out.println(sdf.format(new Date()));
    }

}
