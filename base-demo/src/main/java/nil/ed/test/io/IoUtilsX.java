package nil.ed.test.io;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.stream.IntStream;

import org.apache.commons.io.IOUtils;

/**
 * @author lidein.
 */
public class IoUtilsX {

    public static String readFullText(String file) throws IOException {
        try (InputStream inputStream = new FileInputStream(new File(file))){
            return String.join(System.lineSeparator(), IOUtils.readLines(inputStream, StandardCharsets.UTF_8.name()));
        }
    }

    public static String base64File(String basePath, String file) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try (InputStream inputStream = Files.newInputStream(Paths.get(basePath, file))){
            byte[] buffer = new byte[4096];
            int n = 0;
            while ((n = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, n);
            }
        }
        return Base64.getEncoder().encodeToString(outputStream.toByteArray());
    }

    public static void main(String[] args) throws IOException {
        String basePath = "/Users/lidelin10/lidelin/workspace/oga-atprest/oga-atprest-service/target/classes/com/alibaba/oga/atprest/controller/order";
        String basePath2 = "/Users/lidelin10/lidelin/workspace/oga-atprest/oga-atprest-service/target/classes/com/alibaba/oga/atprest/utils";
        String file0 = "OrderManagementController.class";
        String file2 = "POIOrderExport.class";

        String f = "/Users/lidelin10/lidelin/workspace/ogb-tboservice/tbo-biz/target/classes/com/alibaba/ogb/tboservice/biz/open/impl/OrderManagementServiceImpl.class";
        System.out.println(base64File("", f));
    }

}
