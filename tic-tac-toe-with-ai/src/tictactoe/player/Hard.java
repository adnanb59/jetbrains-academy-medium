package tictactoe.player;

import tictactoe.board.Board;
import tictactoe.board.Piece;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Hard extends Player {
    Piece[] board_display;

    public Hard() {
        super();
        board_display = new Piece[9];
    }

    @Override
    public void setBoard(Board board) {
        super.setBoard(board);
        board.register(this);
        Arrays.fill(board_display, Piece.EMPTY);
    }

    @Override
    public void removeBoard() {
        board.unregister(this);
        super.removeBoard();
    }

    public Map<String, Integer> minimax(Integer alpha, Integer beta, int depth, boolean isMaximizing) {
        Map<String, Integer> result = new HashMap<>();
        for (int i = 0; i < available.size(); i++) {
            int value = available.get(i);

            available.add(i, value);
        }
        return result;
    }

    @Override
    public void move() {
        System.out.printf("Making move level \"%s\"\n", "hard");
        Map<String, Integer> result = minimax(null, null, 3, true);
        int choice = result.get("choice");
        board.makeMove(((choice-1)%3)+1,3 - (choice-1)/3);
        // Call minimax, depth=3, alpha-beta pruning
    }

    @Override
    public void signal(int r, int c) {
        super.signal(r, c);
        board_display[9-3*c+r] = board.getPiece(r, c);
    }
}