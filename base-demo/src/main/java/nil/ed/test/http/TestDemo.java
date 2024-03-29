package nil.ed.test.http;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class TestDemo {
    public static void main(String[] args) throws IOException {
        ///////////
        HttpURLConnection urlConnection2 = (HttpURLConnection) new URL("http://192.168.18.130:8080/api/tos/4567").openConnection();
        urlConnection2.setRequestMethod("POST");
        // [       3426153�  ���   ]
        System.out.println(IOUtils.readLines(urlConnection2.getInputStream(), StandardCharsets.UTF_8.name()));
        ///////////////////

        urlConnection2 = (HttpURLConnection) new URL("http://192.168.18.130:8080/api/test").openConnection();
        urlConnection2.setRequestMethod("GET");
        // [       3426153�  ���   ]
        System.out.println(IOUtils.readLines(urlConnection2.getInputStream(), StandardCharsets.UTF_8.name()));
        ///////////////////
    }

}
