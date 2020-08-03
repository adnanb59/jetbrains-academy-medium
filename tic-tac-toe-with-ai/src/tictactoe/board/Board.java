package tictactoe.board;

import java.util.Arrays;

public class Board {
    private Piece[][] board;
    private State state;
    private int x_moves, o_moves;

    public Board() {
        board = new Piece[3][3];
        prepareBoardForUse();
    }

    private void prepareBoardForUse() {
        state = State.NOT_FINISHED_X;
        x_moves = 0;
        o_moves = 0;
        for (Piece[] row : this.board) {
            Arrays.fill(row, Piece.EMPTY);
        }
    }

    public boolean isAvailable(int r, int c) {
        return board[3-c][r-1] == Piece.EMPTY;
    }

    public boolean isCompleted() {
        return !(this.state == State.NOT_FINISHED_O || this.state == State.NOT_FINISHED_X);
    }

    public String getState() {
        return this.state.toString();
    }

    public String printBoard() {
        StringBuilder sb = new StringBuilder("---------\n");
        for (int i = 0; i < board.length; i++) {
            sb.append("|");
            for (int j = 0; j < board[0].length; j++) {
                sb.append(String.format(" %c", board[i][j].display()));
            }
            sb.append(" |\n");
        }
        sb.append("---------");
        return sb.toString();
    }

    public Piece getCurrentPlayer() {
        if (this.state == State.NOT_FINISHED_X) return Piece.X;
        else if (this.state == State.NOT_FINISHED_O) return Piece.O;
        else return Piece.EMPTY;
    }

    public void reset() {
        prepareBoardForUse();
    }

    private int processBoardForWins(int r, int c) {
        int wins = 0;
        if (this.board[r][c] != Piece.EMPTY) {
            if (board[0][c] == board[1][c] && board[1][c] == board[2][c]) ++wins;
            if (board[r][0] == board[r][1] && board[r][1] == board[r][2]) ++wins;
            if (r == c && (board[0][0] == board[1][1] && board[1][1] == board[2][2])) ++wins;
            if (r+c == 2 && (board[0][2] == board[1][1] && board[1][1] == board[2][0])) ++wins;
        }
        return wins;
    }

    private void updateGameState(int x_wins, int o_wins) {
        if (Math.abs(x_moves - o_moves) > 1 || (x_wins > 0 && o_wins > 0)) this.state = State.IMPOSSIBLE;
        else if (x_wins == 0 && o_wins == 0 && (x_moves + o_moves == board.length*board[0].length))
            this.state = State.DRAW;
        else if (x_wins > 1) this.state = State.X_WINS;
        else if (o_wins > 1) this.state = State.O_WINS;
        else this.state = this.state == State.NOT_FINISHED_X ? State.NOT_FINISHED_O : State.NOT_FINISHED_X;
    }

    public void makeMove(int r, int c) {
        this.board[3-c][r-1] = this.state == State.NOT_FINISHED_X ? Piece.X : Piece.O;
        this.x_moves += this.board[3-c][r-1] == Piece.X ? 1 : 0;
        this.o_moves += this.board[3-c][r-1] == Piece.O ? 1 : 0;
        int wins = processBoardForWins(3-c, r-1);
        updateGameState(this.board[3-c][r-1] == Piece.X ? wins : 0, this.board[3-c][r-1] == Piece.O ? wins : 0);
    }
}