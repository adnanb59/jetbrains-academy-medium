package solver.commands;

import solver.elements.*;

public class RowReduceCommand implements Command {
    private LinearEquation a, b;
    private int idx;
    private double factor;

    public void setModifiedEqn(LinearEquation l) {
        a = l;
    }

    public void setModifierEqn(LinearEquation l) {
        b = l;
    }

    public void setPosition(int p) {
        idx = p;
    }

    public double getFactor() {
        return factor;
    }

    @Override
    public void execute() {
        factor = a.modifyEquation(idx, b);
    }
}