package nil.ed.test.util;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * @author lidelin.
 */
public class ChannelUtils {

    public static String readString(ByteBuffer buffer, ReadableByteChannel channel) throws IOException {
        int n = 0;
        StringBuilder builder = new StringBuilder();
        while ((n = channel.read(buffer)) > 0) {
            builder.append(asString(buffer));
            buffer.clear();
            System.out.println("Read: " + new String(buffer.array()));
        }
        return builder.toString();
    }

    public static String asString(ByteBuffer buffer) {
        return new String(buffer.array());
    }

    public static ByteBuffer asBuffer(String str) {
        return ByteBuffer.wrap(str.getBytes());
    }

    public static void writeString(String str, GatheringByteChannel channel) throws IOException {
        ArrayList<ByteBuffer> ls = new ArrayList<>();
        byte[] bytes = str.getBytes();
        long n = channel.write(scatteringBytes(bytes, ByteBuffer::allocateDirect, 1024));
        System.out.println("Write " + n + " bytes");
    }

    public static ByteBuffer[] scatteringBytes(byte[] bytes, Function<Integer, ByteBuffer> supplier, int oneBufferSize) {
        int cursor = 0;
        List<ByteBuffer> bufferList = new ArrayList<>(bytes.length / oneBufferSize + 1);
        while (cursor < bytes.length) {
            ByteBuffer buffer = supplier.apply(oneBufferSize);
            int size = Math.min(bytes.length - cursor, oneBufferSize);
            buffer.put(bytes, cursor, size).flip();
            cursor += size;
            bufferList.add(buffer);
        }
        return bufferList.toArray(new ByteBuffer[0]);
    }

}
