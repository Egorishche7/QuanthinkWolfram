package by.quantumquartet.quanthink.testCPlusPlus;

import by.quantumquartet.quanthink.cmath.MatrixC;
import by.quantumquartet.quanthink.cmath.NativeMath;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.annotation.Native;

public class TestMatrixOperationsC {

    static MatrixC matrix1;
    static MatrixC matrix2;
    static MatrixC matrixSum;
    static MatrixC matrixSub;
    static MatrixC matrixMul;
    static MatrixC transposedMatrix;

    static MatrixC NonReversedMatrix;
    static MatrixC ReversedMatrix;
    static MatrixC A;
    static MatrixC f;

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
        matrix1 = new MatrixC(4,4, data1);
        matrix2 = new MatrixC(4,4, data2);
        matrixSum = new MatrixC(4,4, dataSum);
        matrixSub = new MatrixC(4,4, dataSub);
        matrixMul = new MatrixC(4,4, dataMul);
        transposedMatrix = new MatrixC(4,4, dataTransposed);
        NonReversedMatrix = new MatrixC(3,3, dataNonReversed);
        ReversedMatrix = new MatrixC(3,3, dataReversed);
        A = new MatrixC(4,4, dataSystemA);
        f = new MatrixC(4,1, dataSystemf);
    }

    @Test
    public void TestMatrixSum() {
        int[] size = matrixSum.getSize();
        MatrixC actual = NativeMath.MatrixSumC(matrix1, matrix2, 3).getMatrixResult();
        for (int i = 0; i < size[0]; i++)
            for (int j = 0; j < size[1]; j++)
                Assert.assertEquals(matrixSum.getElememnt(i, j), actual.getElememnt(i, j), 0);
    }

    @Test
    public void TestMatrixSub() {
        int[] size = matrixSub.getSize();
        MatrixC actual = NativeMath.MatrixSubC(matrix1, matrix2, 2).getMatrixResult();
        for (int i = 0; i < size[0]; i++)
            for (int j = 0; j < size[1]; j++)
                Assert.assertEquals(matrixSub.getElememnt(i, j), actual.getElememnt(i, j), 0);
    }

    @Test
    public void TestMatrixMul() {
        int[] size = matrixMul.getSize();
        MatrixC actual = NativeMath.MatrixMulC(matrix1, matrix2, 3).getMatrixResult();
        for (int i = 0; i < size[0]; i++)
            for (int j = 0; j < size[1]; j++)
                Assert.assertEquals(matrixMul.getElememnt(i, j), actual.getElememnt(i, j), 0);
    }

    @Test
    public void TestMatrixMulNum() {
        int[] size = matrix1.getSize();
        MatrixC actual = NativeMath.MatrixMulC(matrix1, 5, 4).getMatrixResult();
        for (int i = 0; i < size[0]; i++)
            for (int j = 0; j < size[1]; j++)
                Assert.assertEquals(matrix1.getElememnt(i, j) * 5, actual.getElememnt(i, j), 0);
    }

    @Test
    public void TestMatrixTranspose() {
        int[] size = transposedMatrix.getSize();
        MatrixC actual = NativeMath.GetTransposeMatrixC(matrix1).getMatrixResult();
        for (int i = 0; i < size[0]; i++)
            for (int j = 0; j < size[1]; j++)
                Assert.assertEquals(transposedMatrix.getElememnt(i, j), actual.getElememnt(i, j), 0);
    }

    @Test
    public void TestMatrixGetDeterminant() {
        for (int i = 1; i < 8; i++) {
            double det = NativeMath.GetDeterminantC(matrix1, i).getDoubleResult();
            Assert.assertEquals(-1392.0, det, 0);
        }
    }

    @Test
    public void TestGetReversedMatrix() {
        int[] size = NonReversedMatrix.getSize();
        MatrixC actual = NativeMath.GetReverseMatrixC(NonReversedMatrix).getMatrixResult();
        for (int i = 0; i < size[0]; i++)
            for (int j = 0; j < size[1]; j++)
                Assert.assertEquals(ReversedMatrix.getElememnt(i, j), actual.getElememnt(i, j), 1e-8);
    }

    @Test
    public void TestSolveSystem() {
        String actual = NativeMath.SolveSystemC(A,f ,4).getStringResult();
        Assert.assertEquals("-3 -1 2 7", actual);
    }
}
