package coders;

public class Unicode extends Coder {
    private final Character MIN = ' ';
    private final Character MAX = '~';

    @Override
    public Character evaluate(char c, int shift, int multiplier) {
        return modify(c - MIN + shift * multiplier, MIN, MAX);
    }
}