package solver.commands;

import solver.elements.*;
import java.util.List;

public class ColumnSwapCommand implements Command {
    private List<LinearEquation> m;
    private int a, b;

    public ColumnSwapCommand(List<LinearEquation> eqns) {
        m = eqns;
    }

    public void setColumns(int a, int b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public void execute() {
        for (LinearEquation e : m) {
            double temp = e.getCoefficient(a);
            e.setCoefficient(a, e.getCoefficient(b));
            e.setCoefficient(b, temp);
        }
    }
}
