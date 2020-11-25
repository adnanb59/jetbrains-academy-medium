package battleship;

/* Ship enum for ships that can be placed on the board */
public enum Ship {
    CARRIER(5, "Aircraft Carrier"), BATTLESHIP(4, "Battleship"), SUBMARINE(3, "Submarine"), CRUISER(3, "Cruiser"),
    DESTROYER(2, "Destroyer");

    private final int size;
    private final String repr;

    Ship(int length, String repr) {
        this.size = length;
        this.repr = repr;
    }

    public int getSize() {
        return size;
    }

    @Override
    public String toString() {
        return repr;
    }
}