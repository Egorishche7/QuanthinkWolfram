package by.quantumquartet.quanthink;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static by.quantumquartet.quanthink.math.BasicArithmetic.SolveExpression;
public class TestBasicArithmetic {

    private Object[] convertor(String expr){
        int delimiter = expr.indexOf("|");
        return new Object[]{expr.substring(delimiter + 1), expr.substring(0, delimiter)};
    }

    @ParameterizedTest
    @ValueSource(strings = {"2+2|4", "5*9|45", "99-45|54", "44/2|22", "2*(5-1)|8","3+((14-2)*2)|27"})
    public void testSolveExpressionSimple(String expr) {
        int delimiter = expr.indexOf("|");
        Object[] data = convertor(expr);
        Assert.assertEquals((String)data[0],SolveExpression((String)data[1]));
    }
}
