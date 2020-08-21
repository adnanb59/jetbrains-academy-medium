package gameoflife;

import java.util.Random;

public class Generator {
    public static Universe create(int N) {
        Random rand = new Random();
        Universe u = new Universe(N);

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                u.setCell(i, j, rand.nextBoolean() ? 1 : 0);
            }
        }
        return u;
    }

    public static void generate(Universe u) {
        int[][] state = u.getState();
        int len = u.getSize();
        for (int i = 0; i < len; i++) {
            int N = i-1 == -1 ? len-1 : i-1, S = i+1 == len ? 0 : i+1;
            for (int j = 0; j < len; j++) {
                int E = j+1 == len ? 0 : j+1, W = j-1 == -1 ? len-1 : j-1;
                int sum = state[N][W] + state[N][j] + state[N][E] + state[i][W] + state[i][E] + state[S][W] + state[S][j] + state[S][E];
                int value = sum == 3 || (state[i][j] == 1 && sum == 2) ? 1 : 0;
                u.setCell(i, j, value);
            }
        }
    }
}
