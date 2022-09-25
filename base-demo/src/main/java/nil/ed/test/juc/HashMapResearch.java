package nil.ed.test.juc;

public class HashMapResearch {

    public static void main(String[] args) {
        int cap = 0b0010000000000001;
        int n = cap - 1;
        System.out.printf("n => %s%n", Integer.toBinaryString(n));
        n |= n >>> 1;
        System.out.printf("n >>> 1 => %s%n", Integer.toBinaryString(n));
        n |= n >>> 2;
        System.out.printf("n >>> 2 => %s%n", Integer.toBinaryString(n));
        n |= n >>> 4;
        System.out.printf("n >>> 4 => %s%n", Integer.toBinaryString(n));
        n |= n >>> 8;
        System.out.printf("n >>> 8 => %s%n", Integer.toBinaryString(n));
        n |= n >>> 16;
        System.out.printf("n >>> 16 => %s%n", Integer.toBinaryString(n));
        int ret = (n < 0) ? 1 : n + 1;
        System.out.println(ret);
    }


}
