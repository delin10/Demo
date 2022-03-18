package nil.ed.test.tool;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.ForkJoinPool;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.SimpleMetadataReaderFactory;

/**
 * 1. 需要配置JAVA_HOME.
 * 2. 需要安装graphviz: brew install graphviz.
 * 引入依赖：
 *         <dependency>
 *             <groupId>org.ow2.asm</groupId>
 *             <artifactId>asm</artifactId>
 *             <version>9.1</version>
 *         </dependency>
 *         <dependency>
 *            <groupId>org.springframework</groupId>
 *            <artifactId>spring-core</artifactId>
 *            <version>5.2.0.RELEASE</version>
 *         </dependency>
 *         <dependency>
 *             <groupId>commons-io</groupId>
 *             <artifactId>commons-io</artifactId>
 *             <version>2.6</version>
 *         </dependency>
 *
 * @author lidelin.
 */
public class InheritedPrinterV2 {
    static final PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        // 填写需要绘制依赖关系的类或者接口
        Class<?> clazz = AbstractExecutorService.class;
        boolean subClass = false;
        int level = 0;
        String projectDir = new File("").getAbsolutePath();
        new File(projectDir + "/data").mkdirs();
        outPretty(level, clazz.getSimpleName());

        // 扫描类文件
        JarFile jarFile = new JarFile(System.getenv("JAVA_HOME") + "/jre/lib/rt.jar");
        Enumeration<JarEntry> jarEntryEnumeration = jarFile.entries();
        List<String> jarClasses = new LinkedList<>();
        while (jarEntryEnumeration.hasMoreElements()) {
            JarEntry entry = jarEntryEnumeration.nextElement();
            String name = entry.getName();
            if (name.endsWith(".class")) {
                jarClasses.add(name);
            }
        }

        // 合并spring、sun class
        String[] resources = Stream.concat(jarClasses.stream(),
                // 添加依赖对应的类
                scan("classpath*:com/sun/**/*.class", "classpath*:org/springframework/**/*.class"))
                .filter(Objects::nonNull)
                .toArray(String[]::new);

        // 生成继承树
        ClassContainer container = new ClassContainer().setClazz(clazz.getCanonicalName());
        Map<String, ClassContainer> map = new HashMap<>();
        map.put(clazz.getCanonicalName(), container);
        for (String e : resources) {
            try {
                buildTree(resolveClassName(e), map);
            } catch (Throwable ne) {
            }
        }

        // 控制台打印继承树
        traverse(container, 0);
        // 生成graphviz文件
        StringBuilder builder = new StringBuilder("digraph {\n");
        if (subClass) {
            parentGraphviz(container, 0, builder);
        } else {
            graphviz(container, 0, builder);
        }
        builder.append("}\n");
        try (Writer writer = new OutputStreamWriter(Files.newOutputStream(Paths.get(String.format(projectDir + "/data/graph-%s.txt", clazz.getSimpleName())), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING))) {
            IOUtils.write(builder.toString(), writer);
        }

        // 使用graphviz进行绘制
        Process process = Runtime.getRuntime().exec(String.format("dot -Tpng %s/data/graph-%s.txt -o %s/data/tree-%s.png", projectDir, clazz.getSimpleName(), projectDir, clazz.getSimpleName()));
        InputStream inputStream = process.getErrorStream();
        process.waitFor();
        List<String> errors = org.apache.commons.io.IOUtils.readLines(inputStream);
        System.err.println(String.join("\n", errors));
    }

    static Stream<String> scan(String...pattern) throws IOException {
        if (pattern == null || pattern.length == 0) {
            return Stream.empty();
        }
        Stream<String> old = Stream.empty();
        for (String p : pattern) {
            Resource[] resources2 = resolver.getResources(p);
            old = Stream.concat(old, Stream.of(resources2).map(e -> {
                try {
                    return e.getURL().getFile();
                } catch (IOException ioException) {
                    return null;
                }
            }));
        }
        return old;
    }

    static final Set<String> printed = new HashSet<>();
    private static void traverse(ClassContainer root, int level) {
        if (root == null) {
            return;
        }
        outPretty(level, root.getClazz());
        if (!printed.add(root.getClazz())) {
            outPretty(level + 1, "【............】");
            return;
        }
        if (CollectionUtils.isEmpty(root.getChildren())) {
            return;
        }
        for (ClassContainer container : root.getChildren()) {
            traverse(container, level + 1);
        }
    }

    static String escapeDot(String name) {
        return name.replaceAll("\\.", "_");
    }

    static final Set<String> printed2 = new HashSet<>();
    static final Set<Pair<String, String>> edge = new HashSet<>();
    private static void graphviz(ClassContainer root, int level, StringBuilder builder) {
        if (root == null) {
            return;
        }
        if (!printed2.add(root.getClazz())) {
            return;
        }
        if (CollectionUtils.isEmpty(root.getChildren())) {
            return;
        }
        String left = ClassUtils.getShortClassName(root.getClazz());
        for (ClassContainer container : root.getChildren()) {
            String right = ClassUtils.getShortClassName(container.getClazz());
            if (!edge.add(Pair.of(left, right))) {
                continue;
            }
            if (left.contains(".") || right.contains(".")) {
                continue;
            }
            String escapedLeft = escapeDot(left);
            String escapedRight = escapeDot(right);
            builder.append(escapedLeft).append("->").append(escapedRight).append("\n");
            builder.append(escapedLeft).append("[shape=box,splines=ortho,color=blue];\n");
            builder.append(escapedRight).append("[shape=box,splines=ortho,color=blue];\n");
            graphviz(container, level + 1, builder);
            parentGraphviz(container, level, builder);
        }
    }

    static final Set<String> parentPrinted2 = new HashSet<>();
    private static void parentGraphviz(ClassContainer child, int level, StringBuilder builder) {
        if (child == null) {
            return;
        }
        if (!parentPrinted2.add(child.getClazz())) {
            return;
        }
        if (CollectionUtils.isEmpty(child.getParents())) {
            return;
        }
        String right = ClassUtils.getShortClassName(child.getClazz());
        for (ClassContainer container : child.getParents()) {
            if (!printed2.add(container.getClazz())) {
                continue;
            }
            String left = ClassUtils.getShortClassName(container.getClazz());
            if (!edge.add(Pair.of(left, right))) {
                continue;
            }
            if (left.contains(".") || right.contains(".")) {
                continue;
            }
            String escapedLeft = escapeDot(left);
            String escapedRight = escapeDot(right);
            builder.append(escapedLeft).append("->").append(escapedRight).append("\n");
            builder.append(escapedLeft).append("[shape=box,splines=ortho];\n");
            builder.append(escapedRight).append("[shape=box,splines=ortho];\n");
            graphviz(container, level + 1, builder);
        }
    }


    public static void outPretty(int level, Object out) {
        String indent = IntStream.range(0, level).mapToObj(i -> "  ").collect(Collectors.joining());
        System.out.println(indent + level + ":"+ out);
    }

    static final SimpleMetadataReaderFactory factory = new SimpleMetadataReaderFactory();
    public static void buildTree(String target, Map<String, ClassContainer> map) {
        try {
            if (target == null) {
                return;
            }
            MetadataReader reader = factory.getMetadataReader(target);
            List<String> pending = new LinkedList<>();
            String superClass = reader.getClassMetadata().getSuperClassName();
            if (superClass != null && !superClass.endsWith(".Object")) {
                pending.add(superClass);
            }
            Collections.addAll(pending, reader.getClassMetadata().getInterfaceNames());
            if (CollectionUtils.isEmpty(pending)) {
                return;
            }
            for (String nextTarget : pending) {
                ClassContainer superContainer = map.computeIfAbsent(nextTarget, k -> new ClassContainer().setClazz(k));
                ClassContainer targetContainer = map.computeIfAbsent(target, k -> new ClassContainer().setClazz(k));
                targetContainer.getParents().add(superContainer);
                superContainer.getChildren().add(targetContainer);
                buildTree(nextTarget, map);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public static String resolveClassName(String path) {
        try {
            if (!path.endsWith(".class")) {
                throw new IllegalArgumentException("Illegal class file");
            }
            int i = path.indexOf("!/");
            if (i > 0) {
                return org.springframework.util.ClassUtils.convertResourcePathToClassName(path.substring(i + 2, path.length() - ".class".length()));
            }
            return org.springframework.util.ClassUtils.convertResourcePathToClassName(path.substring(i + 1, path.length() - ".class".length()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Getter
    @Setter
    @Accessors(chain = true)
    public static class ClassContainer {
        private String clazz;
        private final Set<ClassContainer> parents = new HashSet<>();
        private final Set<ClassContainer> children = new HashSet<>();

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            ClassContainer that = (ClassContainer) o;
            return Objects.equals(clazz, that.clazz);
        }

        @Override
        public int hashCode() {
            return Objects.hash(clazz);
        }
    }
}
