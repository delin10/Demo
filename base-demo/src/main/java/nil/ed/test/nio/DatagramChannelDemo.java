package nil.ed.test.nio;

import nil.ed.test.util.ChannelUtils;
import nil.ed.test.util.ThreadUtils;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

/**
 * @author lidelin.
 */
public class DatagramChannelDemo {

    public static void main(String[] args) {
        InetSocketAddress localhost = new InetSocketAddress("localhost", 7890);
        ThreadUtils.startThreadWithExceptionAutoCatch(() -> {
            try (DatagramChannel server = DatagramChannel.open()) {
                server.bind(new InetSocketAddress(7890));
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                while (true) {
                    SocketAddress address = server.receive(buffer);
                    System.out.println("from " + address + ":" + ChannelUtils.asString(buffer));
                }
            }
        });

        ThreadUtils.startThreadWithExceptionAutoCatch(() -> {
            try (DatagramChannel channel = DatagramChannel.open()) {
                channel.send(ChannelUtils.asBuffer("hello"), localhost);
            }
        });

        ThreadUtils.startThreadWithExceptionAutoCatch(() -> {
            try (DatagramChannel channel = DatagramChannel.open()) {
                channel.connect(localhost);
                ChannelUtils.writeString("dadasdasdsad", channel);
            }
        });

        ThreadUtils.startThreadWithExceptionAutoCatch(() -> {
            try (DatagramChannel channel = DatagramChannel.open()) {
                channel.bind(new InetSocketAddress(9086));
                channel.connect(localhost);
                ChannelUtils.writeString("binded: dadasdasdsad", channel);
            }
        });


    }

}
