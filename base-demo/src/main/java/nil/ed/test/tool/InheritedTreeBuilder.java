package nil.ed.test.tool;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public interface InheritedTreeBuilder {

    /**
     * @param target   需要匹配的类型.
     * @param rooClazz 根类型.
     * @param map      保存所有的ClassContainer。
     */
    void buildTree(String target, ClassContainer rooClazz, Map<String, ClassContainer> map);

    @Getter
    @Setter
    @Accessors(chain = true)
    class ClassContainer {
        private String clazz;
        private final Set<ClassContainer> parents = new HashSet<>();
        private final Set<ClassContainer> children = new HashSet<>();

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ClassContainer that = (ClassContainer) o;
            return Objects.equals(clazz, that.clazz);
        }

        @Override
        public int hashCode() {
            return Objects.hash(clazz);
        }
    }

}
