import java.io.*;
import java.util.*;

class Matrix {
    private int size;
    private List<List<Double>> eqns;
    private List<Double> solns;

    public Matrix(int size) {
        this.size = size;
        eqns = new ArrayList<List<Double>>();
        for (int i = 0; i < size; i++) {
            eqns.add(Arrays.asList(new Double[size]));
        }
        solns = Arrays.asList(new Double[size]);
    }

    public void addSolution(double value, int eqn) {
        solns.set(eqn, value);
    }

    public void addCoefficient(double value, int eqn, int variable) {
        eqns.get(eqn).set(variable, value);
    }

    public List<Double> getSolutions() {
        return solns;
    }

    public void solve() {
        int step = 1, i = 0;
        for (; step == 1 ? i < size : i >= 0; i += step) {
            if (eqns.get(i).get(i) == 0) continue;

            if (eqns.get(i).get(i) != 1) {
                double factor = eqns.get(i).get(i);
                for (int j = i; j < size; j++) eqns.get(i).set(j, eqns.get(i).get(j)/factor);
                solns.set(i, solns.get(i)/factor);
                System.out.println((1/factor) + " * R" + (i+1) + " -> R" + (i+1));
            }

            for (int j = i + step; step == 1 ? j < size : j >= 0; j += step) {
                if (eqns.get(j).get(i) == 0) continue;
                double factor = eqns.get(j).get(i);
                if (step == -1) eqns.get(j).set(i, eqns.get(j).get(i) - factor);
                else for (int k = i; k < size; k+=step) eqns.get(j).set(k, eqns.get(j).get(k) - factor*eqns.get(i).get(k));

                solns.set(j, solns.get(j) - factor*solns.get(i));
                System.out.println(-factor + " * R" + (i+1) + " + R" + (j+1) + " -> R" + (j+1));
            }

            if (i == size - 1 && step == 1) {
                i++;
                step = -1;
            }
        }
    }
}

public class Runner {
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

        if (hasError || inFile == null || outFile == null) {
            System.err.println("Error. Correct input: java Runner -in <filename> -out <filename>");
            System.exit(1);
        }

        File f = new File(inFile);
        Matrix m;
        try (Scanner in = new Scanner(f); FileWriter fw = new FileWriter(outFile)) {
            // pass
            Integer n;
            if (in.hasNextInt()) {
                n = in.nextInt();
                m = new Matrix(n);
                in.nextLine();
            } else {
                throw new IllegalArgumentException("No n value provided for matrix size");
            }

            int lines = 0;
            while (in.hasNextLine() && lines < n) {
                for (int i = 0; i <= n; i++) {
                    if (in.hasNextDouble()) {
                        if (i == n) m.addSolution(in.nextDouble(), lines);
                        else m.addCoefficient(in.nextDouble(), lines, i);
                    } else throw new IllegalArgumentException("Not enough coefficients provided for equation");
                }
                lines++;
            }

            if (lines != n) throw new IllegalArgumentException("Not enough equations provided");
            else {
                System.out.println("Start solving the equation.");
                System.out.println("Row manipulation:");
                m.solve();
            }

            List<Double> solns = m.getSolutions();
            System.out.print("The solution is: (");
            for (int i = 0; i < solns.size(); i++) {
                System.out.print(solns.get(i));
                if (i < solns.size()-1) System.out.print(", ");
            }
            System.out.println(").");
            for (int i = 0; i < solns.size(); i++) {
                fw.write(solns.get(i).toString());
                if (i < solns.size()-1) fw.write("\n");
            }
            System.out.println("Saved to file " + outFile);
        } catch (FileNotFoundException e) {
            System.err.println("Error opening open file to read");
        } catch (IOException e) {
            System.err.println("Error opening file to write results");
        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
