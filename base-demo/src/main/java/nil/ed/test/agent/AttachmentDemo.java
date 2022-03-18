package nil.ed.test.agent;

import java.io.IOException;

import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;

/**
 * @author lidelin.
 */
public class AttachmentDemo {

    public static void main(String[] args) throws IOException, AttachNotSupportedException {
        System.out.println(VirtualMachine.attach(String.valueOf(24103)).getAgentProperties());

    }

}
