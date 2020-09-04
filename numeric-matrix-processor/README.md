## numeric-matrix-processor

This application allows the user to perform different operations on (linear algebra) matrices.

These include:
- Addition
- Scalar Multiplication
- Matrix Multiplication
- Matrix Transposition
  - along the main diagonal (traditional transpose)
  - along side diagonal
  - vertical line (same as flipping vertically)
  - horizontal line (flipping horizontally)
- Calculating Determinant
- Inverting matrices

#### Running program
After compiling, run `java Runner` to start program.

The user will be prompted with a menu in which they can choose to do a variety of matrix operations.

```
1. Add matrices
2. Multiply matrix to a constant
3. Multiply matrices
4. Transpose matrix
5. Calculate a determinant
6. Inverse matrix
0. Exit
Your choice: 
```

You will be further prompted to create a matrix (or two for add/multiply) as follows:
`Enter size of matrix:`, where you are to enter 2 numbers for the dimensions.

Followed by:
`Enter matrix:`, where you will enter N rows of M space-separated numbers.

> e.g. 3x3 matrix
> ```
> 1 2 3
> 4 5 6
> 7 8 9
> ```

Depending on the operation, you will be prompted to enter a constant or, in the case of transposition, be prompted with another menu for choice of tranpose.

All outputs will be displayed to console.

##### URL: https://hyperskill.org/projects/60
