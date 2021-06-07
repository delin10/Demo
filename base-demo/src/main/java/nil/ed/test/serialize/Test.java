package nil.ed.test.serialize;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @author lidelin.
 */
public class Test {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        // 测试序列化跟访问修饰符的关系
        SerializableSuperClass serializableSuperClass = new SerializableSuperClass();
        SerializableSuperClass.setaStatic("aStatic");
        SerializableSuperClass.setbStatic("bStatic");
        SerializableSuperClass.setcStatic("cStatic");
        SerializableSuperClass.setdStatic("dStatic");
        serializableSuperClass.setA("a");
        serializableSuperClass.setB("b");
        serializableSuperClass.setC("c");
        serializableSuperClass.setD("d");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream outputStream = new ObjectOutputStream(byteArrayOutputStream);
        outputStream.writeObject(serializableSuperClass);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        SerializableSuperClass.setaStatic(null);
        SerializableSuperClass.setbStatic(null);
        SerializableSuperClass.setcStatic(null);
        SerializableSuperClass.setdStatic(null);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        System.out.println(objectInputStream.readObject());
        System.out.println(SerializableSuperClass.getaStatic());
        System.out.println(SerializableSuperClass.getbStatic());
        System.out.println(SerializableSuperClass.getcStatic());
        System.out.println(SerializableSuperClass.getdStatic());
        // 测试如果继承不可序列化父类，而子类可序列化，是否能对父类进行序列化处理

        // 测试如果继承可序列化父类，而子类可序列化，是否能对父类进行序列化处理
    }
}
