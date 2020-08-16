package tictactoe.player;

import tictactoe.board.Board;
import tictactoe.board.Piece;

import java.util.Arrays;

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

    private int calculateState() {
        for (int i = 0; i < 9; i+=3) {
            if (board_display[i] != Piece.EMPTY && (board_display[i] == board_display[i+1] && board_display[i+1] == board_display[i+2])) {
                return board_display[i] == board.getCurrentPlayer() ? 10 : -10;
            }
        }
        for (int i = 0; i < 3; i++) {
            if (board_display[i] != Piece.EMPTY && (board_display[i] == board_display[i+3] && board_display[i] == board_display[i+6])) {
                return board_display[i] == board.getCurrentPlayer() ? 10 : -10;
            }
        }
        if (board_display[0] != Piece.EMPTY && (board_display[0] == board_display[4] && board_display[4] == board_display[8])) {
            return board_display[0] == board.getCurrentPlayer() ? 10 : -10;
        }
        if (board_display[2] != Piece.EMPTY && (board_display[2] == board_display[4] && board_display[4] == board_display[6])) {
            return board_display[2] == board.getCurrentPlayer() ? 10 : -10;
        }
        return 0;
    }

    private int minimax(int depth, boolean isMaximizing) {
        int state = calculateState();
        if (state > 0) state -= depth;
        if (state < 0) state += depth;
        if (state != 0 || available.size() == 0 || depth == 3) return state;

        Integer target_position = null, target_score = null;
        boolean target_found = false;
        for (int i = 0; i < available.size() && !target_found; i++) {
            int position = available.remove(i);
            board_display[position-1] = isMaximizing ? board.getCurrentPlayer() :
                                        board.getCurrentPlayer() == Piece.X ? Piece.O : Piece.X;
            int score = minimax(depth+1, !isMaximizing);
            if (isMaximizing && (target_score == null || score > target_score)) {
                target_score = score;
                target_position = position;
                if (target_score == 10-(depth+1)) target_found = true;
            }
            if (!isMaximizing && (target_score == null || score < target_score)) {
                target_score = score;
                target_position = position;
                if (target_score == -10+(depth+1)) target_found = true;
            }
            available.add(i, position);
            board_display[position-1] = Piece.EMPTY;
        }

        return depth == 0 ? target_position : target_score;
    }

    @Override
    public void move() {
        System.out.printf("Making move level \"%s\"\n", "hard");
        int result = minimax(0, true);
        board.makeMove(((result-1)%3)+1,3 - (result-1)/3);
    }

    @Override
    public void signal(int r, int c) {
        super.signal(r, c);
        board_display[8-3*c+r] = board.getPiece(r, c);
    }
}