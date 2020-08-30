package tictactoe.board;

import tictactoe.player.*;

import java.util.ArrayList;
import java.util.Arrays;

public class Board {
    private Piece[][] board;
    private State state;
    private int x_moves, o_moves;
    private ArrayList<Player> listeners;

    /** Board constructor */
    public Board() {
        board = new Piece[3][3];
        listeners = new ArrayList<>();
        prepareBoardForUse();
    }

    /** Initialize board to be used (reset states and counters, empty board) */
    private void prepareBoardForUse() {
        state = State.NOT_FINISHED_X;
        x_moves = 0;
        o_moves = 0;
        for (Piece[] row : this.board) {
            Arrays.fill(row, Piece.EMPTY);
        }
    }

    /**
    * Check if position passed in is available
    * 
    * @param r - Row of coordinate on board
    * @param c - Column of coordinate on board
    * @return Whether or not spot is available
    */
    public boolean isAvailable(int r, int c) {
        return board[3-c][r-1] == Piece.EMPTY;
    }

    /**
    * Check if the game has finished (Draw or Win)
    * 
    * @return Whether the state is not one of the playing states, meaning the game has concluded
    */
    public boolean isCompleted() {
        return !(this.state == State.NOT_FINISHED_O || this.state == State.NOT_FINISHED_X);
    }

    /**
    * Get current state of game
    * 
    * @return String representation of State enum that represents the state of the game
    */
    public String getState() {
        return this.state.toString();
    }

    /**
    * String representation of Tic-Tac-Toe board
    * 
    * @return Tic-Tac-Toe board
    */
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

    /**
    * Get current player making move in the game
    * 
    * @return Player Enum representing player making move (or EMPTY if resolved state was reached)
    */
    public Piece getCurrentPlayer() {
        if (this.state == State.NOT_FINISHED_X) return Piece.X;
        else if (this.state == State.NOT_FINISHED_O) return Piece.O;
        else return Piece.EMPTY;
    }

    /** Public method for users to clean up game board for re-use */
    public void reset() {
        prepareBoardForUse();
    }

    /**
    * Check if there has been a win in the game with respect to move at position passed in
    * 
    * @param r - Row of coordinate to check
    * @param c - Column of coordinate to check
    * @return Number of winning moves that have been found
    */
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

    /**
     * Update the game state after a move has been made. This can either result in a conclusive state or
     * just flipping current the player (from X to O and vice-versa).
     * 
     * @param x_wins - Number of X wins after last move
     * @param o_wins - Number of O wins after last move
     */
    private void updateGameState(int x_wins, int o_wins) {
        // If one of the pieces has been played more than once more than the other or both pieces have winning moves,
        // the game has reached an impossible state. Otherwise, check for conclusion or next player.
        if (Math.abs(x_moves - o_moves) > 1 || (x_wins > 0 && o_wins > 0)) this.state = State.IMPOSSIBLE;
        else if (x_wins == 0 && o_wins == 0 && (x_moves + o_moves == board.length*board[0].length))
            this.state = State.DRAW;
        else if (x_wins >= 1) this.state = State.X_WINS;
        else if (o_wins >= 1) this.state = State.O_WINS;
        else this.state = this.state == State.NOT_FINISHED_X ? State.NOT_FINISHED_O : State.NOT_FINISHED_X;
    }

    /**
    * Function the user calls to make a move with coordinates passed in
    * 
    * @param r - Row of coordinate to make move in
    * @param c - Column of coordinate to make move in
    */
    public void makeMove(int r, int c) {
        // Make move, update counters, check if a win has been found and calculate new game state
        this.board[3-c][r-1] = getCurrentPlayer();
        this.x_moves += this.board[3-c][r-1] == Piece.X ? 1 : 0;
        this.o_moves += this.board[3-c][r-1] == Piece.O ? 1 : 0;
        int wins = processBoardForWins(3-c, r-1);
        updateGameState(this.board[3-c][r-1] == Piece.X ? wins : 0, this.board[3-c][r-1] == Piece.O ? wins : 0);
        notifyPlayers(r, c);
    }

    /**
    * Notify observers of game of move made.
    * 
    * @param r - Row of coordinate that move was made in
    * @param c - Column of coordinate that move was made in
    */
    private void notifyPlayers(int r, int c) {
        for (Player p : listeners) {
            p.signal(r, c);
        }
    }

    /**
    * Find piece on board at passed in position
    * 
    * @param r - Row of coordinate
    * @param c - Column of coordinate
    * @return Piece enum representing place on board
    */
    public Piece getPiece(int r, int c) {
        return board[3-c][r-1];
    }

    /**
    * Unregister observer from game
    * 
    * @param observer - Player that will be removed from observers list
    */
    public void unregister(Player observer) {
        listeners.remove(observer);
    }

    /**
    * Register observer for game
    * 
    * @param observer - Player that will observe game
    */
    public void register(Player observer) {
        listeners.add(observer);
    }
}