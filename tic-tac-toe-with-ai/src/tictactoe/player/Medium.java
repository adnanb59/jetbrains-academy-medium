package tictactoe.player;

import tictactoe.board.Board;
import tictactoe.board.Piece;

public class Medium extends Player {
    
    /**
    * Add game board to be used by player.
    * Register player as observer of game.
    * 
    * @param board - Tic-Tac-Toe board to be used by player
    */
    @Override
    public void setBoard(Board board) {
        super.setBoard(board);
        board.register(this);
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
    * Make a move for a medium level AI.
    * If there's a winning move make it, otherwise make a defending move and if there's no defending move, make a random move.
    */
    @Override
    public void move() {
        System.out.printf("Making move level \"%s\"\n", "medium");
        int move = -1, defend = -1;
        // Go through available pieces in the board and check for winning (or defending moves)
        for (Integer a : available) {
            int f, g=0, h=0;
            if ((f=checkHorizontal(a)) == 1 || (g=checkVertical(a)) == 1 || (a % 2 != 0 && (h=checkDiagonal(a)) == 1)) {
                move = a;
                break;
            } else if (defend == -1 && (f == -1 || g == -1 || (a % 2 != 0 && h == -1))) {
                defend = a;
            }
        }

        if (move == -1 && defend == -1) { // If there's no winning or defending move, make a random move
            move = available.get((int) (Math.random()*available.size()));
        } else if (defend != -1) { // If there's a defending move and no winning move, make that move
            move = defend;
        }
        board.makeMove(((move-1)%3)+1,3 - (move-1)/3);
    }

    /**
    * Check if the two positions passed in are equal to each other (either for current player or opponent)
    * 
    * @param first - first spot on the board to check
    * @param second - second spot on the board to check
    * @return 1 if equal to current player, -1 if equal to opposition, 0 otherwise
    */
    private int checkResult(int first, int second) {
        if (board.getPiece(((first-1)%3)+1,3 - (first-1)/3) == board.getCurrentPlayer() && board.getPiece(((second-1)%3)+1,3 - (second-1)/3) == board.getCurrentPlayer()) {
            return 1;
        } else if (board.getPiece(((first-1)%3)+1,3 - (first-1)/3) != Piece.EMPTY && board.getPiece(((second-1)%3)+1,3 - (second-1)/3) != Piece.EMPTY) {
            return -1;
        } else return 0;
    }

    /**
    * Check if there's a winning state from the diagonal positions
    * 
    * @param a - piece on the board (going from 1 to 9)
    * @return 1 if win, -1 if loss, 0 for draw
    */
    private int checkDiagonal(int a) {
        if (a % 2 == 0) return 0;
        int result = 0;

        // Check main diagonal
        if (a % 4 == a % 2) {
            // Find the other 2 spots along diagonal and check if they're equal to current player or opponent
            int first = a + 4, second = a + 8;
            if (first > 9) first %= 6;
            if (second > 9) second %= 6;
            result = checkResult(first, second);
        }

        // Check opposite diagonal, if winning position hasn't been found
        if (result != 1 && (a == 5 || a % 4 == 3)) {
            int first = a + 2, second = a + 4;
            if (first > 9) first %= 6;
            if (second > 9) second %= 6;
            int tmp = checkResult(first, second);
            result = tmp == 1 ? 1 : (result == 0 ? tmp : result);
        }
        return result;
    }

    /**
    * Check if there's a winning position from the vertical position
    * 
    * @param a - piece on the board (going from 1 to 9)
    * @return 1 if win, -1 if loss, 0 for draw
    */
    private int checkVertical(int a) {
        // Find the other 2 spots along vertical and check if they're equal to current player or opponent
        int first = a+3, second = a+6;
        if (first > 9) first %= 9;
        if (second > 9) second %= 9;
        return checkResult(first, second);
    }

    /**
    * Check if there's a winning position from the horizontal position
    * 
    * @param a - piece on the board (going from 1 to 9)
    * @return 1 if win, -1 if loss, 0 for draw
    */
    private int checkHorizontal(int a) {
        // Find the other 2 spots along horizontal and check if they're equal to current player or opponent
        int first = a+1, second = a+2;
        int factor = (a-1)/3;
        if (first > 3*(factor+1)) first = (first % 3) + 3*factor;
        if (second > 3*(factor+1)) second = (second % 3) + 3*factor;
        return checkResult(first, second);
    }
}