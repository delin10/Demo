package nil.ed.test.algo.string;

import java.util.LinkedList;
import java.util.List;

import com.alibaba.fastjson.JSON;

/**
 * 括号生成.
 * https://leetcode-cn.com/problems/generate-parentheses/
 * @author lidelin.
 */
public class GenerateParenthesisV2 {


    public static void main(String[] args) {
        System.out.println(new GenerateParenthesisV2().generateParenthesis(1));
        System.out.println(new GenerateParenthesisV2().generateParenthesis(2));
        System.out.println(new GenerateParenthesisV2().generateParenthesis(3));
        List<String> ret = new GenerateParenthesisV2().generateParenthesis(5);
        System.out.println(ret);
        JSON.parseArray("[\"((((()))))\",\"(((()())))\",\"(((())()))\",\"(((()))())\",\"(((())))()\",\"((()(())))\",\"((()()()))\",\"((()())())\",\"((()()))()\",\"((())(()))\",\"((())()())\",\"((())())()\",\"((()))(())\",\"((()))()()\",\"(()((())))\",\"(()(()()))\",\"(()(())())\",\"(()(()))()\",\"(()()(()))\",\"(()()()())\",\"(()()())()\",\"(()())(())\",\"(()())()()\",\"(())((()))\",\"(())(()())\",\"(())(())()\",\"(())()(())\",\"(())()()()\",\"()(((())))\",\"()((()()))\",\"()((())())\",\"()((()))()\",\"()(()(()))\",\"()(()()())\",\"()(()())()\",\"()(())(())\",\"()(())()()\",\"()()((()))\",\"()()(()())\",\"()()(())()\",\"()()()(())\",\"()()()()()\"]", String.class)
                .forEach(e -> {
                    if (!ret.contains(e)) {
                        System.out.println("=" + e);
                    }
                });
    }

    public List<String> generateParenthesis(int n) {
        List<String> ret = new LinkedList<>();
        generateParenthesis("", n, n, ret);
        return ret;
    }

    public void generateParenthesis(String cur, int left, int right, List<String> res) {
        if (right == 0) {
            res.add(cur);
            return;
        }

        if (left == right) {
            generateParenthesis(cur + '(', left - 1, right, res);
        } else {
            generateParenthesis(cur + ')', left, right - 1, res);
            if (left > 0) {
                generateParenthesis(cur + '(', left - 1, right, res);
            }
        }
    }

}
