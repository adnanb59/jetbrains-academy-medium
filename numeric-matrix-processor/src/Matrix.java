public class Matrix {
    private final int rows, cols;
    private final double[][] matrix;

    public Matrix (int r, int c) {
        rows = r;
        cols = c;
        matrix = new double[r][c];
    }

    public Matrix subMatrix(int row, int col) {
        Matrix m = new Matrix(rows-1, cols-1);
        for (int i = 0; i < rows; i++) {
            if (i == row) continue;
            for (int j = 0; j < cols; j++) {
                if (j != col) m.matrix[i < row ? i : i - 1][j < col ? j : j - 1] = matrix[i][j];
            }
        }
        return m;
    }

    public void setValue(double v, int r, int c) {
        if (r < 0 || r >= rows || c < 0 || c >= cols) return;
        matrix[r][c] = v;
    }

    public Double getValue(int r, int c) {
        if (r < 0 || r >= rows || c < 0 || c >= cols) return null;
        return matrix[r][c];
    }


    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                sb.append(matrix[i][j] == -0.0 ? 0 : String.format("%.2f", matrix[i][j]));
                sb.append(j == cols - 1 ? "\n" : "\t");
            }
        }
        return sb.toString();
    }
}
