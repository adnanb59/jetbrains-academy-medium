package tictactoe.player;

public class Easy extends Player {

    @Override
    public void move() {
        if (board == null || board.isCompleted()) return;
        System.out.printf("Making move level \"%s\"\n", "easy");
        int r, c;
        while (board.isAvailable(r=((int) (Math.random()*3 + 1)), c=((int) (Math.random()*3 + 1))));
        board.makeMove(r, c);
    }
}