package nil.ed.test.db.mysql.binlog;

/**
 * @author lidelin.
 */
public class ByteExtractor {

    public static class LittleEndian {

        private int[] SHIFT_BITS = {0, 8, 16, };

        public static int getInt(byte[] bytes, int idx) {
            return (((bytes[idx + 3]) << 24) | ((bytes[idx + 2] & 0xff) << 16) | ((bytes[idx + 1] & 0xff) << 8) | ((bytes[idx] & 0xff)));
        }

        public static long getLong(byte[] bytes, int from, int len) {
            if (len > 8) {
                throw new IllegalArgumentException();
            }
            long result = 0;
            int i = 0;
            for (int k = from; k < from + len; k++) {
                if (i == 0) {
                    result = bytes[k] & 0xff;
                } else if (i == 7) {
                    result |= bytes[k] << i * 8;
                } else {
                    result |= (bytes[k] & 0xff) << i * 8;
                }
                ++i;
            }
            return result;
        }

        public static short getShort(byte[] bytes, int idx) {
            return (short) (((bytes[idx + 1] & 0xff) << 8) | ((bytes[idx] & 0xff)));
        }

        public static byte getByte(byte[] bytes, int idx) {
            return bytes[idx];
        }

        public static String getString(byte[] bytes, int idx, int len) {
            return new String(bytes, idx, len);
        }
    }

}
