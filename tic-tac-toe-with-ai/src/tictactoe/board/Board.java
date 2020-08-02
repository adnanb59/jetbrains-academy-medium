package tictactoe.board;

public class Board {
    private char[][] board;
    private State state;

    public Board() {
        board = new char[3][3];
        state = State.NOT_FINISHED_X;
    }

    public boolean isAvailable(int r, int c) {
        return board[3-c][r-1] == Piece.EMPTY.display();
    }

    public boolean isCompleted() {
        return this.state == State.NOT_FINISHED_O || this.state == State.NOT_FINISHED_X;
    }

    public String getState() {
        return this.state.toString();
    }

    public String printBoard() {
        StringBuilder sb = new StringBuilder("---------\n");
        for (int i = 0; i < board.length; i++) {
            sb.append("|");
            for (int j = 0; j < board[0].length; j++) {
                sb.append(String.format(" %c", board[i][j]));
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
    }

    public void makeMove(int r, int c) {
    }
}