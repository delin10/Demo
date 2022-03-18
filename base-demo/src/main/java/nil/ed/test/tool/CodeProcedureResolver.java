package nil.ed.test.tool;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

import com.sun.source.tree.ClassTree;
import com.sun.source.tree.MethodTree;
import com.sun.source.tree.Tree;
import com.sun.source.util.TreeScanner;
import com.sun.tools.javac.code.Type;
import com.sun.tools.javac.file.JavacFileManager;
import com.sun.tools.javac.parser.Parser;
import com.sun.tools.javac.parser.ParserFactory;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.util.Context;
import org.apache.commons.io.IOUtils;

/**
 * @author lidelin.
 */
public class CodeProcedureResolver {
    public static void main(String[] args) throws IOException {
        String dir = "/Users/lidelin10/lidelin/private/Demo/base-demo/src/main/java/nil/ed/test/tool";
        List<String> lines = IOUtils.readLines(Files.newInputStream(Paths.get(dir, "InheritedPrinterV2.java")), StandardCharsets.UTF_8);
        String src = String.join("\n", lines);
        Context context = new Context();
        JavacFileManager.preRegister(context);
        ParserFactory factory = ParserFactory.instance(context);
        Parser parser = factory.newParser(src, true, false, true);
        JCTree.JCCompilationUnit unit = parser.parseCompilationUnit();
        List<String> ls = new LinkedList<>();
        JCTree.JCClassDecl classDecl = (JCTree.JCClassDecl) unit.defs.stream().filter(tree -> tree instanceof JCTree.JCClassDecl).findAny().get();
        JCTree.JCMethodDecl methodDecl = (JCTree.JCMethodDecl) classDecl.defs.stream().filter(def -> def instanceof JCTree.JCMethodDecl).findAny().get();
        System.out.println(methodDecl.getName());
//        new MethodScanner().visitMethod(, ls);
//        System.out.println(ls);
    }

    static class Node {
        private String node;
        private String condition;
        private List<Node> children;
    }
    static class MethodScanner extends
            TreeScanner<List<String>, List<String>> {
        @Override
        public List<String> visitMethod(MethodTree node, List<String> p) {
            p.add(node.getName().toString());
            return p;
        }

        @Override
        public List<String> visitClass(ClassTree classTree, List<String> strings) {
            classTree.getMembers().forEach(tree -> {
                System.out.println(tree);
                // 变量是VARIABLE
                // METHOD
                if (tree.getKind().equals(Tree.Kind.VARIABLE)) {
                    JCTree.JCVariableDecl node = (JCTree.JCVariableDecl) tree;
                }
            });
            return strings;
        }

    }
}
