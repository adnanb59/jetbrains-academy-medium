package tictactoe.player;

import tictactoe.board.Board;

public class Hard extends Player {

    @Override
    public void setBoard(Board board) {
        super.setBoard(board);
        board.register(this);
    }

    @Override
    public void removeBoard() {
        board.unregister(this);
        super.removeBoard();
    }

    @Override
    public void move() {
        System.out.printf("Making move level \"%s\"\n", "hard");
    }
}