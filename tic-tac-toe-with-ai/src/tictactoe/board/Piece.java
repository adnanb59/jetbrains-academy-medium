package tictactoe.board;

/** Enum representing Tic-Tac-Toe game piece */
public enum Piece {
    EMPTY('_'),
    X('X'),
    O('O');

    private char piece;

    Piece(char repr) {
        this.piece = repr;
    }

    public char display() {
        return this.piece;
    }
}
