package by.quantumquartet.quanthink.math;

import static by.quantumquartet.quanthink.math.MatrixOperations.determinant;
import static by.quantumquartet.quanthink.math.MatrixOperations.generateSubArray;

public class ComputingThreads {
    public static class CountDeterminantTask implements Runnable {
        int i;
        double[][] A;
        int N;
        double Result;

        public CountDeterminantTask(double[][] _A, int _N, int _i) {
            this.A = _A;
            this.N = _N;
            this.i = _i;
        }

        @Override
        public void run() {
            double[][] subArray = generateSubArray(A, N, i);
            Result = Math.pow(-1.0, 1.0 + i + 1.0) * A[0][i] * determinant(subArray, N - 1);
        }
    }
}
