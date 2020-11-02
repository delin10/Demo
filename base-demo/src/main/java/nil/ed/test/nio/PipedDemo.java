package nil.ed.test.nio;

import nil.ed.test.util.ChannelUtils;
import nil.ed.test.util.ThreadUtils;

import java.nio.ByteBuffer;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.Pipe;
import java.nio.channels.ScatteringByteChannel;

/**
 * @author lidelin.
 */
public class PipedDemo {

    public static void main(String[] args) throws Exception {
        Pipe pipe = Pipe.open();
        ThreadUtils.startThreadWithExceptionAutoCatch(() -> {
            GatheringByteChannel w = pipe.sink();
            ChannelUtils.writeString("ddadsad", w);
        });

        ThreadUtils.startThreadWithExceptionAutoCatch(() -> {
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            ScatteringByteChannel w = pipe.source();
            System.out.println(ChannelUtils.readString(buffer, w));
        });
    }

}
