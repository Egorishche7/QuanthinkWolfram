package by.quantumquartet.quanthink;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import java.lang.Math;
import static by.quantumquartet.quanthink.math.BasicArithmetic.SolveExpression;
public class TestBasicArithmetic {

    private Object[] convertor(String expr){
        int delimiter = expr.indexOf("|");
        return new Object[]{expr.substring(delimiter + 1), expr.substring(0, delimiter)};
    }

    @ParameterizedTest
    @ValueSource(strings = {"2+2|4",
            "-3+4|1",
            "0+0|0",
            (Integer.MAX_VALUE + Integer.MIN_VALUE)+"|-1",
            "-1+-1|-2",
            "4+-7|-3",
            (Integer.MAX_VALUE + Integer.MIN_VALUE)+"|-1",
            "(-3)+(-4)|-7",
            "(-10)+-21|-31",
            "0+-0|0",
            "+4+8|12",
            "(15+100)|115",
            "((-16)+(+17))|1"
    })
    public void testSolveExpressionSimpleSum(String expr) {
        Object[] data = convertor(expr);
        Assertions.assertEquals(data[0], SolveExpression((String)data[1]));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "99-45|54",
            "10-17|-7",
            "-15-99|-114",
            "-12-(-14)|2",
            (Integer.MAX_VALUE -(-1-Integer.MIN_VALUE))+"|0",
            "-(33-18)|-15",
            "100-(-15)|115",
            "-(-(-50-(-15)))|-35",
            "100--99|199",
            "0-0|0",
            Integer.MAX_VALUE + "-0|" + Integer.MAX_VALUE,
            Integer.MIN_VALUE + "-0|" + Integer.MIN_VALUE,
            Integer.MAX_VALUE +"-1000000000|" + (Integer.MAX_VALUE - 1_000_000_000),
            "-(-15)-(-60)|75",
    })
    public void testSolveExpressionSimpleSub(String expr) {
        Object[] data = convertor(expr);
        Assertions.assertEquals(data[0], SolveExpression((String)data[1]));
    }

    @ParameterizedTest
    @ValueSource(strings = {"5*9|45",
            "-5*9|-45", "5*-9|-45", "-5*-9|45", "(-5)*9|-45",
            "5*(-9)|-45", "(-5)*(-9)|45", "5*9|45", "5*0|0",
            "6*-0|0", "7*+0|0", "8*(-0)|0", "7*(+0)|0",
            "0*5|0", "-0*6|0", "+0*7|0", "(-0)*8|0",
            "(+0)*7|0",
            "0*0|0",
            Integer.MIN_VALUE + "*1|" + Integer.MIN_VALUE ,
            Integer.MIN_VALUE + "*0|0",
            Integer.MAX_VALUE + "*1|" + Integer.MAX_VALUE ,
            Integer.MAX_VALUE + "*0|0",
            })
    public void testSolveExpressionSimpleMul(String expr) {
        Object[] data = convertor(expr);
        Assertions.assertEquals(data[0], SolveExpression((String)data[1]));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "44/2|22",
            "11/1|11",
            "16/-4|-4",
            "30/(-5)|-6",
            "-5/2|-2.5",
            "(-15)/4|-3.75",
            "(-100)/(-25)|4",
            "-70/-5|14",
            "(-14)/-4|3.5",
            "-20/-4|5",
            "-20/(-5)|4",
            "11/14|"+(11.0/14.0),
            Integer.MIN_VALUE + "/1|" + Integer.MIN_VALUE ,
            Integer.MAX_VALUE + "/1|" + Integer.MAX_VALUE ,
            Integer.MAX_VALUE + "/-1|" + (-Integer.MAX_VALUE),
            })
    public void testSolveExpressionSimpleDel(String expr) {
        Object[] data = convertor(expr);
        Assertions.assertEquals(data[0], SolveExpression((String)data[1]));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "2^4|16",
            "3^-3|"+(1.0/27.0),
            "1^1|1",
            "1^100|1",
            "1^0|1",
            "1^(-0)|1",
            "1^(+0)|1",
            "1^-0|1",
            "1^+0|1",
            "0^0|1",
    })
    public void testSolveExpressionSimplePow(String expr) {
        Object[] data = convertor(expr);
        Assertions.assertEquals(data[0], SolveExpression((String)data[1]));
    }

    @ParameterizedTest
    @ValueSource(strings = {"e^2|"+(Math.E * Math.E),
            "2*π|"+(2*Math.PI)})
    public void testSolveExpressionConstants(String expr) {
        Object[] data = convertor(expr);
        Assertions.assertEquals(data[0], SolveExpression((String)data[1]));
    }

    @ParameterizedTest
    @ValueSource(strings = {"(2*5^3-1000)/0.3|"+(int)((2*125-1000)/0.3)
    })
    public void testSolveExpressionComplex(String expr) {
        Object[] data = convertor(expr);
        Assertions.assertEquals(data[0], SolveExpression((String)data[1]));
    }
}
