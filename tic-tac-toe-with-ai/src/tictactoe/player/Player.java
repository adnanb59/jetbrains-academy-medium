package tictactoe.player;

import tictactoe.board.Board;

import java.util.ArrayList;
import java.util.List;

public abstract class Player {
    protected Board board;
    protected ArrayList<Integer> available;

    /**
    * Set the game board for the given player and reset available
    * slots on the board (book-keeping) if there isn't a board already set.
    * 
    * @param board - Tic-Tac-Toe board that player will use for the game
    */
    public void setBoard(Board board) {
        if (this.board == null) {
            this.board = board;
            this.available = new ArrayList<>(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9));
        }
    }

    /** Remove game board from player, so they can't access it. */
    public void removeBoard() { this.board = null; }
    
    public abstract void move();

    /**
    * Observer method called by Observable (board) once a move is made in the game.
    * Allows the player (observer) to update internal book-keeping structures.
    * 
    * @param r - row of coordinates on board
    * @param c - column of coordinates on board
    */
    public void signal(int r, int c) {
        int idx = available.indexOf(9 - 3*c + r);
        if (idx != -1) available.remove(idx);
    }
}