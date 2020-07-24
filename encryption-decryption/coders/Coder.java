package coders;

interface Evaluator {
    /**
    * Take the passed in character and do initial shift (left or right)
    * before passing result to function to complete shift
    *
    * @param c - Initial character that will be modified
    * @param shift - The amount that the character will be shifted
    * @param multiplier - -1 indicates left shift (for decryption), 1 indicates right shift (for encryption)
    * @return Character after shift (or c if not modified)
    */
    Character evaluate(char c, int shift, int multiplier);
}

public abstract class Coder implements Evaluator {
    protected final Integer ENCRYPT_CONST = 1;
    protected final Integer DECRYPT_CONST = -1;

    /**
    * After initial shift occurs, take result and calculate final shift for character
    *
    * @param character - Unicode integer value for character after initial shift
    * @param min - Minimum possible value for character
    * @param max - Maximum possible value for character
    * @return Modified character
    */
    public Character modify(int character, Character min, Character max) {
        int tmp = character;
        if (tmp < 0) tmp += (max - min + 1); // Java doesn't handle modulus calculations well for negative numbers
        tmp = (tmp % (max - min + 1)) + min;
        return (char) tmp;
    }

    /**
    * Take original text and amount to shift as well as direction (specified by multiplier)
    * then go through text, modify the characters and return the result
    *
    * @param ciphertext - Original string that will be modified
    * @param shift - Number of characters to shift
    * @param multiplier - -1 indicates left shift (for decryption), 1 indicates right shift (for encryption)
    * @return Modified string
    */
    protected String action(String ciphertext, int shift, int multiplier) {
        // Go through ciphertext character-by-character, modify it and store it
        // Calls evaluate() which is defined by concrete implementations
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < ciphertext.length(); i++) {
            sb.append(evaluate(ciphertext.charAt(i), shift, multiplier));
        }
        return sb.toString();
    }

    /**
    * Take initial text and amount to shift characters, then encrypt the text and return result
    *
    * @param ciphertext - Original string that will be modified
    * @param shift - Number of characters to shift
    * @return Encrypted string
    */
    public String encrypt(String ciphertext, int shift) {
        return action(ciphertext, shift, ENCRYPT_CONST);
    }

    /**
    * Take initial text and amount to shift characters, then decrypt the text and return result
    *
    * @param ciphertext - Original string that will be modified
    * @param shift - Number of characters to shift
    * @return Decrypted string
    */
    public String decrypt(String ciphertext, int shift) {
        return action(ciphertext, shift, DECRYPT_CONST);
    }
}