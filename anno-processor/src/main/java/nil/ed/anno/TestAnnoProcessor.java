package nil.ed.anno;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Set;

/**
 * @author lidelin.
 */
@SupportedAnnotationTypes({"nil.ed.anno.MyCondition"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class TestAnnoProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        System.out.println(annotations);
        System.out.println(roundEnv);
        System.out.println("process");
        try(OutputStream outputStream = new FileOutputStream(new File(System.getProperty("user.home") + "/delin/xx.txt"))) {
            outputStream.write("okkkkk".getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

}
