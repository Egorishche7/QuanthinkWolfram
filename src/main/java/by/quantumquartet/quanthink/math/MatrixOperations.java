package by.quantumquartet.quanthink.math;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
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
    public static double GetDeterminant(Matrix m, int n){
        if (m.getSize()[0] != m.getSize()[1]){
            throw new IllegalArgumentException("Matrix must be square!");
        }
        else{
            Double res;
            if (n == 1)
            {
                res = 0.0;
                for (int i = 0; i < m.getSize()[0]; i++){
                    double[][] subArray = generateSubArray (m.getData(), m.getSize()[0], i);
                    res += Math.pow(-1.0, 1.0 + i + 1.0) * m.getElememnt(0,i)
                            * determinant(subArray, m.getSize()[0] - 1);
                }
            }
            else {
                res = 0.0;
                ExecutorService executorService = Executors.newFixedThreadPool(n);
                List<ComputingThreads.CountDeterminantTask> tasks = new ArrayList<>(m.getSize()[0]);

                for (int i = 0; i < m.getSize()[0]; i ++) {
                    ComputingThreads.CountDeterminantTask task = new ComputingThreads.CountDeterminantTask(m.getData(),
                            m.getSize()[0], i);
                    tasks.add(task);
                    executorService.execute(task);
                }
                executorService.shutdown();
                try {
                    executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                for (ComputingThreads.CountDeterminantTask task: tasks) {
                    res += task.Result;
                }

            }
            return res;
        }

    }

    public static String SolveSystem(Matrix A, Matrix f){
        return " ";
    }



    static double determinant(double A[][], int n){
        double res;

        if (n == 1)
            res = A[0][0];
        else if (n == 2)
            res = A[0][0]*A[1][1] - A[1][0]*A[0][1];
        else{
            res = 0;
            for (int i = 0; i < n; i++){
                double[][] subArray = generateSubArray (A, n, i);
                res += Math.pow(-1.0, 1.0 + i + 1.0) * A[0][i] * determinant(subArray, n - 1);
            }
        }
        return res;
    }

    static double[][] generateSubArray(double A[][], int n, int j1){
        double[][] m = new double[n-1][];
        for (int k=0; k < (n - 1); k++)
            m[k] = new double[n-1];

        for (int i = 1; i < n; i++){
            int j2 = 0;
            for (int j = 0; j < n; j++){
                if(j == j1)
                    continue;
                m[i-1][j2] = A[i][j];
                j2++;
            }
        }
        return m;
    }


}

