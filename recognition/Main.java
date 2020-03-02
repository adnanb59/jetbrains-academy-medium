package recognition;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Network n = new Network();
        int lines = 0;
        System.out.println("Input grid:");
        while (in.hasNext() && lines < 3) {
            String curr = in.next();
            for (int i = 0; i < curr.length() && i < 3; i++) {
                switch (curr.charAt(i)) {
                    case 'X':
                        n.addNodeValue(1);
                        break;
                    case '_':
                    default:
                        n.addNodeValue(0);
                        break;
                }
            }
            ++lines;
        }
        System.out.printf("This number is %d", n.getResult());
    }
}
