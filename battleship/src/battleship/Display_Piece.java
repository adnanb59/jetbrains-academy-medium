package battleship;

/* enum for `items that can be displayed on Battleship board */
public enum Display_Piece {
    OCCUPIED('O'), FREE('~'), STRIKE('X'), MISS('M');

    private final char c;

    Display_Piece(char c) {
        this.c = c;
    }

    @Override
    public String toString() {
        return "" + c;
    }
}