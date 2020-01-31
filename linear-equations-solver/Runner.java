import java.io.*;
import java.util.*;
import solver.elements.*;

public class Main {
    public static void main(String[] args) {
        // -- ARGUMENT PROCESSING --
        String inFile = null;
        String outFile = null;
        boolean hasError = false;
        for (int i = 0; i < args.length && !hasError; i+=2) {
            switch(args[i]) {
                case "-in":
                    hasError = inFile != null || i >= args.length-1;
                    if (!hasError) inFile = args[i+1];
                    break;
                case "-out":
                    hasError = outFile != null || i >= args.length-1;
                    if (!hasError) outFile = args[i+1];
                    break;
                default:
                    hasError = true;
            }
        }

        // If there's any errors, don't proceed any further
        if (hasError || inFile == null || outFile == null) {
            System.err.println("Error. Correct input: java Runner -in <filename> -out <filename>");
            System.exit(1);
        }

        // -- FILE INPUT AND OUTPUT PROCESSING --
        File f = new File(inFile);
        try (Scanner in = new Scanner(f); FileWriter fw = new FileWriter(outFile)) {
            int eqns, vars;
            if (in.hasNextInt()) vars = in.nextInt();
            else throw new IllegalArgumentException("Valid number of coefficients not provided");
            if (in.hasNextInt()) eqns = in.nextInt();
            else throw new IllegalArgumentException("Valid number of equations not provided");

            // Create Matrix and for each input line processed, create a linear equation
            // and then store the created equation in the matrix
            Matrix m = new Matrix();
            int lines = 0;
            LinearEquation l;
            while (in.hasNextLine() && lines < eqns) {
                // For each input line processed, create a new equation
                l = new LinearEquation(vars);
                for (int i = 0; i <= vars; i++) {
                    if (in.hasNextDouble()) {
                        if (i == vars) l.addSolution(in.nextDouble());
                        else l.addCoefficient(in.nextDouble());
                    } else throw new IllegalArgumentException("Not enough coefficients provided for equation");
                }
                m.addEquation(l);
                lines++;
            }

            // If there weren't enough equations provided as specified by user, throw error
            if (lines != eqns) throw new IllegalArgumentException("Not enough equations provided");
            else {
                System.out.println("Start solving the equation.");
                m.solve(); // FINALLY, start solving the matrix
            }

            // After solving matrix, display solution
            switch (m.getSolutionState()) {
                case NO_SOLUTION:
                    System.out.println("No solutions");
                    fw.write("No solutions");
                    break;
                case ONE_SOLUTION:
                    System.out.print("The solution is: (");
                    List<Double> solns = m.getSolutions();
                    for (int i = 0; i < solns.size(); i++) {
                        System.out.print(solns.get(i));
                        fw.write(solns.get(i).toString());
                        if (i < solns.size()-1) {
                            System.out.print(", ");
                            fw.write("\n");
                        }
                    }
                    System.out.println(").");
                    break;
                case INFINITE_SOLUTIONS:
                    System.out.println("Infinitely many solutions");
                    fw.write("Infinitely many solutions");
                    break;
            }
            System.out.println("Saved to file " + outFile);
        } catch (FileNotFoundException e) {
            System.err.println("Error opening open file to read");
        } catch (IOException e) {
            System.err.println("Error opening file to write results");
        } catch (NumberFormatException e) {
            System.err.println("Error converting value passed by user");
        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}