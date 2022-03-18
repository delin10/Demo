package nil.ed.anno;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.StandardOpenOption;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;

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

        try(OutputStream outputStream =
                    new FileOutputStream(new File(TestAnnoProcessor.class.getResource("/").getFile() + "/xx.txt"))) {
            outputStream.write("okkkkk".getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
//        return true;
    }

}
