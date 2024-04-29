package by.quantumquartet.quanthink.testJava;

import by.quantumquartet.quanthink.math.Matrix;
import by.quantumquartet.quanthink.math.MatrixOperations;
import org.junit.Assert;
import org.junit.Test;
import org.junit.BeforeClass;


public class TestMatrixOperations {

    static Matrix matrix1;
    static Matrix matrix2;
    static Matrix matrixSum;
    static Matrix matrixSub;
    static Matrix matrixMul;
    static Matrix transposedMatrix;

    static Matrix NonReversedMatrix;
    static Matrix ReversedMatrix;
    static Matrix A;
    static Matrix f;

    @BeforeClass
    public static void setUpBeforeTest(){
        double[][] data1 = new double[][]{{1, -5, 14, 15},
                {5, -16, 11, 14},
                {-1, 0 , 17, 20},
                {5, -6, -3, 1}};
        double[][] data2 = new double[][]{{7, -15, 4, -9},
                {-7, -6, 9, 19},
                {-5, 5 , 9, -2},
                {8, 1, -3, 0}};
        double[][] dataSum = new double[][]{{8, -20, 18, 6},
                {-2, -22, 20, 33},
                {-6, 5, 26, 18},
                {13, -5, -6, 1}};
        double[][] dataSub = new double[][]{{-6, 10, 10, 24},
                {12, -10, 2, -5},
                {4, -5, 8, 22},
                {-3, -7, 0, 1}};
        double[][] dataMul = new double[][]{{92, 100, 40, -132},
                {204, 90, -67, -371},
                {68, 120, 89, -25},
                {100, -53, -64, -153}};
        double[][] dataTransposed = new double[][]{{1, 5, -1, 5},
                {-5, -16, 0, -6},
                {14, 11 , 17, -3},
                {15, 14, 20, 1}};

        double[][] dataNonReversed = new double[][]{{1, 2, 1},
                {2, 5, 6},
                {3, 1, 2}};
        double[][] dataReversed = new double[][]{{4.0/19.0, -3.0/19.0, 7.0/19.0},
                {14.0/19.0, -1.0/19.0, -4.0/19.0},
                {-13.0/19.0, 5.0/19.0, 1.0/19.0}};
        double[][] dataSystemA = new double[][]{{3, 2, 1, 1},
                {1, -1, 4, -1},
                {-2, -2 , -3,  1},
                {1, 5, -1 , 2}};
        double[][] dataSystemf = new double[][]{{-2}, {-1}, {9},{4}};
        matrix1 = new Matrix(4,4, data1);
        matrix2 = new Matrix(4,4, data2);
        matrixSum = new Matrix(4,4, dataSum);
        matrixSub = new Matrix(4,4, dataSub);
        matrixMul = new Matrix(4,4, dataMul);
        transposedMatrix = new Matrix(4,4, dataTransposed);
        NonReversedMatrix = new Matrix(3,3, dataNonReversed);
        ReversedMatrix = new Matrix(3,3, dataReversed);
        A = new Matrix(4,4, dataSystemA);
        f = new Matrix(4,1, dataSystemf);
    }

    @Test
    public void TestMatrixSum() {
        int[] size = matrixSum.getSize();
        Matrix actual = MatrixOperations.MatrixSum(matrix1, matrix2, 4);
        for (int i = 0; i < size[0]; i++)
            for (int j = 0; j < size[1]; j++)
                Assert.assertEquals(matrixSum.getElememnt(i, j), actual.getElememnt(i, j), 0);
    }

    @Test
    public void TestMatrixSub() {
        int[] size = matrixSub.getSize();
        Matrix actual = MatrixOperations.MatrixSub(matrix1, matrix2, 2);
        for (int i = 0; i < size[0]; i++)
            for (int j = 0; j < size[1]; j++)
                Assert.assertEquals(matrixSub.getElememnt(i, j), actual.getElememnt(i, j), 0);
    }

    @Test
    public void TestMatrixMul() {
        int[] size = matrixMul.getSize();
        Matrix actual = MatrixOperations.MatrixMul(matrix1, matrix2, 4);
        for (int i = 0; i < size[0]; i++)
            for (int j = 0; j < size[1]; j++)
                Assert.assertEquals(matrixMul.getElememnt(i, j), actual.getElememnt(i, j), 0);
    }

    @Test
    public void TestMatrixMulNum() {
        int[] size = matrix1.getSize();
        Matrix actual = MatrixOperations.MatrixMul(matrix1, 5, 4);
        for (int i = 0; i < size[0]; i++)
            for (int j = 0; j < size[1]; j++)
                Assert.assertEquals(matrix1.getElememnt(i, j) * 5, actual.getElememnt(i, j), 0);
    }

    @Test
    public void TestMatrixTranspose() {
        int[] size = transposedMatrix.getSize();
        Matrix actual = MatrixOperations.GetTransposeMatrix(matrix1);
        for (int i = 0; i < size[0]; i++)
            for (int j = 0; j < size[1]; j++)
                Assert.assertEquals(transposedMatrix.getElememnt(i, j), actual.getElememnt(i, j), 0);
    }

    @Test
    public void TestMatrixGetDeterminant() {
        for (int i = 1; i < 8; i++) {
            double det = MatrixOperations.GetDeterminant(matrix1, i);
            Assert.assertEquals(-1392.0, det, 0);
        }
    }

    @Test
    public void TestGetReversedMatrix() {
        int[] size = NonReversedMatrix.getSize();
        Matrix actual = MatrixOperations.GetReverseMatrix(NonReversedMatrix);
        for (int i = 0; i < size[0]; i++)
            for (int j = 0; j < size[1]; j++)
                Assert.assertEquals(ReversedMatrix.getElememnt(i, j), actual.getElememnt(i, j), 1e-8);
    }

    @Test
    public void TestSolveSystem() {
        String actual = MatrixOperations.SolveSystem(A,f ,4);
        Assert.assertEquals("-3 -1 2 7", actual);
    }
}
