package by.quantumquartet.quanthink;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.lang.Math;
import static by.quantumquartet.quanthink.math.BasicArithmetic.SolveExpression;
public class TestBasicArithmetic {

    private Object[] convertor(String expr){
        int delimiter = expr.indexOf("|");
        return new Object[]{expr.substring(delimiter + 1), expr.substring(0, delimiter)};
    }

    @ParameterizedTest
    @ValueSource(strings = {"2+2|4",
            "5*9|45",
            "99-45|54",
            "44/2|22",
            "2^4|16",
            "14*(2-3)|-14"})
    public void testSolveExpressionSimple(String expr) {
        int delimiter = expr.indexOf("|");
        Object[] data = convertor(expr);
        Assert.assertEquals((String)data[0],SolveExpression((String)data[1]));
    }

    @ParameterizedTest
    @ValueSource(strings = {"e^2|"+(Math.E * Math.E),
            "2*π|"+(2*Math.PI)})
    public void testSolveExpressionConstants(String expr) {
        int delimiter = expr.indexOf("|");
        Object[] data = convertor(expr);
        Assert.assertEquals((String)data[0],SolveExpression((String)data[1]));
    }

    @ParameterizedTest
    @ValueSource(strings = {"(2*5^3-1000)/0.3|"+(int)((2*125-1000)/0.3)
    })
    public void testSolveExpressionComplex(String expr) {
        int delimiter = expr.indexOf("|");
        Object[] data = convertor(expr);
        Assert.assertEquals((String)data[0],SolveExpression((String)data[1]));
    }
}
