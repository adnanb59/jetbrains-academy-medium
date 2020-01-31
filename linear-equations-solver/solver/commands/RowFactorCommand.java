package solver.commands;

import solver.elements.*;

public class RowFactorCommand implements Command {
    private LinearEquation a;
    private int index;
    private double factor;

    public void setModifiedEqn(LinearEquation l) {
        a = l;
    }

    public void setModifiedIndex(int idx) {
        index = idx;
    }

    public double getFactor() {
        return factor;
    }

    @Override
    public void execute() {
        factor = a.factorEquation(index);
    }
}
