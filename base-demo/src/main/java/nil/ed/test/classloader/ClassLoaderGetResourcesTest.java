package nil.ed.test.classloader;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

/**
 * @author lidelin.
 */
public class ClassLoaderGetResourcesTest {
    public static void main(String[] args) throws IOException {
        Enumeration<URL> urlEnumeration
                = ClassLoaderGetResourcesTest.class.getClassLoader().getResources("");
        while (urlEnumeration.hasMoreElements()) {
            System.out.println(urlEnumeration.nextElement());
        }

        Enumeration<URL> urlEnumeration2 = ClassLoader.getSystemResources("");
        while (urlEnumeration2.hasMoreElements()) {
            System.out.println(urlEnumeration2.nextElement());
        }

        System.out.println("xx===");
        Enumeration<URL> urlEnumeration3 = IOUtils.class.getClassLoader().getResources("org/apache");
        while (urlEnumeration3.hasMoreElements()) {
            System.out.println(urlEnumeration3.nextElement());
        }
        PathMatchingResourcePatternResolver resourcePatternResolver  = new PathMatchingResourcePatternResolver();
        Resource[] resources = resourcePatternResolver.getResources("classpath*:org/apache/**/*.class");

        for (Resource resource : resources) {
            System.out.println(resource);
        }
    }
}
