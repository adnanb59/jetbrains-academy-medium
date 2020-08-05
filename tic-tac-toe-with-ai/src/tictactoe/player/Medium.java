package tictactoe.player;

import tictactoe.board.Board;
import tictactoe.board.Piece;

import java.util.HashSet;
import java.util.Set;

public class Medium extends Player {


    @Override
    public void setBoard(Board board) {
        super.setBoard(board);
        board.register(this);
    }

    @Override
    public void removeBoard() {
        board.unregister(this);
        super.removeBoard();
    }

    private int checkSectionOfBoard(int r, int c, int delta_r, int delta_c) {
        int row = 0, col = 0, free_count = 0;
        if (board.isAvailable(r, c)) {
            free_count++;
            row = r;
            col = c;
        }
        if (board.isAvailable(r + delta_r, c + delta_c)) {
            free_count++;
            row = r + delta_r;
            col = c + delta_c;
        }
        if (board.isAvailable(r + 2*delta_r, c + 2*delta_c)) {
            free_count++;
            row = r + 2*delta_r;
            col = c + 2*delta_c;
        }
        if (free_count == 1) {
            int tmp_row = row == r ? r + delta_r : r, tmp_col = col == c ? c + delta_c : c;
            if (board.getPiece(tmp_row, tmp_col) ==
                board.getPiece(row == r + 2*delta_r ? r + delta_r : r + 2*delta_r, col == c + 2*delta_c ? c + delta_c : c + 2*delta_c)) {
                int m = board.getPiece(tmp_row, tmp_col) == board.getCurrentPlayer() ? 1 : -1;
                return m*(delta_r == 0 ? col : row);
            }
        }
        return 0;
    }

    @Override
    public void move() {
        System.out.printf("Making move level \"%s\"\n", "medium");
        int r = 0, c = 0, free_count = 0;
        // Check verticals
        for (int i = 1; i <= 3; i++) {
            // i, 1, 0, 1
            int tmp = checkSectionOfBoard(i, 1, 0, 1);
            if (tmp > 0) {
                board.makeMove(i, tmp);
                return;
            } else if (tmp < 0) {
                r = i;
                c = -tmp;
            }
            /*free_count = (board.isAvailable(i, 3) ? 1 : 0) +
                       (board.isAvailable(i, 2) ? 1 : 0) +
                       (board.isAvailable(i, 1) ? 1 : 0);
            if (free_count == 1) {
                int j = board.isAvailable(i, 3) ? 3 : board.isAvailable(i, 2) ? 2 : 1;
                if (board.getPiece(i, j-1 <= 0 ? 3+(j-1) : j-1) == board.getPiece(i, j-2 <= 0 ? 3+(j-2) : j-2)) {
                    if (board.getPiece(i, j-1 <= 0 ? 3+(j-1) : j-1) == board.getCurrentPlayer()) {
                        board.makeMove(i, j);
                        System.out.println("(" + i + ", " + j + ")");
                        return;
                    } else {
                        r = i;
                        c = j;
                    }
                }
            }*/
        }

        // Check horizontals
        for (int i = 1; i <= 3; i++) {
            // 1, i, 1, 0
            int tmp = checkSectionOfBoard(1, i, 1, 0);
            if (tmp > 0) {
                board.makeMove(tmp, i);
                return;
            } else if (tmp < 0) {
                r = -tmp;
                c = i;
            }
            /*free_count = (board.isAvailable(1, i) ? 1 : 0) +
                    (board.isAvailable(2, i) ? 1 : 0) +
                    (board.isAvailable(3, i) ? 1 : 0);
            if (free_count == 1) {
                int j = board.isAvailable(1, i) ? 1 : board.isAvailable(2, i) ? 2 : 3;
                if (board.getPiece(j-1 <= 0 ? 3+(j-1) : j-1, i) == board.getPiece(j-2 <= 0 ? 3+(j-2) : j-2, i)) {
                    if (board.getPiece(j-1 <= 0 ? 3+(j-1) : j-1, i) == board.getCurrentPlayer()) {
                        board.makeMove(j, i);
                        System.out.println("(" + j + ", " + i + ")");
                        return;
                    } else if (r == 0) {
                        r = j;
                        c = i;
                    }
                }
            }*/
        }

        // Check diagonals
        // - Check R to L
        // 1, 1, 1, 1
        int tmp = checkSectionOfBoard(1, 1, 1, 1);
        if (tmp > 0) {
            board.makeMove(tmp, tmp);
            return;
        } else if (tmp < 0) {
            r = -tmp;
            c = -tmp;
        }
        /*free_count = (board.isAvailable(3, 3) ? 1 : 0) + (board.isAvailable(2, 2) ? 1 : 0) +
                   (board.isAvailable(1, 1) ? 1 : 0);
        if (free_count == 1) {
            int elem = board.isAvailable(1, 1) ? 1 : board.isAvailable(2, 2) ? 2 : 3;
            int tmp = elem - 1 <= 0 ? 3 + (elem - 1) : elem - 1;
            int tmp_second = elem - 2 <= 0 ? 3 + (elem - 2) : elem - 2;
            if (board.getPiece(tmp, tmp) == board.getPiece(tmp_second, tmp_second)) {
                if (board.getPiece(tmp, tmp) == board.getCurrentPlayer()) {
                    board.makeMove(elem, elem);
                    System.out.println("(" + elem + ", " + elem + ")");
                    return;
                } else if (r == 0) {
                    r = elem;
                    c = elem;
                }
            }
        }*/
        // - Check L to R
        // 1, 3, 1, -1
        tmp = checkSectionOfBoard(1, 3, 1, -1);
        if (tmp > 0) {
            board.makeMove(tmp, 3-tmp+1);
            return;
        } else if (tmp < 0) {
            r = tmp;
            c = 3-tmp+1;
        }
        /*free_count = (board.isAvailable(1, 3) ? 1 : 0) + (board.isAvailable(2, 2) ? 1 : 0) +
               (board.isAvailable(3, 1) ? 1 : 0);
        if (free_count == 1) {
            int elem = board.isAvailable(1, 3) ? 1 : board.isAvailable(2, 2) ? 2 : 3;
            int tmp = elem - 1 <= 0 ? 3 + (elem - 1) : elem - 1;
            int tmp_second = elem - 2 <= 0 ? 3 + (elem - 2) : elem - 2;
            if (board.getPiece(tmp, 3-tmp+1) == board.getPiece(tmp_second, 3-tmp_second+1)) {
                if (board.getPiece(tmp, 3-tmp+1) == board.getCurrentPlayer()) {
                    board.makeMove(elem, 3-elem+1);
                    System.out.println("(" + elem + ", " + (3-elem+1) + ")");
                    return;
                } else {
                    r = elem;
                    c = 3-elem+1;
                }
            }
        }*/

        if (r == 0) {
            while (!board.isAvailable(r=((int) (Math.random()*3 + 1)), c=((int) (Math.random()*3 + 1))));
        }
        board.makeMove(r, c);
        System.out.println("(" + r + ", " + c + ")");
    }
}