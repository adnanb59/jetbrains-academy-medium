package tictactoe.player;

import tictactoe.board.Board;

public class Easy extends Player {

    /**
    * Add game board to be used by player.
    * Register player as observer of game.
    * 
    * @param board - Tic-Tac-Toe board to be used by player
    */
    @Override
    public void setBoard(Board board) {
        super.setBoard(board);
        board.register(this);
    }

    /**
    * Remove board from being used by player.
    * Unregister player from observing board.
    */
    @Override
    public void removeBoard() {
        if (board != null) board.unregister(this);
        super.removeBoard();
    }

    /** Make a move representing an easy AI (just a random move on the board) */
    @Override
    public void move() {
        if (board == null || board.isCompleted()) return;
        System.out.printf("Making move level \"%s\"\n", "easy");
        int v = available.get((int) (Math.random()*available.size()));
        board.makeMove(((v-1)%3)+1, 3-(v-1)/3);
    }
}