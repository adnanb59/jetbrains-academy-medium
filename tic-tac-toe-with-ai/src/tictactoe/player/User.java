package tictactoe.player;

import java.util.InputMismatchException;
import java.util.Scanner;

public class User extends Player {
    private Scanner in;

    public User() {
        super();
        in = new Scanner(System.in);
    }

    @Override
    public void move() {
        boolean areCoordinatesValid = false;
        while (!areCoordinatesValid) {
            /* BOARD COORDINATES:
             * (1,3) (2,3) (3,3)
             * (1,2) (2,2) (2,3)
             * (1,1) (2,1) (3,1) */
            System.out.print("Enter the coordinates: ");
            // If coordinates are valid (& unoccupied), then the move will be made
            // Otherwise, provide an error message
            try {
                int promptX = Integer.parseInt(in.next());
                int promptY = Integer.parseInt(in.next());
                if (promptX >= 1 && promptX <= 3 && promptY >= 1 && promptY <= 3) {
                    areCoordinatesValid = board.isAvailable(promptX, promptY);
                    if (!areCoordinatesValid) System.out.println("This cell is occupied! Choose another one!");
                    else board.makeMove(promptX, promptY);
                } else System.out.println("Coordinates should be from 1 to 3!");
            } catch (InputMismatchException e) {
                System.out.println("You should enter numbers!");
            } finally {
                in.nextLine();
                System.out.println();
            }
        }
    }
}