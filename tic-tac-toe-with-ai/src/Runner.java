import tictactoe.board.*;
import tictactoe.player.*;

import java.util.Scanner;

public class Runner {
    /**
    * Play current Tic-Tac-Toe game till resolution is found (win, lose, draw).
    * 
    * @param board - Tic-Tac-Toe game board
    * @param p1 - First player
    * @param p2 - Second player
    */
    public static void play(Board board, Player p1, Player p2) {
        p1.setBoard(board);
        p2.setBoard(board);

        // Continue game till resolution
        while (!board.isCompleted()) {
            System.out.println(board.printBoard());
            if (board.getCurrentPlayer() == Piece.X) p1.move();
            else p2.move();
        }
        p1.removeBoard();
        p2.removeBoard();

        System.out.println(board.printBoard());
        System.out.println(board.getState());
        System.out.println();
        board.reset();
    }

    /** MAIN METHOD */
    public static void main(String[] args) {
        // -- INITIALIZE VARIABLES --
        Board board = new Board();
        Scanner in = new Scanner(System.in);
        boolean isRunningProgram = true, invalidCommandProvided;
        String[] commands;

        // Allow user to enter prompt for game they want to play
        // `start [easy|medium|hard|user] [easy|medium|hard|user]` OR exit
        while (isRunningProgram) {
            invalidCommandProvided = true;
            while (invalidCommandProvided) {
                System.out.print("Input command: ");
                commands = in.nextLine().trim().split("\\s+"); // split prompt into tokens
                if (commands.length == 1 && commands[0].equals("exit")) { // exit
                    invalidCommandProvided = false;
                    isRunningProgram = false;
                } else if (commands.length != 3 || !commands[0].equals("start") ||
                        !commands[1].matches("(user|easy|medium|hard)") ||
                        !commands[2].matches("(user|easy|medium|hard)")) { // incorrect prompt
                    System.out.println("Bad parameters!");
                } else { // initialize game
                    play(board, PlayerFactory.makePlayer(commands[1]), PlayerFactory.makePlayer(commands[2]));
                    invalidCommandProvided = false;
                }
            }
        }
    }
}