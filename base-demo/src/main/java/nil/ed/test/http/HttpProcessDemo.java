package nil.ed.test.http;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.ResponseContentEncoding;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.util.StreamUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPInputStream;

/**
 * @author lidelin.
 */
public class HttpProcessDemo {

    public static void main(String[] args) throws Exception {
        /*
        1. HttpConnection.
         */
        String testUrl = "http://localhost:8080/get";
        HttpURLConnection urlConnection = (HttpURLConnection) new URL(testUrl).openConnection();
        // 设置超时时间
        urlConnection.setReadTimeout(10_000);
        urlConnection.setConnectTimeout(10_000);
        InputStream in = urlConnection.getInputStream();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        StreamUtils.copy(in, outputStream);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
        // [       3426153�  ���   ]
        System.out.println(IOUtils.readLines(inputStream, StandardCharsets.UTF_8.name()));
        inputStream.reset();
        GZIPInputStream gzipInputStream = new GZIPInputStream(inputStream);
        // [12345678]
        System.out.println(IOUtils.readLines(gzipInputStream, StandardCharsets.UTF_8.name()));

        /*
        2. HttpClient.
         */
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet(testUrl);
        // 客户端支持的编码类型.
        get.addHeader("Accept-Encoding", "*");
        HttpResponse httpResponse = httpClient.execute(get);
        // [12345678]
        System.out.println(IOUtils.readLines(httpResponse.getEntity().getContent(), StandardCharsets.UTF_8.name()));

    }
}
