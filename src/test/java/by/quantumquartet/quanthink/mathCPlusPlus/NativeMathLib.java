package by.quantumquartet.quanthink.mathCPlusPlus;

public class NativeMathLib {
    static {
        System.loadLibrary("BasicArithmeticCPlusPlus");
    }

    public static native String solveExpressionC(String expr);
}
