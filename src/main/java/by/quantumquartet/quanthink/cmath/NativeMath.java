package by.quantumquartet.quanthink.cmath;

public class NativeMath {
    static {
        System.loadLibrary("nativeMathLib");
    }

    public static native String solveExpressionC(String expr);

    public static native String solveEquationC(String expr);
}
