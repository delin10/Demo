package nil.ed.test.java;

/**
 * @author lidelin.
 */
public class SwitchStringDemo {

    public static void main(String[] args) {
        
        switchString("A");
    }

    static void switchString(String val) {
        switch (val) {
            case "A":
                System.out.println("case A");
                break;
            case "B":
                System.out.println("case B");
                break;
            case "Aa":
                System.out.println("case Aa");
            case "BB":
                System.out.println("case BB");
                break;
            case "CC":
            case "DD":
                System.out.println("ok");
            default:
                System.out.println("case C");
        }
    }

}
