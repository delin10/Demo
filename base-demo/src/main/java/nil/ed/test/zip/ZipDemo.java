package nil.ed.test.zip;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.time.temporal.TemporalAdjusters;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * @author lidelin.
 */
public class ZipDemo {
    public static void main(String[] args) throws IOException {
        ZipFile zipFile = new ZipFile("/Users/admin/delin/test/1.97.zip"/*, Charset.forName("gbk")*/);
        Enumeration<? extends ZipEntry> zipEntryEnumeration = zipFile.entries();
        while (zipEntryEnumeration.hasMoreElements()) {
            ZipEntry entry = zipEntryEnumeration.nextElement();
            System.out.println(entry.getName());
            InputStream in = zipFile.getInputStream(entry);
            System.out.println(IOUtils.readLines(in).size());
        }
    }
}
