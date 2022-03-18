package nil.ed.test.db.mysql.binlog;

import java.io.DataInputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import nil.ed.test.util.MemUnit;

/**
 * @author lidelin.
 */
public class BinLogParserDemo {

    private static final int V4 = 4;
    private static final int V3 = 3;
    private static final int V1 = 1;

    public static void main(String[] args) throws Exception {
        String dataDir = "/usr/local/var/mysql/";
        String binLogName = "mysql-bin.000001";
        byte[] bytes = new byte[(int) MemUnit.MB.toBytes(1)];
        try (DataInputStream inputStream = new DataInputStream(Files.newInputStream(Paths.get(dataDir, binLogName)))) {
            int n = inputStream.read(bytes);
            int idx = 0;
            if (n > 4) {
                idx += 4;
                byte[] magicNumber = Arrays.copyOfRange(bytes, 0, idx);
                FormatUtils.Line.formatKv("魔数", new String(magicNumber));
            } else {
                System.err.println("Failed");
                return;
            }
            int version = determineVersion(bytes, idx);
            EventType eventType = getEventType(bytes, idx);
            // assert version == 4;
            FormatUtils.Line.formatKv("版本", version);
            FormatUtils.Line.formatKv("时间戳(单位: sec)", getTimestamp(bytes, idx));
            FormatUtils.Line.formatKv("事件类型", eventType);
            FormatUtils.Line.formatKv("服务器id", getServerId(bytes, idx));
            FormatUtils.Line.formatKv("记录长度", getEventLength(bytes, idx));
            FormatUtils.Line.formatKv("Next Pos", getNextPosition(bytes, idx));
            FormatUtils.Line.formatKv("Flags", getFlags(bytes, idx));
            FormatUtils.Line.formatKv("bin log version", ByteExtractor.LittleEndian.getShort(bytes, idx + 19));
            FormatUtils.Line.formatKv("server version", ByteExtractor.LittleEndian.getString(bytes, idx + 21, 50));
            FormatUtils.Line.formatKv("create ts", ByteExtractor.LittleEndian.getInt(bytes, idx + 71));
            byte headerLen = ByteExtractor.LittleEndian.getByte(bytes, idx + 75);
            FormatUtils.Line.formatKv("header length", headerLen);
            FormatUtils.Line.formatKv("post header", ByteExtractor.LittleEndian.getString(bytes, idx + 76, headerLen));


            while (true) {
                if (n - idx < 91) {
                    shiftEle(bytes, idx, Math.min(n - idx, 0), -idx);
                    n = inputStream.read(bytes, Math.min(n - idx, 0), bytes.length - Math.min(n - idx, 0));
                    idx = 0;
                    if (n <= 0) {
                        break;
                    }
                }
                idx = getNextPosition(bytes, idx);
                FormatUtils.Line.formatKv("时间戳(单位: sec)", getTimestamp(bytes, idx));
                FormatUtils.Line.formatKv("事件类型", getEventType(bytes, idx));
                FormatUtils.Line.formatKv("服务器id", getServerId(bytes, idx));
                FormatUtils.Line.formatKv("记录长度", getEventLength(bytes, idx));
                FormatUtils.Line.formatKv("Next Pos", getNextPosition(bytes, idx));
                FormatUtils.Line.formatKv("Flags", getFlags(bytes, idx));
                byte[] rowData = new byte[getEventLength(bytes, idx)];
                System.arraycopy(bytes, idx, rowData, 0, getEventLength(bytes, idx));
                System.out.println(new String(rowData, StandardCharsets.US_ASCII));
                if (idx + 31 > n) {
                    shiftEle(bytes, idx, Math.min(n - idx, 0), -idx);
                    n = inputStream.read(bytes, Math.min(n - idx, 0), bytes.length - Math.min(n - idx, 0));
                    idx = 0;
                    if (n <= 0) {
                        break;
                    }
                }
                FormatUtils.Line.formatKv("TableId", ByteExtractor.LittleEndian.getLong(bytes, idx + 19, 6));
                int column = ByteExtractor.LittleEndian.getByte(bytes, idx + 27);
                FormatUtils.Line.formatKv("Column num", column);
                FormatUtils.Line.formatKv("Column num", ByteExtractor.LittleEndian.getByte(bytes, idx + 28));
                FormatUtils.Line.formatKv("After Image Bits", Integer.toBinaryString(ByteExtractor.LittleEndian.getByte(bytes, idx + 29)));

                if (idx + getEventLength(bytes, idx) > n) {
                    shiftEle(bytes, idx, Math.min(n - idx, 0), -idx);
                    n = inputStream.read(bytes, Math.min(n - idx, 0), bytes.length - Math.min(n - idx, 0));
                    idx = 0;
                    if (n <= 0) {
                        break;
                    }
                }
                if (getEventType(bytes, idx) == EventType.WRITE_ROWS_EVENTv2) {
                    break;
                }
                System.out.println("=====");
            }

        }
    }

    public static int getPackedLength(byte[] arr, int offset) {
        if ((arr[offset] | 0xff) < 251) {
            return 1;
        }

        if ((arr[offset] | 0xff) < 253) {
            return 3;
        }

        if ((arr[offset] | 0xff) < 253) {
            return 3;
        }
        return 1;
    }

    public static void shiftEle(byte[] arr, int offset, int length, int distance) {
        if (distance == 0 || length <= 0) {
            return;
        }
        System.arraycopy(arr, offset, arr, offset + distance, offset + length - offset);
    }

    public static short getBinLogEvent(byte[] bytes, int idx) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes, idx + 17, 2).order(ByteOrder.LITTLE_ENDIAN);
        return byteBuffer.getShort();
    }

    public static short getFlags(byte[] bytes, int idx) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes, idx + 17, 2).order(ByteOrder.LITTLE_ENDIAN);
        return byteBuffer.getShort();
    }

    public static int getNextPosition(byte[] bytes, int idx) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes, idx + 13, 4).order(ByteOrder.LITTLE_ENDIAN);
        return byteBuffer.getInt();
    }

    public static int getEventLength(byte[] bytes, int idx) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes, idx + 9, 4).order(ByteOrder.LITTLE_ENDIAN);
        return byteBuffer.getInt();
    }

    public static int getServerId(byte[] bytes, int idx) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes, idx + 5, 4).order(ByteOrder.LITTLE_ENDIAN);
        return byteBuffer.getInt();
    }

    public static EventType getEventType(byte[] bytes, int idx) {
        return EventType.findByCode(bytes[idx + 4]);
    }

    public static int getTimestamp(byte[] bytes, int idx) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes, idx, 4).order(ByteOrder.LITTLE_ENDIAN);
        return byteBuffer.getInt();
    }

    public static int determineVersion(byte[] bytes, int idx) {
        if (bytes[idx + 4] == 15) {
            return V4;
        }
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes, idx + 9, 4);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        int eventLen = byteBuffer.getInt();
        if (eventLen == 75) {
            return V3;
        } else if (eventLen == 69) {
            return V1;
        }
        throw new IllegalArgumentException();
    }
}
