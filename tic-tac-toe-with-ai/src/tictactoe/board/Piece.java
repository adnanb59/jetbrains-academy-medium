package tictactoe.board;

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
