package tictactoe.player;

import tictactoe.board.Board;

public abstract class Player {
    protected Board board;

    public void setBoard(Board board) {
        if (this.board == null) this.board = board;
    }
    public void removeBoard() { this.board = null; }
    public abstract void move();
}