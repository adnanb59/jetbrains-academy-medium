package tictactoe.player;

import tictactoe.board.Board;
import tictactoe.board.Piece;

import java.util.Arrays;

public class Hard extends Player {
    Piece[] board_display;

    /**
    * Create a Hard AI user, uses minimax algorithm to make moves
    */
    public Hard() {
        super();
        board_display = new Piece[9];
    }

    /**
    * Add game board to be used by player, for minimax algorithm another game board is maintained.
    * Register player as observer of game.
    * 
    * @param board - Tic-Tac-Toe board to be used by player
    */
    @Override
    public void setBoard(Board board) {
        super.setBoard(board);
        board.register(this);
        Arrays.fill(board_display, Piece.EMPTY);
    }

    /**
    * Remove board from being used by player.
    * Unregister player from observing board.
    */
    @Override
    public void removeBoard() {
        if (board != null) board.unregister(this);
        super.removeBoard();
    }

    /**
    * Calculate current state of game.
    * 
    * @return 0 if no winning move, 10 if AI wins, -10 if opponent wins
    */
    private int calculateState() {
        // Calculate if there's a horizontal win
        for (int i = 0; i < 9; i+=3) {
            if (board_display[i] != Piece.EMPTY && (board_display[i] == board_display[i+1] && board_display[i+1] == board_display[i+2])) {
                return board_display[i] == board.getCurrentPlayer() ? 10 : -10;
            }
        }

        // Calculate if there's a vertical win
        for (int i = 0; i < 3; i++) {
            if (board_display[i] != Piece.EMPTY && (board_display[i] == board_display[i+3] && board_display[i] == board_display[i+6])) {
                return board_display[i] == board.getCurrentPlayer() ? 10 : -10;
            }
        }

        // Calculate if there's a main diagonal win
        if (board_display[0] != Piece.EMPTY && (board_display[0] == board_display[4] && board_display[4] == board_display[8])) {
            return board_display[0] == board.getCurrentPlayer() ? 10 : -10;
        }

        // Calculate if there's an opposite diagonal win
        if (board_display[2] != Piece.EMPTY && (board_display[2] == board_display[4] && board_display[4] == board_display[6])) {
            return board_display[2] == board.getCurrentPlayer() ? 10 : -10;
        }

        return 0;
    }

    /**
    * Minimax algorithm to calculate next move, only checks up to 3 moves ahead.
    * 
    * @param depth - current depth of recursion (moves that minimax has evaluated)
    * @param isMaximizing - based on player (AI or opponent), if score is minimizing or maximizing
    * @return Max or min score at current depth or position to move to at top level (depth==0)
    */
    private int minimax(int depth, boolean isMaximizing) {
        // Calculate current state, if there's a winning position on either
        // side then factor depth into the calculations.
        // If the algorithm has reached a conclusion (winning state, no more places to move, max depth reached)
        int state = calculateState();
        if (state > 0) state -= depth;
        if (state < 0) state += depth;
        if (state != 0 || available.size() == 0 || depth == 3) return state;

        // Go through available spots on the board, add current move to the board and check if conclusion has been reached
        Integer target_position = null, target_score = null;
        boolean target_found = false;
        for (int i = 0; i < available.size() && !target_found; i++) {
            int position = available.remove(i);
            board_display[position-1] = isMaximizing ? board.getCurrentPlayer() :
                                        board.getCurrentPlayer() == Piece.X ? Piece.O : Piece.X;
            // Recurse on new state of board to see if winning position has been found
            int score = minimax(depth+1, !isMaximizing);
            // if it's the AI's move and the recursed score is the largest score seen
            // then update the counter variables
            if (isMaximizing && (target_score == null || score > target_score)) {
                target_score = score;
                target_position = position;
                // if the recursed score is the max score possible, no need to keep going with algo
                if (target_score == 10-(depth+1)) target_found = true;
            }
            // if it's the opponent's move and the recursed score is the lowest score seen
            // then update the counter variables
            if (!isMaximizing && (target_score == null || score < target_score)) {
                target_score = score;
                target_position = position;
                // if the recursed score is the min score possible, end algo here
                if (target_score == -10+(depth+1)) target_found = true;
            }
            // Reset moves made
            available.add(i, position);
            board_display[position-1] = Piece.EMPTY;
        }

        return depth == 0 ? target_position : target_score;
    }

    /** Have the AI make a move, next move calculated using minimax algorithm. */
    @Override
    public void move() {
        System.out.printf("Making move level \"%s\"\n", "hard");
        int result = minimax(0, true);
        board.makeMove(((result-1)%3)+1,3 - (result-1)/3);
    }

    /**
    * Observer method called by Observable (board) once a move is made in the game.
    * Allows the player (observer) to update internal book-keeping structures.
    * 
    * @param r - row of coordinates on board
    * @param c - column of coordinates on board
    */
    @Override
    public void signal(int r, int c) {
        super.signal(r, c);
        board_display[8-3*c+r] = board.getPiece(r, c);
    }
}