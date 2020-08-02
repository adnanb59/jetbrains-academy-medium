import tictactoe.board.*;
import tictactoe.player.*;

import java.util.Scanner;

public class Runner {
    public static void play(Board board, Player p1, Player p2) {
        while (!board.isCompleted()) {
            System.out.println(board.printBoard());
            if (board.getCurrentPlayer() == Piece.X) p1.move();
            else p2.move();
        }

        System.out.println(board.printBoard());
        System.out.println(board.getState());
        board.reset();
    }


    public static void main(String[] args) {
        // -- INITIALIZE VARIABLES --
        Board board = new Board();
        Scanner in = new Scanner(System.in);
        boolean isRunningProgram = true, invalidCommandProvided;
        String[] commands;

        while (isRunningProgram) {
            invalidCommandProvided = true;
            while (invalidCommandProvided) {
                System.out.print("Input command: ");
                commands = in.nextLine().trim().split("\\s+");
                if (commands.length == 1 && commands[0].equals("exit")) {
                    invalidCommandProvided = false;
                    isRunningProgram = false;
                } else if (commands.length != 3 || !commands[0].equals("start") ||
                        !commands[1].matches("(user|easy|medium|hard)") ||
                        !commands[2].matches("(user|easy|medium|hard)")) {
                    System.out.println("Bad parameters!");
                } else {
                    play(board, PlayerFactory.makePlayer(commands[1]), PlayerFactory.makePlayer(commands[2]));
                    invalidCommandProvided = false;
                }
            }
        }
    }
}