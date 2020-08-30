import java.util.*;

import processor.*;

public class Runner {
    // MAIN METHOD
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        // Once program starts, show menu to give user options on matrix operations they can calculate
        boolean dontExit = true, displayPrompt = true;
        while (dontExit) {
            if (displayPrompt) {
                System.out.println("1. Add matrices");
                System.out.println("2. Multiply matrix to a constant");
                System.out.println("3. Multiply matrices");
                System.out.println("4. Transpose matrix");
                System.out.println("5. Calculate a determinant");
                System.out.println("6. Inverse matrix");
                System.out.println("0. Exit");
                displayPrompt = false;
            }
            // If option is valid, do the operation (further input reading in specific functions) or exit
            // If not, require user to enter a valid one
            try {
                System.out.print("Your choice: ");
                int v = Integer.parseInt(in.nextLine());
                switch (v) {
                    case 1:
                        addMatrices(in);
                        displayPrompt = true;
                        break;
                    case 2:
                        scalarMultiply(in);
                        displayPrompt = true;
                        break;
                    case 3:
                        multMatrices(in);
                        displayPrompt = true;
                        break;
                    case 4:
                        transposeMatrix(in);
                        displayPrompt = true;
                        break;
                    case 5:
                        findDeterminant(in);
                        displayPrompt = true;
                        break;
                    case 6:
                        invertMatrix(in);
                        displayPrompt = true;
                        break;
                    case 0:
                        dontExit = false;
                        break;
                    default:
                        System.out.println("Invalid option, try again.");
                        break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid option, try again.");
            }
        }
    }

    /**
    * Invert matrix inputted by user, if matrix is not a square matrix then exit.
    * Assumption is that an invertible matrix is passed in.
    * If a non-invertible matrix is passed in, then null is given as result
    *
    * @param in - Scanner to read input from user
    */
    private static void invertMatrix(Scanner in) {
        Matrix m = createMatrix(in, "");
        if (m.getCols() != m.getRows()) { // Ensure a square matrix was provided by user
            System.out.println("Enter a square matrix");
            return;
        }
        Matrix result = MatrixOperation.inverse(m);
        System.out.println("The result is:");
        System.out.println(result.toString());
    }

    /**
    * Display the determinant of a user inputted (square) matrix.
    * If matrix is not square, then exit
    *
    * @param in - Scanner to read input from user
    */
    private static void findDeterminant(Scanner in) {
        Matrix m = createMatrix(in, "");
        if (m.getCols() != m.getRows()) {
            System.out.println("Enter a square matrix");
            return;
        }
        System.out.println("The result is:");
        System.out.println(MatrixOperation.determinant(m) + "\n");
    }

    /**
    * Transpose a user inputted matrix based on transpose type specified by the user.
    * 
    * @param in - Scanner to read input from
    */
    private static void transposeMatrix(Scanner in) {
        boolean hasPrompt = false;
        int res = 0;
        System.out.println("1. Main diagonal");
        System.out.println("2. Side diagonal");
        System.out.println("3. Vertical line");
        System.out.println("4. Horizontal line");
        // Have user choose transpose type
        while (!hasPrompt) {
            try {
                System.out.print("Your choice: ");
                res = Integer.parseInt(in.nextLine());
                hasPrompt = true;
            } catch (NumberFormatException e) {
                System.out.println("Error parsing numbers.");
            }
        }

        // Create matrix and then transpose it
        Matrix m = createMatrix(in, "");
        Matrix ret = null;
        switch (res) {
            case 1:
                ret = MatrixOperation.transposeMain(m);
                break;
            case 2:
                ret = MatrixOperation.transposeSide(m);
                break;
            case 3:
                ret = MatrixOperation.transposeVertical(m);
                break;
            case 4:
                ret = MatrixOperation.transposeHorizontal(m);
                break;
            default:
                break;
        }
        System.out.println("The result is:");
        System.out.println(ret);
    }

    /**
    * Utility function to create a matrix using user input.
    *
    * @param in - Scanner to read input from
    * @param s - label to show user to indicate matrix being created
    * @return Created Matrix object
    */
    private static Matrix createMatrix(Scanner in, String s) {
        Matrix ret = null;
        boolean isNotValid = true;
        // Have user enter appropriate dimensions for matrix to be created
        while (isNotValid) {
            try {
                System.out.print("Enter size of " + s + " matrix: ");
                int[] values = Arrays.stream(in.nextLine().trim().split("\\s+")).mapToInt(Integer::parseInt).toArray();
                if (values.length != 2 || values[0] < 0 || values[1] < 0) {
                    System.out.println("Invalid entry for dimensions.");
                } else {
                    ret = new Matrix(values[0], values[1]);
                    isNotValid = false;
                }
            } catch (NumberFormatException e) {
                System.out.println("Error parsing numbers.");
            }
        }

        // Have user enter values for the matrix
        System.out.println("Enter " + s + " matrix:");
        for (int i = 0; i < ret.getRows();) {
            try {
                double[] values = Arrays.stream(in.nextLine().trim().split("\\s+")).mapToDouble(Float::parseFloat).toArray();
                if (values.length != ret.getCols()) System.out.println("Values doesn't match column size.");
                else {
                    for (int j = 0; j < ret.getCols(); j++) ret.setValue(values[j], i, j);
                    i++;
                }
            } catch (NumberFormatException e) {
                System.out.println("Error parsing numbers.");
            }
        }
        return ret;
    }

    /**
    * Read two matrices from the user and multiply them together.
    * If the first matrix's # of columns is not equal to the second matrix's # of rows, then exit
    *
    * @param in - Scanner to read input from
    */
    private static void multMatrices(Scanner in) {
        Matrix m = createMatrix(in, "first");
        Matrix n = createMatrix(in, "second");
        Matrix ret = m.getCols() == n.getRows() ? MatrixOperation.multiply(m, n) : null;
        if (ret != null) {
            System.out.println("The multiplication result is:");
            System.out.println(ret.toString());
        }
    }

    /**
    * Read two matrices from the user and them together, if the matrices don't have the same dimensions, then exit
    *
    * @param in - Scanner to read input from
    */
    private static void addMatrices(Scanner in) {
        Matrix m = createMatrix(in, "first");
        Matrix n = createMatrix(in, "second");
        Matrix ret = m.getCols() == n.getCols() && m.getRows() == n.getRows() ? MatrixOperation.add(m, n) : null;
        if (ret != null) {
            System.out.println("The addition result is:");
            System.out.println(ret.toString());
        }
    }

    /**
    * Take a user inputted matrix (M) and constant value (c) and do the scalar multiplication => c*M
    *
    * @param in - Scanner to read input from
    */
    private static void scalarMultiply(Scanner in) {
        Matrix m = createMatrix(in, "");
        int c = 0;
        // Read constant from user
        boolean hasEnteredConstant = false;
        while (!hasEnteredConstant) {
            try {
                System.out.print("Enter constant: ");
                c = Integer.parseInt(in.nextLine());
                hasEnteredConstant = true;
            } catch (NumberFormatException e) {
                System.out.println("Error parsing numbers.");
            }
        }
        Matrix ret = MatrixOperation.scalarMultiply(m, c);
        System.out.println("The result is:");
        System.out.println(ret.toString());
    }
}
