package nil.ed.test.util;

/**
 * @author lidelin.
 */
public class UnitUtils {
    public static String formatMem(double bytes) {
        double amount;
        if ((amount = MemUnit.BYTE.toGb(bytes)) >= 1.0) {
            return String.format("%.2fGB", amount);
        } else if ((amount = MemUnit.BYTE.toMb(bytes)) >= 1.0) {
            return String.format("%.2fMB", amount);
        } else if ((amount = MemUnit.BYTE.toKb(bytes)) >= 1.0) {
            return String.format("%.2fKB", amount);
        } else {
            return String.format("%.2fGB", amount);
        }
    }

    public static String formatTime(long millis) {
        long sec = millis / 1_000;
        long minutes = sec / 60;
        long hours = minutes / 60;
        long days = hours / 24;
        if (days >= 1) {
            return String.format("%02d天%02d小时%02d分%02d秒", days, hours % 24, minutes % 60, sec % 60);
        } else if (hours >= 1) {
            return String.format("%02d小时%02d分%02d秒", hours % 24, minutes % 60, sec % 60);
        } else if (minutes >= 1) {
            return String.format("%02d分%02d秒", minutes % 60, sec % 60);
        } else if (sec >= 1) {
            return String.format("%.2f秒", millis / 1000.0);
        } else {
            return String.format("%d毫秒", millis);
        }
    }
}
