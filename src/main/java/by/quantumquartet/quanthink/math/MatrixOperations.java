package by.quantumquartet.quanthink.math;

import java.util.stream.DoubleStream;

public class MatrixOperations {

    public static Matrix MatrixSum(Matrix m1, Matrix m2, int threads){
        int[] size1 = m1.getSize();
        int[] size2 = m2.getSize();
        for (int i = 0; i < size2.length; i++)
            if (size1[i] != size2[i])
                throw new IllegalArgumentException("Invalid matrixes sizes");
        if (threads > 1){

            return new Matrix(1,1, new double[][]{{0}});
        }
        else {
            double[][] new_data = new double[size1[0]][size1[1]];
            for (int i = 0; i < size2[0]; i++)
                for (int j = 0; j < size2[1]; j++)
                    new_data[i][j] = m1.getElememnt(i,j) + m2.getElememnt(i, j);
            return new Matrix(m1.getSize()[0], m1.getSize()[1], new_data);
        }
    }

    public static Matrix MatrixSub(Matrix m1, Matrix m2, int threads){
        int[] size1 = m1.getSize();
        int[] size2 = m2.getSize();
        for (int i = 0; i < size2.length; i++)
            if (size1[i] != size2[i])
                throw new IllegalArgumentException("Invalid matrixes sizes");
        if (threads > 1){
            return new Matrix(1,1, new double[][]{{0}});
        }
        else {
            double[][] res_data = new double[size1[0]][size1[1]];
            for (int i = 0; i < size2[0]; i++)
                for (int j = 0; j < size2[1]; j++)
                    res_data[i][j] = m1.getElememnt(i,j) - m2.getElememnt(i, j);
            return new Matrix(m1.getSize()[0], m1.getSize()[1], res_data);
        }
    }

    public static Matrix MatrixMul(Matrix m1, Matrix m2, int threads){
        int[] size1 = m1.getSize();
        int[] size2 = m2.getSize();
        if (size1[1] != size2[0])
            throw new IllegalArgumentException("Invalid matrixes sizes");
        if (threads > 1){
            return new Matrix(1,1, new double[][]{{0}});
        }
        else {
            double[][] res_data = new double[size1[0]][size1[1]];
            for (int i = 0; i < size2[0]; i++){
                double[] row = m1.getRow(i);
                for (int j = 0; j < size2[1]; j++) {
                    double [] column = m2.getColumn(j);
                    double sum = 0;
                    for (int q = 0; q < column.length; q++) {
                        sum += row[q] * column[q];
                    }
                    res_data[i][j] = sum;
                }
            }

            return new Matrix(size1[0], size1[1], res_data);
        }
    }

    public static Matrix MatrixMul(Matrix m, double num, int threads){
        if (threads > 1){
            return new Matrix(1,1, new double[][]{{0}});
        }
        else {
            double[][] res_data = new double[m.getSize()[0]][m.getSize()[1]];
            for (int i = 0; i < m.getSize()[0]; i++)
                for (int j = 0; j < m.getSize()[1]; j++)
                    res_data[i][j] = m.getElememnt(i,j) * num;
            return new Matrix(m.getSize()[0], m.getSize()[1], res_data);
        }
    }

    public static Matrix GetTransposeMatrix(Matrix m){
        int[] size = m.getSize();
        double[][] res_data = new double[size[1]][size[0]];
        for (int i = 0 ; i < size[1]; i++){
            res_data[i] = m.getColumn(i);
        }
        return new Matrix(size[1], size[0], res_data);
    }

    public static Matrix GetReverseMatrix(Matrix m){
        return new Matrix(1,1, new double[][]{{0}});
    }

    public static double GetDeterminant(Matrix m){
        return 0;
    }

    public static String SolveSystem(Matrix A, Matrix f){
        return " ";
    }
}
