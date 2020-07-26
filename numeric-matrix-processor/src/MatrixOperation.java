public class MatrixOperation {
    public static Matrix add (Matrix m, Matrix n) {
        if (m.getRows() != n.getRows() || m.getCols() != n.getCols()) return null;
        Matrix res = new Matrix(m.getRows(), m.getCols());
        for (int i = 0; i < m.getRows(); i++) {
            for (int j = 0; j < m.getCols(); j++) {
                res.setValue(m.getValue(i, j) + n.getValue(i, j), i, j);
            }
        }
        return res;
    }

    public static Matrix scalarMultiply(Matrix m, double c) {
        Matrix res = new Matrix(m.getRows(), m.getCols());
        for (int i = 0; i < m.getRows(); i++) {
            for (int j = 0; j < m.getCols(); j++) {
                res.setValue(m.getValue(i, j) * c, i, j);
            }
        }
        return res;
    }

    public static Matrix multiply(Matrix m, Matrix n) {
        if (m.getCols() != n.getRows()) return null;
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

    public static Matrix transposeMain(Matrix m) {
        Matrix ret = new Matrix(m.getCols(), m.getRows());
        for (int i = 0; i < m.getRows(); i++) {
            for (int j = 0; j < m.getCols(); j++) {
                ret.setValue(m.getValue(i, j), j, i);
            }
        }
        return ret;
    }

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

    public static double determinant(Matrix m) {
        if (m.getRows() == 0) return 0.0;
        else if (m.getRows() == 1) return m.getValue(0, 0);
        else if (m.getRows() == 2) return m.getValue(0, 0)*m.getValue(1, 1) - m.getValue(0, 1)*m.getValue(1, 0);
        else {
            double result = 0.0;
            for (int i = 0; i < m.getCols(); i++) {
                result += Math.pow(-1, i)*m.getValue(0, i)*determinant(m.subMatrix(0, i)); // double check number
            }
            return result;
        }
    }

    public static Matrix inverse(Matrix m) {
        double det = determinant(m);
        if (det == 0.0) return null;
        Matrix cofactor = new Matrix(m.getRows(), m.getCols());
        for (int i = 0; i < m.getRows(); i++) {
            for (int j = 0; j < m.getCols(); j++) {
                cofactor.setValue(Math.pow(-1, i+j)*determinant(m.subMatrix(i, j)), i, j);
            }
        }
        return scalarMultiply(transposeMain(cofactor),1/det);
    }
}
