package nil.ed.test.asm;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.Arrays;

public class AsmBaseDemo {

    public static void main(String[] args) throws IOException {
        visitClassDemo();
    }

    public static void visitClassDemo() throws IOException {
        /*
        Exception in thread "main" java.lang.IllegalArgumentException
            at org.objectweb.asm.ClassReader.<init>(Unknown Source)
            at org.objectweb.asm.ClassReader.<init>(Unknown Source)
            at org.objectweb.asm.ClassReader.<init>(Unknown Source)
        不支持的JDK版本.
         */
        ClassReader cr = new ClassReader(AsmBaseDemo.class.getResourceAsStream("/nil/ed/test/asm/AsmTestClass0$1A.class"));
        cr.accept(new ClassVisitor(Opcodes.ASM9) {
            @Override
            public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
                System.out.println("=> visit");
                System.out.println("version: " + version);
                System.out.println("access: " + Modifier.toString(access));
                System.out.println("name: " + name);
                System.out.println("signature: " + signature);
                System.out.println("superName: " + superName);
                System.out.println("interfaces: " + Arrays.toString(interfaces));
            }

            @Override
            public void visitSource(String source, String debug) {
                System.out.println("=> visitSource");
                System.out.println("source: " + source);
                System.out.println("debug: " + debug);
            }

            @Override
            public void visitOuterClass(String owner, String name, String desc) {
                System.out.println("=> visitOuterClass");
                System.out.println("owner: " + owner);
                System.out.println("name: " + name);
                System.out.println("desc: " + desc);
            }

            @Override
            public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
                System.out.println("=> visitAnnotation");
                System.out.println("desc: " + desc);
                System.out.println("visible: " + visible);
                return new AnnotationVisitor(Opcodes.ASM9) {
                    @Override
                    public void visit(String name, Object value) {
                        super.visit(name, value);
                    }

                    @Override
                    public void visitEnum(String name, String descriptor, String value) {
                        super.visitEnum(name, descriptor, value);
                    }

                    @Override
                    public AnnotationVisitor visitAnnotation(String name, String descriptor) {
                        return super.visitAnnotation(name, descriptor);
                    }

                    @Override
                    public AnnotationVisitor visitArray(String name) {
                        return super.visitArray(name);
                    }

                    @Override
                    public void visitEnd() {
                        super.visitEnd();
                    }
                };
            }

            @Override
            public void visitAttribute(Attribute attr) {
                System.out.println("=> visitAttribute");
                System.out.println(attr);
            }

            @Override
            public void visitInnerClass(String name, String outerName, String innerName, int access) {
                super.visitInnerClass(name, outerName, innerName, access);
            }

            @Override
            public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
                return super.visitField(access, name, desc, signature, value);
            }

            @Override
            public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
                System.out.println("=> visitMethod");
                System.out.println("access: " + Modifier.toString(access));
                System.out.println("name: " + name);
                System.out.println("signature: " + signature);
                System.out.println("superName: " + desc);
                System.out.println("interfaces: " + Arrays.toString(exceptions));
                return null;
            }

            @Override
            public void visitEnd() {
                super.visitEnd();
            }
        }, ClassReader.EXPAND_FRAMES);
    }

}
