package by.quantumquartet.quanthink;

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
        matrix1 = new Matrix(4,4, data1);
        matrix2 = new Matrix(4,4, data2);
        matrixSum = new Matrix(4,4, dataSum);
        matrixSub = new Matrix(4,4, dataSub);
        matrixMul = new Matrix(4,4, dataMul);
        transposedMatrix = new Matrix(4,4, dataTransposed);
    }

    @Test
    public void TestMatrixSum() {
        int[] size = matrixSum.getSize();
        Matrix actual = MatrixOperations.MatrixSum(matrix1, matrix2, 1);
        for (int i = 0; i < size[0]; i++)
            for (int j = 0; j < size[1]; j++)
                Assert.assertEquals(matrixSum.getElememnt(i, j), actual.getElememnt(i, j), 0);
    }

    @Test
    public void TestMatrixSub() {
        int[] size = matrixSub.getSize();
        Matrix actual = MatrixOperations.MatrixSub(matrix1, matrix2, 1);
        for (int i = 0; i < size[0]; i++)
            for (int j = 0; j < size[1]; j++)
                Assert.assertEquals(matrixSub.getElememnt(i, j), actual.getElememnt(i, j), 0);
    }

    @Test
    public void TestMatrixMul() {
        int[] size = matrixMul.getSize();
        Matrix actual = MatrixOperations.MatrixMul(matrix1, matrix2, 1);
        for (int i = 0; i < size[0]; i++)
            for (int j = 0; j < size[1]; j++)
                Assert.assertEquals(matrixMul.getElememnt(i, j), actual.getElememnt(i, j), 0);
    }

    @Test
    public void TestMatrixMulNum() {
        int[] size = matrix1.getSize();
        Matrix actual = MatrixOperations.MatrixMul(matrix1, 5, 1);
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
}
