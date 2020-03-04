package recognition;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class Network {
    private static int[][] matrix;
    private Integer solution = null;
    private int[] values;
    private Set<Integer> output;

    public Network() {
        matrix = new int[][]{
                { 1,  1,  1,  1, -1,  1,  1, -1,  1,  1, -1,  1,  1,  1,  1},
                {-1,  1, -1, -1,  1, -1, -1,  1, -1, -1,  1, -1, -1,  1, -1},
                { 1,  1,  1, -1, -1,  1,  1,  1,  1,  1, -1, -1,  1,  1,  1},
                { 1,  1,  1, -1, -1,  1,  1,  1,  1, -1, -1,  1,  1,  1,  1},
                { 1, -1,  1,  1, -1,  1,  1,  1,  1, -1, -1,  1, -1, -1,  1},
                { 1,  1,  1,  1, -1, -1,  1,  1,  1, -1, -1,  1,  1,  1,  1},
                { 1,  1,  1,  1, -1, -1,  1,  1,  1,  1, -1,  1,  1,  1,  1},
                { 1,  1,  1, -1, -1,  1, -1, -1,  1, -1, -1,  1, -1, -1,  1},
                { 1,  1,  1,  1, -1,  1,  1,  1,  1,  1, -1,  1,  1,  1,  1},
                { 1,  1,  1,  1, -1,  1,  1,  1,  1, -1, -1,  1,  1,  1,  1}
        };
        values = new int[]{-1, 6, 1, 0, 2, 0, -1, 3, -2, -1};
        output = new LinkedHashSet<>();
    }

    public void addNodeValue(int pos) {
        output.add(pos);
    }

    public boolean removeNode(int pos) {
        return output.remove(pos);
    }

    public Integer getResult() {
        if (solution == null) {
            // Values already have the biases added to them, now add node weights
            for (Integer o : output) {
                for (int j = 0; j < values.length; j++) {
                    values[j] += matrix[j][o];
                }
            }
            int curr = 0, max = values[0];
            for (int i = 1; i < values.length; i++) {
                if (values[i] > max) {
                    curr = i;
                    max = values[i];
                }
            }
            solution = curr;
        }
        return solution;
    }
}