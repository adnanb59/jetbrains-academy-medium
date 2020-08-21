import gameoflife.*;

public class Runner {
    public static void main(String[] args) {
        GameOfLife gol = new GameOfLife();
        int N = 100, G = 25;
        Universe u = Generator.create(N);
        for (int i = 0; i < G; i++) {
            try {
                Generator.generate(u);
                gol.setText("GenerationLabel", "Generation: #" + (i+1));
                gol.setText("AliveLabel", "Alive: " + u.getAlive());
                gol.updateBody(u.getState());
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}