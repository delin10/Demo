package nil.ed.test.java.javac;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.StandardLocation;

import com.sun.tools.javac.comp.LambdaToMethod;
import com.sun.tools.javac.file.JavacFileManager;
import com.sun.tools.javac.main.JavaCompiler;
import com.sun.tools.javac.parser.Parser;
import com.sun.tools.javac.parser.ParserFactory;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.util.Context;
import org.apache.commons.io.IOUtils;

/**
 * @author lidelin.
 */
public class LambdaDemo {
    public static void main(String[] args) throws Throwable {
        parse(LambdaDemo.class.getResourceAsStream("/LambdaDemo.java"));
        compile(LambdaDemo.class.getResource("/LambdaDemo.java").getFile());
    }

    public static void parse(InputStream inputStream) throws IOException {
        List<String> lines = IOUtils.readLines(inputStream, StandardCharsets.UTF_8);
        String src = String.join("\n", lines);
        Context context = new Context();
        JavacFileManager.preRegister(context);
        ParserFactory factory = ParserFactory.instance(context);
        Parser parser = factory.newParser(src, true, false, true);
        JCTree.JCCompilationUnit unit = parser.parseCompilationUnit();

        System.out.println(unit);
    }

    public static void compile(String...files) throws Throwable {
        Context context = new Context();
        JavacFileManager.preRegister(context);
        JavacFileManager javacFileManager = (JavacFileManager) context.get(JavaFileManager.class);
        Iterable<? extends JavaFileObject> iterable = javacFileManager.getJavaFileObjects(files);
        javacFileManager.setLocation(StandardLocation.CLASS_OUTPUT, Collections.singletonList(new File("data/classes")));
        JavaCompiler.instance(context).compile(com.sun.tools.javac.util.List.from(iterable));
    }
}
