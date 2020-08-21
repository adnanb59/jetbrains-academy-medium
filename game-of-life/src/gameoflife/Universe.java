package gameoflife;

import java.util.Arrays;

public class Universe {
    private int[][] state;
    private final int size;
    private int alive;

    public Universe(int N) {
        state = new int[N][N];
        for (int[] s : state) {
            Arrays.fill(s, 0);
        }
        size = N;
        alive = 0;
    }

    public void setCell(int r, int c, int v) {
        if (v > state[r][c]) alive++;
        else if (v < state[r][c]) alive--;
        state[r][c] = v;
    }

    public int[][] getState() {
        int[][] result = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) result[i][j] = state[i][j];
        }
        return result;
    }

    public int getSize() {
        return size;
    }

    public int getAlive() {
        return alive;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[0].length; j++) {
                sb.append(state[i][j] == 1 ? "O" : " ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
