package nil.ed.test.nio;

import nil.ed.test.util.ChannelUtils;
import nil.ed.test.util.ThreadUtils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author lidelin.
 */
public class NonBlockingServerSocketDemo {

    public static void main(String[] args) {
        ThreadUtils.startThread(() -> {
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            try (ServerSocketChannel ch = ServerSocketChannel.open()) {
                ch.bind(new InetSocketAddress("127.0.0.1", 8099));
                ch.configureBlocking(false);
                while (true) {
                    SocketChannel client = ch.accept();
                    if (client == null) {
                        continue;
                    }
                    System.out.println("Accepted: " + client.getRemoteAddress());
                    ThreadUtils.startThread(() -> {
                        try (SocketChannel c = client) {
                            String result = ChannelUtils.readString(buffer, client);
                            System.out.println("return from " + c.getRemoteAddress().toString() + ":" + result);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        AtomicInteger acc = new AtomicInteger();
        ThreadUtils.startThread(() -> {
            int count = 3000;
            while (count-- > 0) {
                try  {
                    SocketChannel client = SocketChannel.open();
                    client.configureBlocking(false);
                    client.connect(new InetSocketAddress("127.0.0.1", 8099));
                    System.out.println(client.isConnectionPending());
                    ThreadUtils.startThread(() -> {
                        try {
                            while (!client.finishConnect()) {
                                TimeUnit.SECONDS.sleep(1);
                                System.out.println("Connect pending...");
                            }
                            System.out.println("Send...");
                            ChannelUtils.writeString("xsaadadassefrefwqwq", client);
                        } catch (IOException | InterruptedException e) {
                            e.printStackTrace();
                        } finally {
                            acc.incrementAndGet();
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        System.out.println("client connection = " + acc.get());

    }

}
