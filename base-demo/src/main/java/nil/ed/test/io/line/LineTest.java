package nil.ed.test.io.line;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author lidelin10
 * @date 2022/7/20 上午8:13
 */
public class LineTest {

    public static void main(String[] args) throws IOException {
//        try (OutputStream outputStream = Files.newOutputStream(Paths.get("/Users/lidelin10/lidelin/private/Demo/data", "line.file"))) {
//            List<String> result = new LinkedList<>();
//            IntStream.range(0, 10_000_000).forEach(e -> {
//                result.add(String.valueOf(e));
//                if (result.size() > 100_000) {
//                    String str = String.join("\n", result);
//                    try {
//                        outputStream.write(str.getBytes());
//                        outputStream.flush();
//                    } catch (IOException ioException) {
//                        ioException.printStackTrace();
//                    }
//                    result.clear();
//                }
//            });
//        }

        Files.lines(Paths.get("/Users/lidelin10/lidelin/private/Demo/data", "line.file")).skip(100000).limit(100).forEach(System.out::println);
    }
}
