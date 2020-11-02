package nil.ed.test.nio;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

/**
 * @author lidelin.
 */
public class BufferDemo {

    public static void main(String[] args) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        byte[] bytes = new byte[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        InputStream in = new ByteArrayInputStream(bytes);
        ReadableByteChannel channel = Channels.newChannel(in);
        channel.read(buffer);
        buffer.flip();
        while (buffer.hasRemaining()) {
            System.out.println(buffer.get());
        }

        // 二次读
        buffer.flip();
        while (buffer.hasRemaining()) {
            System.out.println(buffer.get());
        }
        // 回到开始读
        buffer.flip();
        for (int i = 0; i < 5 && buffer.hasRemaining(); ++i) {
            System.out.println(buffer.get());
        }
        buffer.rewind();
        while (buffer.hasRemaining()) {
            System.out.println(buffer.get());
        }
//        buffer.compact()
    }

}
