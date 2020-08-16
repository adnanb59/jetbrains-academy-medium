package tictactoe.player;

import tictactoe.board.Board;
import tictactoe.board.Piece;

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

    @Override
    public void move() {
        System.out.printf("Making move level \"%s\"\n", "medium");
        int move = -1, defend = -1;
        for (Integer a : available) {
            int f, g=0, h=0;
            if ((f=checkHorizontal(a)) == 1 || (g=checkVertical(a)) == 1 || (a % 2 != 0 && (h=checkDiagonal(a)) == 1)) {
                move = a;
                break;
            } else if (defend == -1 && (f == -1 || g == -1 || (a % 2 != 0 && h == -1))) {
                defend = a;
            }
        }
        if (move == -1 && defend == -1) {
            move = available.get((int) (Math.random()*available.size()));
        } else if (defend != -1) {
            move = defend;
        }
        board.makeMove(((move-1)%3)+1,3 - (move-1)/3);
    }

    private int checkResult(int first, int second) {
        if (board.getPiece(((first-1)%3)+1,3 - (first-1)/3) == board.getCurrentPlayer() && board.getPiece(((second-1)%3)+1,3 - (second-1)/3) == board.getCurrentPlayer()) {
            return 1;
        } else if (board.getPiece(((first-1)%3)+1,3 - (first-1)/3) != Piece.EMPTY && board.getPiece(((second-1)%3)+1,3 - (second-1)/3) != Piece.EMPTY) {
            return -1;
        } else return 0;
    }

    private int checkDiagonal(int a) {
        if (a % 2 == 0) return 0;
        int result = 0;

        if (a % 4 == a % 2) {
            int first = a + 4, second = a + 8;
            if (first > 9) first %= 6;
            if (second > 9) second %= 6;
            result = checkResult(first, second);
        }
        if (result != 1 && (a == 5 || a % 4 == 3)) {
            int first = a + 2, second = a + 4;
            if (first > 9) first %= 6;
            if (second > 9) second %= 6;
            int tmp = checkResult(first, second);
            result = tmp == 1 ? 1 : (result == 0 ? tmp : result);
        }
        return result;
    }

    private int checkVertical(int a) {
        int first = a+3, second = a+6;
        if (first > 9) first %= 9;
        if (second > 9) second %= 9;
        return checkResult(first, second);
    }

    private int checkHorizontal(int a) {
        int first = a+1, second = a+2;
        int factor = (a-1)/3;
        if (first > 3*(factor+1)) first = (first % 3) + 3*factor;
        if (second > 3*(factor+1)) second = (second % 3) + 3*factor;
        return checkResult(first, second);
    }
}