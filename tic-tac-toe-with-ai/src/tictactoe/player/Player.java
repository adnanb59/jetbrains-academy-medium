package tictactoe.player;

import tictactoe.board.Board;

import java.util.ArrayList;
import java.util.List;

public abstract class Player {
    protected Board board;
    protected ArrayList<Integer> available;

    public void setBoard(Board board) {
        if (this.board == null) {
            this.board = board;
            this.available = new ArrayList<>(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9));
        }
    }
    public void removeBoard() { this.board = null; }
    public abstract void move();

    public void signal(int r, int c) {
        int idx = available.indexOf(9 - 3*c + r);
        if (idx != -1) available.remove(idx);
    }
}