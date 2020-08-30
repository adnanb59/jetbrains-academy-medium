package processor;

public class MatrixOperation {
    /**
    * Take the two matrices passed in to function and return their sum.
    * 
    * @param m - First matrix
    * @param n - Second matrix
    * @return M + N
    */
    public static Matrix add (Matrix m, Matrix n) {
        if (m.getRows() != n.getRows() || m.getCols() != n.getCols()) return null; // If dimensions aren't equal, return null
        Matrix res = new Matrix(m.getRows(), m.getCols());
        for (int i = 0; i < m.getRows(); i++) {
            for (int j = 0; j < m.getCols(); j++) {
                res.setValue(m.getValue(i, j) + n.getValue(i, j), i, j);
            }
        }
        return res;
    }

    /**
    * Take the matrix passed in and multiply it's values by the constant passed in and return the new matrix.
    * 
    * @param m - Matrix
    * @param c - Constant to multiply matrix with
    * @return c*M as new matrix
    */
    public static Matrix scalarMultiply(Matrix m, double c) {
        Matrix res = new Matrix(m.getRows(), m.getCols());
        for (int i = 0; i < m.getRows(); i++) {
            for (int j = 0; j < m.getCols(); j++) {
                res.setValue(m.getValue(i, j) * c, i, j);
            }
        }
        return res;
    }

    /**
    * Take two matrices, multiply them together and return the result.
    * If the matrices are incompatible (in terms of multiplication - C_M != R_N), then result is null.
    * 
    * @param m - First matrix
    * @param n - Second matrix
    * @return m*n (as new matrix)
    */
    public static Matrix multiply(Matrix m, Matrix n) {
        if (m.getCols() != n.getRows()) return null; // if M's # of cols != N's # of rows, return null
        Matrix res = new Matrix(m.getRows(), n.getCols());
        for (int i = 0; i < m.getRows(); i++) {
            for (int j = 0; j < n.getCols(); j++) {
                float dotProduct = 0;
                for (int k = 0; k < m.getCols(); k++) {
                    dotProduct += m.getValue(i, k) * n.getValue(k, j);
                }
                res.setValue(dotProduct, i, j);
            }
        }
        return res;
    }

    /**
    * Transpose matrix along horizontal line along middle of matrix.
    * Same as "reflecting" matrix along x-axis
    * 
    * @param m - Matrix to transpose
    * @return Transposed matrix
    */
    public static Matrix transposeHorizontal(Matrix m) {
        Matrix ret = new Matrix(m.getRows(), m.getCols());
        int R = m.getRows(), C = m.getCols();
        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                ret.setValue(m.getValue(i, j), R-i-1, j);
            }
        }
        return ret;
    }

    /**
    * Transpose matrix along main diagonal (traditional meaning of transpose).
    * 
    * @param m - Matrix to transpose
    * @return Transposed matrix
    */
    public static Matrix transposeMain(Matrix m) {
        Matrix ret = new Matrix(m.getCols(), m.getRows());
        for (int i = 0; i < m.getRows(); i++) {
            for (int j = 0; j < m.getCols(); j++) {
                ret.setValue(m.getValue(i, j), j, i);
            }
        }
        return ret;
    }

    /**
    * Transpose matrix along vertical line along the middle of the matrix.
    * Same as "reflecting" matrix along y-axis.
    * 
    * @param m - Matrix to transpose
    * @return Transposed matrix
    */
    public static Matrix transposeVertical(Matrix m) {
        Matrix ret = new Matrix(m.getRows(), m.getCols());
        int R = m.getRows(), C = m.getCols();
        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                ret.setValue(m.getValue(i, j), i, C-j-1);
            }
        }
        return ret;
    }

    /**
    * Transpose matrix along opposite diagonal.
    * 
    * @param m - Matrix to transpose
    * @return Transposed matrix
    */
    public static Matrix transposeSide(Matrix m) {
        Matrix ret = new Matrix(m.getCols(), m.getRows());
        int R = m.getRows(), C = m.getCols();
        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                ret.setValue(m.getValue(i, j), C-j-1, R-i-1);
            }
        }
        return ret;
    }

    /**
    * Calculate determinant of matrix passed in for square matrices with size >= 0.
    * 
    * @param m - Matrix to calculate determinant for
    * @return Det(m)
    */
    public static double determinant(Matrix m) {
        // First three are base cases for determinant calculation
        if (m.getRows() == 0) return 0.0;
        else if (m.getRows() == 1) return m.getValue(0, 0);
        else if (m.getRows() == 2) return m.getValue(0, 0)*m.getValue(1, 1) - m.getValue(0, 1)*m.getValue(1, 0);
        else {
            // Recursively calculate determinant by calculating cofactors of elements along top row
            double result = 0.0;
            for (int i = 0; i < m.getCols(); i++) {
                result += Math.pow(-1, i)*m.getValue(0, i)*determinant(m.subMatrix(0, i)); // double check number
            }
            return result;
        }
    }

    /**
    * Invert matrix passed in and return result, if det(M) = 0, return null
    * A matrix's (A) inverse (B) is another matrix such that AB = BA = I, another way to write B is A^(-1).
    * Matrix is calculated by transposing the cofactor matrix and multiplying it by 1/det(A).
    * 
    * @param m - Matrix to calculate inverse for
    * @return Inverted matrix
    */
    public static Matrix inverse(Matrix m) {
        double det = determinant(m);
        if (det == 0.0) return null;
        Matrix cofactor = new Matrix(m.getRows(), m.getCols());
        for (int i = 0; i < m.getRows(); i++) {
            for (int j = 0; j < m.getCols(); j++) {
                // Calculate cofactors
                cofactor.setValue(Math.pow(-1, i+j)*determinant(m.subMatrix(i, j)), i, j);
            }
        }
        // Transpose matrix (along main diagonal) and scalar multiply by 1/det
        return scalarMultiply(transposeMain(cofactor),1/det);
    }
}
