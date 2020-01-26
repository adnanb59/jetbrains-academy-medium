import converter.*;

import java.util.*;

public class Runner {
    // Main method
    public static void main(String[] args) {
        // -- INITIALIZE ARGUMENTS --
        Scanner in = new Scanner(System.in);
        Integer src = null, tgt = null;
        String num = null;
        byte valid = 0;

        // -- PROCESS USER INPUT --
        // Run loop until valid=111 (where each bit is set when valid input is provided by user)
        while (valid != 7) {
            try {
                // If source base hasn't been set (or upon attempt it was invalid), then set it
                if ((valid & 1) == 0) {
                    System.out.print("Enter source number's base: ");
                    src = in.nextInt();
                    if (src < 1 || src > 36)
                        throw new IllegalArgumentException("Valid base is between 1 and 36");
                    else valid += 1;
                }

                // If source value hasn't been set (& only if base is set), then set it
                if ((valid & 2) == 0 && (valid & 1) != 0) {
                    System.out.print("Enter source number: ");
                    num = in.next();
                    if (!NumberBaseConverter.isValidNumber(num, src))
                        throw new IllegalArgumentException("Enter a valid number");
                    else valid += 2;
                }

                // If target base hasn't been set (or was invalid the last attempt), then set it
                if ((valid & 4) == 0) {
                    System.out.print("Enter target number's base: ");
                    tgt = in.nextInt();
                    if (tgt < 1 || tgt > 36) throw new IllegalArgumentException("Valid base is between 1 and 36");
                    else valid += 4;
                }
            } catch(InputMismatchException e) {
                System.err.println("Error: Please enter numbers");
                in.nextLine();
            } catch(IllegalArgumentException e) {
                System.err.println("Error: " + e.getMessage());
                in.nextLine();
            }
        }

        // -- OUTPUT RESULTS --
        String ret = NumberBaseConverter.convert(src, num, tgt);
        System.out.printf("%s in base %d is equal to %s in base %d\n", num, src, ret, tgt);
    }
}