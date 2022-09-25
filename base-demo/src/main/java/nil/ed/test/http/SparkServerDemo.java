package nil.ed.test.http;

import spark.Spark;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPOutputStream;

public class SparkServerDemo {
    public static void main(String[] args) {
        Spark.port(8080);
        Spark.get("/get", (req, resp) -> {
            resp.header("Content-Encoding", "gzip");
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            GZIPOutputStream outputStream = new GZIPOutputStream(byteArrayOutputStream);
            outputStream.write("12345678".getBytes(StandardCharsets.UTF_8));
            outputStream.finish();
            return byteArrayOutputStream.toByteArray();
        });
    }
}
