package recognition;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Network n = new Network();
        int lines = 0, ROWS = 5, COLS = 3;

        System.out.println("Input grid:");
        while (in.hasNext() && lines < ROWS) {
            String curr = in.next();
            for (int i = 0; i < curr.length() && i < COLS; i++) {
                switch (curr.charAt(i)) {
                    case 'X':
                        n.addNodeValue(lines*3 + i);
                        break;
                    case '_':
                        break;
                    default:
                        System.err.println("Incorrect input entered");
                        --lines;
                        i = COLS;
                        for (int j = lines*3; j < lines*3+i; j++) {
                            n.removeNode(j);
                        }
                        break;
                }
            }
            ++lines;
        }
        System.out.printf("This number is %d", n.getResult());
    }
}