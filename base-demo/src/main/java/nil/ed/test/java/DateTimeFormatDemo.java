package nil.ed.test.java;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author lidelin.
 */
public class DateTimeFormatDemo {

    public static void main(String[] args) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY-MM-dd");
        LocalDate localDate = LocalDate.of(2019, 12, 31);
        System.out.println(formatter.format(localDate));
    }

}
