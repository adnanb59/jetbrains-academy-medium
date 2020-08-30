package tictactoe.board;

/** State of Tic-Tac-Toe game */
public enum State {
    DRAW("Draw"),
    NOT_FINISHED_X("Game not finished"),
    NOT_FINISHED_O("Game not finished"),
    O_WINS("O wins"),
    X_WINS("X wins"),
    IMPOSSIBLE("Impossible");

    private final String statement;

    /** Constructor for State Enum */
    State(String st) {
        this.statement = st;
    }

    /**
    * String representation of State Enum
    * @return statement associated with given state
    */
    @Override
    public String toString() {
        return this.statement;
    }
}