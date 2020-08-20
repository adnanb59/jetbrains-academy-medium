package gameoflife;

import java.io.IOException;

public class GameOfLife {
    private Universe u;

    public GameOfLife(int size) {
        u = Generator.create(size);
    }

    public void evolve(int generations) {
        for (int i = 0; i < generations; i++) {
            try {
                Generator.generate(u);
                System.out.println("Generation: #" + (i + 1));
                System.out.println(toString());
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Alive: ").append(u.alive).append("\n");
        sb.append(u.toString());
        return sb.toString();
    }
}
