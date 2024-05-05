package by.quantumquartet.quanthink.cmath;

public class NativeMath {
    static {
        System.loadLibrary("nativeMathLib");
    }

    public static native String solveExpressionC(String expr);

    public static native String solveEquationC(String expr);

    //с++ не поддерживает многие символы ASCII, поэтому нужна заглушка
    public static String solveEquationCStub(String expr){
        String tmp = replacePI(expr);
        tmp = solveEquationC(tmp);
        tmp = replaceHashTag(tmp);
        return tmp;
    }

    //с++ не поддерживает многие символы ASCII, поэтому нужна заглушка
    public static String solveExpressionCStub(String expr){
        String tmp = replacePI(expr);
        tmp = solveExpressionC(tmp);
        return tmp;
    }

    private static String replacePI(String expr){
        String tmp = expr;
        int previous = -1;
        while(true){
            int index = tmp.indexOf("π", previous + 1);
            if(index == -1)
                break;
            tmp = tmp.substring(0, index) + "p" + tmp.substring(index + 1);
            previous = index;
        }
        return tmp;
    }

    private static String replaceHashTag(String expr){
        String tmp = expr;
        int previous = -1;
        while(true){
            int index = tmp.indexOf("#", previous + 1);
            if(index == -1)
                break;
            tmp = tmp.substring(0, index) + "±" + tmp.substring(index + 1);
            previous = index;
        }
        return tmp;
    }

}
