package coders;

public class Shift extends Coder {
    private final Character LOWER_MIN = 'a';
    private final Character LOWER_MAX = 'z';
    private final Character UPPER_MIN = 'A';
    private final Character UPPER_MAX = 'Z';

    @Override
    public Character evaluate(char c, int shift, int multiplier) {
        // If character is a letter then have it modified, otherwise leave it as-is
        if (Character.isLetter(c)) {
            // Check if character is upper or lower case to calculate offset for modification calculations
            char local_min = (c >= 'a') ? LOWER_MIN : UPPER_MIN;
            char local_max = (c >= 'a') ? LOWER_MAX : UPPER_MAX;
            return modify(c - local_min + shift * multiplier, local_min, local_max);
        } else return c;
    }
}
