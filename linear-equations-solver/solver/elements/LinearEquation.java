package solver.elements;

import java.util.*;

public class LinearEquation {
    private List<Double> coefficients;
    private Double solution;
    private Double sum;
    private int size;

    /**
    * LinearEquation constructor
    *
    * @param size - specified size of linear equation
    */
    public LinearEquation(int size) {
        coefficients = new ArrayList<>();
        this.size = size;
        sum = 0.0;
    }

    /**
    * Add variable to the linear equation.
    *
    * @param value - Value of coefficient being inserted
    */
    public void addCoefficient(double value) {
        if (coefficients.size() < size) {
            coefficients.add(value);
            sum += value;
        }
    }

    /**
    * Set the value of the specified coefficient in the linear equation.
    *
    * @param idx - Coefficient to update
    * @param v - New value of coefficient
    */
    public void setCoefficient(int idx, double v) {
        if (idx < size) {
            double temp = coefficients.get(idx);
            coefficients.set(idx, v);
            sum += (v - temp);
        }
    }

    /**
    * Get value of i'th coefficient.
    *
    * @param position - position to get coefficient from
    * @return position'th coefficient or null (if it doesn't exist)
    */
    public Double getCoefficient(int position) {
        if (position >= size) return null;
        return this.coefficients.get(position);
    }

    /**
    * Get the number of the variables in the equation.
    *
    * @return Get number of variables in the equation
    */
    public int getNumOfVariables() { return size; }

    /**
    * Add the solution to the equation (or override current)
    *
    * @param value - New solution for equation
    */
    public void addSolution(double value) { solution = value; }

    /**
    * Get the solution for the linear equation.
    *
    * @return Solution for equation (or null if none specified)
    */
    public Double getSolution() { return isEquationPossible() ? solution : null; }

    /**
    * Figure out if this equation is not contradictory, in that
    * the sum of the coefficients is not zero or the solution is zero.
    *
    * @return Whether or not the current equation is contradictory or makes sense
    */
    public boolean isEquationPossible() {
        System.out.println(this.solution + " " + this.sum + "\n " + toString());
        return this.solution == 0.0 || this.sum != 0.0;
    }

    /** Reduce row by the inverse of the coefficient specified in parameter.
    *
    * @param idx - specific coefficient to reduce equation by
    * @return factor that reduces coefficients & solution of equation
    */
    public double factorEquation(int idx) {
        Double factor = coefficients.get(idx);
        for (int i = 0; i < size; i++) coefficients.set(i, coefficients.get(i)/factor);
        solution /= factor;
        sum /= factor;
        return factor;
    }

    /** Reduce row by factor of the specified linear equation's coefficient (specified by position).
    *
    * @param position - Specific coefficient to get
    * @param b - Equation to get coefficient from
    * @return factor reducing equation (coefficients & solution)
    */
    public double modifyEquation(int position, LinearEquation b) {
        double factor = position < size ? coefficients.get(position) : 0;
        if (factor != 0) {
            factor = coefficients.get(position);
            factor /= b.getCoefficient(position);
            solution -= factor*b.getSolution();
            for (int i = position; i < size; i++) {
                coefficients.set(i, coefficients.get(i) - factor*b.getCoefficient(i));
                sum -= factor*b.getCoefficient(i);
            }
        }

        return factor;
    }
}