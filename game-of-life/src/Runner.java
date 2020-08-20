import gameoflife.*;

import java.util.Scanner;

public class Runner {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        boolean isValidEntry = false;
        while (!isValidEntry) {
            try {
                int n, g;
                long s;
                n = Integer.parseInt(in.next());
                if (n > 0) isValidEntry = true;
                if (isValidEntry) {
                    GameOfLife gol = new GameOfLife(n);
                    gol.evolve(10);
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid entry, try again.");
            }
        }
    }
}