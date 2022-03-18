package nil.ed.test.udp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.concurrent.TimeUnit;

/**
 * @author lidelin.
 */
public class UdpUtils {

    public static ByteBuffer waitReceive(String ip, int port) throws IOException {
        try (DatagramChannel channel = DatagramChannel.open()) {
            channel.bind(new InetSocketAddress(ip, port));
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            channel.receive(buffer);
            return buffer;
        }
    }

    public static ByteBuffer send(String ip, int port, ByteBuffer buffer) throws IOException {
        try (DatagramChannel channel = DatagramChannel.open()) {
            channel.send(buffer, new InetSocketAddress(ip, port));
            return buffer;
        }
    }

    public static void main(String[] args) {
        new Thread(() -> {
            while (true) {
                try {
                    ByteBuffer buffer = waitReceive("127.0.0.1", 12378);
                    System.out.println(new String(buffer.array()));
                    TimeUnit.SECONDS.sleep(1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(() -> {
            while (true) {
                try {
                    byte[] bytes = "1324567".getBytes();
                    ByteBuffer buffer = ByteBuffer.allocate(bytes.length + 4);
                    buffer.putInt(bytes.length).put(bytes);
                    send("127.0.0.1", 13212, buffer);
                    TimeUnit.SECONDS.sleep(1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
