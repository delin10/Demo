package nil.ed.test.nio;

import nil.ed.test.util.ChannelUtils;
import nil.ed.test.util.ThreadUtils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author lidelin.
 */
public class SelectorDemo {

    public static void main(String[] args) throws IOException {
        Selector selector = select();
        ServerSocketChannel ch = ServerSocketChannel.open();
        ch.bind(new InetSocketAddress("127.0.0.1", 8099));
        ch.configureBlocking(false);
        ch.register(selector, A);
        SocketChannel client = SocketChannel.open();
        client.configureBlocking(false);
        client.register(selector, RWC);
        client.connect(new InetSocketAddress("127.0.0.1", 8099));
    }

    public static Selector select() throws IOException {
        Selector selector = Selector.open();
        ThreadUtils.startThreadWithExceptionAutoCatch(() -> {
           while (true) {
               int count = selector.select(3000);
               if (count == 0) {
//                   System.out.println("No selected, continue...");
                   continue;
               }
               System.out.println("Select count = " + count);
               Set<SelectionKey> keySet = selector.selectedKeys();
               Iterator<SelectionKey> it = keySet.iterator();
               while (it.hasNext()) {
                    SelectionKey key = it.next();
                    processKey(key);
                    it.remove();
               }
           }
        });
        return selector;
    }

    private static final int RW = SelectionKey.OP_READ | SelectionKey.OP_WRITE;
    private static final int RWC = RW | SelectionKey.OP_CONNECT;
    private static final int A = SelectionKey.OP_ACCEPT;
    public static void processKey(SelectionKey key) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        if (key.isReadable()) {
            ReadableByteChannel channel = (ReadableByteChannel) key.channel();
            System.out.println(ChannelUtils.readString(buffer, channel));
        } else if (key.isWritable()){
//            WritableByteChannel channel = (WritableByteChannel) key.channel();
//            channel.write(ChannelUtils.asBuffer("hello writable"));
        } else if (key.isAcceptable()) {
            ServerSocketChannel channel = (ServerSocketChannel) key.channel();
            SocketChannel accept = channel.accept();
            accept.configureBlocking(false);
            System.out.println("From " + accept.getRemoteAddress());
            accept.register(key.selector(), RW, channel);
        } else if (key.isConnectable()) {
            SocketChannel channel = (SocketChannel) key.channel();
            if (channel.finishConnect()) {
                System.out.println("connect ok!");
                channel.write(ChannelUtils.asBuffer("hello connectable"));
            } else {
                System.out.println("connect not ok!");
            }
        }
    }

}
