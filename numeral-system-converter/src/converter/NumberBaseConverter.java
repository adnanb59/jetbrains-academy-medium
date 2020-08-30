package converter;

import java.util.regex.*;

public class NumberBaseConverter {
    /* Regexps for possible numbers */
    private static final String NUMBER = "([a-z0-9]+)(\\.([a-z0-9]+))?";
    private static final String BASE_ONE = "(1)+";

    /**
    * Check if number provided by user for that specified base is valid
    *
    * @param number - Number provided by user to check
    * @param base - Base (Radix) for given number
    * @return Whether or not the number is valid for that base
    */
    public static boolean isValidNumber(String number, Integer base) {
        if (base == 1) { // Essentially, checking if number is all one's
            return Pattern.compile(BASE_ONE).matcher(number).matches();
        } else {
            // First check the regex pattern, then check each digit to see if it belongs
            // to that radix, and break if one digit violating the radix property is found
            boolean t = Pattern.compile(NUMBER).matcher(number).matches();
            if (t) {
                for (int i = 0; i < number.length(); i++) {
                    if (number.charAt(i) == '.') continue;
                    else t = Character.digit(number.charAt(i), base) != -1;
                    if (!t) break;
                }
            }
            return t;
        }
    }

    /**
    * Convert given number in source base to number in target base
    *
    * @param sourceRadix - The base (radix) that the number provided by user will be converted from
    * @param sourceNumber - The number that will be converted
    * @param targetRadix - The base (radix) that the given number will be converted to
    * @return
    */
    public static String convert(Integer sourceRadix, String sourceNumber, Integer targetRadix) {
        if (targetRadix.equals(sourceRadix)) return sourceNumber;

        StringBuilder res = new StringBuilder();
        Matcher m = Pattern.compile(NUMBER).matcher(sourceNumber);
        // The integer portion of the number is either derived from the number passed in (through conversion)
        // Or a count of how many one's there are (radix 1)
        Long whole = (m.matches() && sourceRadix == 1) ? (long) m.group(1).length() :
                                                         Long.parseLong(m.group(1), sourceRadix);
        double decimal = 0.0;

        // There is a decimal portion
        if (m.group(2) != null) {
            if (sourceRadix == 10) { // No base_x to base_10 conversion required before decimal conversion to base_y
                decimal = Double.parseDouble(m.group(2));
            } else { // Do conversion of decimal from base_src to base_10
                String mantissa = m.group(3);
                for (int i = 0; i < mantissa.length(); i++) {
                    decimal += Character.digit(mantissa.charAt(i), sourceRadix)/Math.pow(sourceRadix, i+1);
                }
            }
        }

        if (targetRadix == 1) {
            for (int i = 0; i < whole; i++) res.append(1);
        } else {
            res.append(Long.toString(whole, targetRadix));
            if (m.group(2) != null) {
                res.append("");
                // Keeping to 5 decimal places, use algorithm to calculate each digit
                for (int i = 0; i < 5; i++) {
                    decimal *= targetRadix;
                    res.append(Character.digit((int) decimal, targetRadix));
                    decimal -= (int) decimal;
                }
            }
        }

        return res.toString();
    }
}