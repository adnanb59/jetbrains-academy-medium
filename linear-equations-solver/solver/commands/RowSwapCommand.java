package solver.commands;

import solver.elements.*;
import java.util.*;

public class RowSwapCommand implements Command {
    private List<LinearEquation> m;
    private int a, b;

    public RowSwapCommand(List<LinearEquation> eqns) {
        m = eqns;
    }

    public void setRows(int a, int b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public void execute() {
        Collections.swap(m, a, b);
    }
}
