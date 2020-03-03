package recognition;

import java.util.Arrays;

public class Network {
    private static int[] weights = {2, 1, 2, 4, -4, 4, 2, -1, 2, -5};
    private static int b = -5;
    private Integer solution = null;
    private int[] values;
    private int index;

    public Network() {
        values = new int[9];
        index = 0;
    }

    public void addNodeValue(int value) {
        if (index < 9) {
            values[index] = value;
            ++index;
        }
    }

    public Integer getResult() {
        if (index < 9) return null;
        if (solution == null) {
            solution = b;
            //System.out.println(solution);
            for (int v : values) {
                //System.out.print("-->" + v + " " + weights[9-index]);
                solution += v * weights[9 - index--];
                //System.out.println(solution);
            }
            //System.out.println(solution);
            solution = solution >= 0 ? 0 : 1;
        }
        return solution;
    }
}
