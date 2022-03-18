package nil.ed.test.java.javac;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import com.sun.tools.javac.file.JavacFileManager;
import com.sun.tools.javac.parser.Parser;
import com.sun.tools.javac.parser.ParserFactory;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.util.Context;
import org.apache.commons.io.IOUtils;

/**
 * @author lidelin.
 */
public class IfElseDemo {
    public static void main(String[] args) throws IOException {
        parse(IfElseDemo.class.getResourceAsStream("/IfElseDemo.java"));
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
}
