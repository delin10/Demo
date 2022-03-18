package nil.ed.test.algo.dp;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * 不同的二叉搜索树II.
 * @author lidelin.
 */
public class GenerateTrees {

    public static void main(String[] args) {
        GenerateTrees inst = new GenerateTrees();
//        inst.print(inst.generateTrees(1));
//        System.out.println("====");
//        inst.print(inst.generateTrees(2));
//        System.out.println("====");
        inst.print(inst.generateTrees(3));
        System.out.println("====");
    }

    public void print(List<TreeNode> node) {
        node.forEach(this::print);
    }

    public void print(TreeNode node) {
        List<Integer> vals = new LinkedList<>();
        LinkedList<TreeNode> first = new LinkedList<>();
        LinkedList<TreeNode> second = new LinkedList<>();
        boolean allNull = true;
        first.add(node);
        while (true) {
            if (first.isEmpty()) {
                if (allNull || second.isEmpty()) {
                    break;
                } else {
                    LinkedList<TreeNode> tmp = first;
                    first = second;
                    second = tmp;
                    allNull = true;
                }
            }
            TreeNode n = first.removeFirst();
            if (n != null) {
                if (n.left != null || n.right != null) {
                    second.addLast(n.left);
                    second.addLast(n.right);
                }
                allNull = allNull && n.left == null && n.right == null;
            }

            vals.add(n == null ? null : n.val);
        }
        System.out.println(vals);
    }

    public List<TreeNode> generateTrees(int n) {
        return generateTrees(1, n);
    }

    public List<TreeNode> generateTrees(int start, int end) {

        if (start == end) {
            return Collections.singletonList(new TreeNode(start));
        }

        List<TreeNode> ls = new LinkedList<>();
        for (int i = start; i <= end; i++) {
            List<TreeNode> left = null;
            List<TreeNode> right = null;

            if (i == start) {
                right = generateTrees(start + 1, end);
            } else if (i == end) {
                left = generateTrees(start, end - 1);
            } else {
                right = generateTrees(i + 1, end);
                left = generateTrees(start, i - 1);
            }

            if (left != null && right == null) {
                for (TreeNode n : left) {
                    ls.add(new TreeNode(i, n, null));
                }
            } else if (left == null && right != null) {
                for (TreeNode n : right) {
                    ls.add(new TreeNode(i, null, n));
                }
            } else if (left != null) {
                for (TreeNode l : left) {
                    for (TreeNode r : right) {
                        ls.add(new TreeNode(i, l, r));
                    }
                }
            }
        }
        return ls;
    }
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode() {}
        TreeNode(int val) { this.val = val; }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

}
