package processor;

public class Matrix {
    private final int rows, cols;
    private final double[][] matrix;
    
    /**
    * Matrix constructor
    *
    * @param r - rows of matrix
    * @param c - columns of matrix
    */
    public Matrix (int r, int c) {
        rows = r;
        cols = c;
        matrix = new double[r][c];
    }

    /**
    * Given current matrix, create a submatrix with one less column and row (specified by user).
    * 
    * 
    * @param row - index for row to omit from submatrix
    * @param col - index for column to omit from submatrix
    * @return new Matrix object representing submatrix
    */
    public Matrix subMatrix(int row, int col) {
        Matrix m = new Matrix(rows-1, cols-1);
        for (int i = 0; i < rows; i++) {
            if (i == row) continue; // Skip current row
            for (int j = 0; j < cols; j++) {
                // For every other column than the one specified, calculate coordinates for submatrix to map
                if (j != col) m.matrix[i < row ? i : i - 1][j < col ? j : j - 1] = matrix[i][j];
            }
        }
        return m;
    }

    /**
    * Set value of matrix at position (r,c) to v
    * 
    * @param v - value to put into matrix
    * @param r - row of coordinate to enter v
    * @param c - column of coordinate to enter v
    */
    public void setValue(double v, int r, int c) {
        if (r < 0 || r >= rows || c < 0 || c >= cols) return;
        matrix[r][c] = v;
    }

    /**
    * Get value from matrix at position (r,c)
    * 
    * @param r - row of coordinate to read value from
    * @param c - column of cooridnate to read value from
    * @return value of matrix at specified coordinates
    */
    public Double getValue(int r, int c) {
        if (r < 0 || r >= rows || c < 0 || c >= cols) return null;
        return matrix[r][c];
    }

    /**
    * Get number of rows in the matrix
    * 
    * @return number of rows in the matrix
    */
    public int getRows() {
        return rows;
    }

    /**
    * Get number of columns in the matrix
    * 
    * @return number of columns in matrix
    */
    public int getCols() {
        return cols;
    }

    /** String representation of matrix */
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
