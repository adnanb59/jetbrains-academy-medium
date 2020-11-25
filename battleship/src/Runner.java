import battleship.Board;
import battleship.Ship;

import java.util.Scanner;

public class Runner {
    /**
     * Create a Battleship game board, prompting user for locations to place the various ships
     *
     * @param in - Scanner for input
     * @return Battleship game board
     */
    public static Board create_player(Scanner in) {
        Board b = new Board();
        System.out.println(b.display(true));

        // Go through Ship enum and add ships to the game board
        for (Ship s : Ship.values()) {
            System.out.printf("Enter the coordinates of the %s (%d cells):\n", s.toString(), s.getSize());
            boolean success = false;
            // Attempt to enter coordinates until successful (a free section is entered)
            while (!success) {
                // A valid regex consists of a pair of coordinates where one of the letters are the same in both
                // or the numbers are
                String coordinates = in.nextLine().trim();
                if (!coordinates.matches("([A-J])([1-9]|10)\\s+(\\1([1-9]|10)|[A-J]\\2)"))
                    System.out.println("Error! Wrong ship location! Try again:");
                else {
                    String[] pair = coordinates.split("\\s+");
                    int result = b.place(pair[0], pair[1], s);
                    if (result == -1) System.out.printf("Error! Wrong length of the %s! Try again:\n", b.toString());
                    else if (result == 1) System.out.println("Error! You placed it too close to another one. Try again:");
                    else success = true;
                }
            }
            System.out.println(b.display(true));
        }
        return b;
    }

    /**
     * Play game of Battleship
     *
     * @param A - first player (Battleship board)
     * @param B - second player (Battleship board)
     * @param in - Scanner for input
     */
    public static void play(Board A, Board B, Scanner in) {
        boolean firstTurn = true, success;

        // Run game until a winner is found
        while (!(A.isCompleted() || B.isCompleted())) {
            // Display current game progress from perspective of current player
            System.out.println((firstTurn ? B : A).display(false));
            System.out.println("---------------------");
            System.out.println((firstTurn ? A : B).display(true));
            System.out.printf("Player %d, it's your turn:\n", firstTurn ? 1 : 2);

            success = false;
            while (!success) {
                String location = in.nextLine().trim();
                if (!location.matches("[A-J]([1-9]|10)"))
                    System.out.println("Error! You entered the wrong coordinates! Try again:");
                else {
                    int hit = (firstTurn ? B : A).target(location);
                    if (hit == -1) System.out.println("Error! You already aimed there! Try again:");
                    else {
                        success = true;
                        if (hit == 0) System.out.println("You hit a ship!");
                        else if (hit == 1) System.out.println("You missed!");
                        else if (hit == 10) System.out.println("You sank a ship! Specify a new target:");
                        else if (hit == 100) System.out.println("You sank the last ship. You won. Congratulations!");
                    }
                }
            }

            // if winner wasn't found with last move, then continue
            if (!(firstTurn ? B : A).isCompleted()) firstTurn = !firstTurn;
        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Player 1, place your ships to the game field");
        Board b1 = create_player(in);

        System.out.println("Player 2, place your ships to the game field");
        Board b2 = create_player(in);

        System.out.println("The game starts!");
        play(b1, b2, in);
    }
}