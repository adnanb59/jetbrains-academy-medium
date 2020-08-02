package tictactoe.player;

import tictactoe.board.Board;

public abstract class Player {
    protected Board board;

    public void setBoard(Board board) {
        this.board = board;
    }

    public abstract void move();
}