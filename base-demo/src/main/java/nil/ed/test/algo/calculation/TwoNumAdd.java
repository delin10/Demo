package nil.ed.test.algo.calculation;

/**
 * 两数相加.
 * 简单.
 * <a>https://leetcode-cn.com/problems/add-two-numbers/</a>
 * @author lidelin.
 */
public class TwoNumAdd {

    public static void main(String[] args) {
        test(12345, 12345);
        test(123456, 12345);
        test(12345, 123456);
        test(1, 1);
        test(9999999, 9999);
    }

    public static void test(int val1, int val2) {
        ListNode node1 = construct(val1);
        ListNode node2 = construct(val2);
        ListNode ret = addTwoNumbers(node1, node2);
        System.out.println("actual: " + print(ret));
        System.out.println("expected: " + (val1 + val2));
    }

    public static ListNode construct(int num) {
        String str = String.valueOf(num);
        ListNode head = new ListNode();
        ListNode cursor = head;
        for (int i = str.length() - 1; i >= 0; --i) {
            cursor.val = str.charAt(i) - '0';
            if (i == 0) {
                break;
            }
            cursor.next = new ListNode();
            cursor = cursor.next;
        }
        return head;
    }

    public static String print(ListNode node) {
        StringBuilder builder = new StringBuilder(16);
        ListNode p = node;
        while (p != null) {
            builder.append(p.val);
            p = p.next;
        }
        return builder.reverse().toString();
    }


    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode p1 = l1;
        ListNode p2 = l2;

        ListNode result = new ListNode();
        ListNode cursor = result;
        int sum = 0;
        int mod = 0;
        while (p1 != null && p2 != null) {
            sum = p1.val + p2.val + mod;
            mod = sum / 10;
            sum = sum % 10;
            cursor.val = sum;
            p1 = p1.next;
            p2 = p2.next;

            if (p1 == null && p2 == null) {
                break;
            }
            cursor.next = new ListNode();
            cursor = cursor.next;
        }

        while (p1 != null) {
            sum = p1.val + mod;
            mod = sum / 10;
            sum = sum % 10;
            cursor.val = sum;
            p1 = p1.next;
            if (p1 == null) {
                break;
            }
            cursor.next = new ListNode();
            cursor = cursor.next;
        }

        while (p2 != null) {
            sum = p2.val + mod;
            mod = sum / 10;
            sum = sum % 10;
            cursor.val = sum;
            p2 = p2.next;
            if (p2 == null) {
                break;
            }
            cursor.next = new ListNode();
            cursor = cursor.next;
        }
        if (mod > 0) {
            cursor.next = new ListNode();
            cursor.next.val = mod;
        }
        return result;
    }

     public static class ListNode {
         int val;
         ListNode next;
         ListNode() {}
         ListNode(int val) { this.val = val; }
         ListNode(int val, ListNode next) { this.val = val; this.next = next; }
     }
}
