package nil.ed.test.algo.string;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.JSON;

/**
 * 括号生成.
 * https://leetcode-cn.com/problems/generate-parentheses/
 * @author lidelin.
 */
public class GenerateParenthesis {


    public static void main(String[] args) {
        System.out.println(new GenerateParenthesis().generateParenthesis(1));
        System.out.println(new GenerateParenthesis().generateParenthesis(2));
        System.out.println(new GenerateParenthesis().generateParenthesis(3));
        List<String> ret = new GenerateParenthesis().generateParenthesis(5);
        JSON.parseArray("[\"((((()))))\",\"(((()())))\",\"(((())()))\",\"(((()))())\",\"(((())))()\",\"((()(())))\",\"((()()()))\",\"((()())())\",\"((()()))()\",\"((())(()))\",\"((())()())\",\"((())())()\",\"((()))(())\",\"((()))()()\",\"(()((())))\",\"(()(()()))\",\"(()(())())\",\"(()(()))()\",\"(()()(()))\",\"(()()()())\",\"(()()())()\",\"(()())(())\",\"(()())()()\",\"(())((()))\",\"(())(()())\",\"(())(())()\",\"(())()(())\",\"(())()()()\",\"()(((())))\",\"()((()()))\",\"()((())())\",\"()((()))()\",\"()(()(()))\",\"()(()()())\",\"()(()())()\",\"()(())(())\",\"()(())()()\",\"()()((()))\",\"()()(()())\",\"()()(())()\",\"()()()(())\",\"()()()()()\"]", String.class)
                .forEach(e -> {
                    if (!ret.contains(e)) {
                        System.out.println("=" + e);
                    }
                });
    }

    public List<String> generateParenthesis(int n) {
        Map<Integer, Set<String>> map = new HashMap<>(n, 1);
        for (int i = 1; i <= n; i++) {
            generateParenthesis(i, map);
        }
        return new ArrayList<>(map.get(n));
    }

    public void generateParenthesis(int n, Map<Integer, Set<String>> map) {
        if (n == 1) {
            map.put(1, Collections.singleton("()"));
            return;
        }

        Set<String> set = new HashSet<>(map.get(n - 1).size() * 2 + 2, 1);
        Set<String> lastSet = map.get(n - 1);
        lastSet.forEach(e -> {
            set.add("(" + e + ")");
            set.add("()" + e);
            set.add(e + "()");
        });
        map.put(n, set);
        for (int i = 2; i <= n / 2; i++) {
            lastSet = map.get(n - i);
            Set<String> lastAnotherSet = map.get(i);
            lastSet.forEach(e1 -> {
                lastAnotherSet.forEach(e2 -> {
                    set.add(e1 + e2);
                    set.add(e2 + e1);
                });
            });
        }

    }

}
