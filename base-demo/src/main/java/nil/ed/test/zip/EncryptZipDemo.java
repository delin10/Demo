package nil.ed.test.zip;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ThreadLocalRandom;

import net.lingala.zip4j.io.outputstream.ZipOutputStream;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.model.enums.CompressionLevel;
import net.lingala.zip4j.model.enums.CompressionMethod;
import net.lingala.zip4j.model.enums.EncryptionMethod;

/**
 * @author lidelin.
 */
public class EncryptZipDemo {

    private static final String PWD_ARR = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
    private static final int PWD_LEN = 10;

    public static void main(String[] args) throws IOException {
        byte[] out = encryptZip("的军事基地将阿苏妲己".getBytes(StandardCharsets.UTF_8), "1234");
        try (OutputStream outputStream = Files.newOutputStream(Paths.get("/Users/lidelin10/lidelin/data/x.zip"), StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE)){
            outputStream.write(out);
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < PWD_LEN; ++i) {
            int idx = ThreadLocalRandom.current().nextInt(PWD_ARR.length());
            builder.append(PWD_ARR.charAt(idx));
        }
        System.out.println(builder);
    }

    private static byte[] encryptZip(byte[] in, String pwd) throws IOException {
        ZipParameters parameters = new ZipParameters();
        parameters.setFileNameInZip("的军事基地将阿苏妲己.txt");
        //压缩方式
        parameters.setCompressionMethod(CompressionMethod.DEFLATE);
        // 压缩级别
        parameters.setCompressionLevel(CompressionLevel.NORMAL);
        parameters.setEncryptFiles(true);
        //加密方式
        parameters.setEncryptionMethod(EncryptionMethod.AES);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(byteArrayOutputStream, pwd.toCharArray(), StandardCharsets.UTF_8)){
            zipOutputStream.putNextEntry(parameters);
            zipOutputStream.write(in);
        }
        return byteArrayOutputStream.toByteArray();
    }
}
