package by.quantumquartet.quanthink.testJava;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import java.lang.Math;

import static by.quantumquartet.quanthink.math.BasicArithmetic.solveExpression;
public class TestBasicArithmetic {

    private Object[] convertor(String expr){
        int delimiter = expr.indexOf("=");
        return new Object[]{expr.substring(delimiter + 1), expr.substring(0, delimiter)};
    }

    @ParameterizedTest
    @ValueSource(strings = {"=0",
            "2+2=4",
            "-3+4=1",
            "0+0=0",
            (Integer.MAX_VALUE + Integer.MIN_VALUE)+"=-1",
            "-1+-1=-2",
            "4+-7=-3",
            "1+-2.=-1",
            (Integer.MAX_VALUE + Integer.MIN_VALUE)+"=-1",
            "(-3)+(-4)=-7",
            "(-10)+-21=-31",
            "0+-0=0",
            "+4+8=12",
            "(15+100)=115",
            "((-16)+(+17))=1"
    })
    public void testSolveExpressionSimpleSum(String expr) {
        Object[] data = convertor(expr);
        Assertions.assertEquals(data[0], solveExpression((String)data[1]));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "99-45=54",
            "10-17=-7",
            "-15-99=-114",
            "-12-(-14)=2",
            (Integer.MAX_VALUE -(-1-Integer.MIN_VALUE))+"=0",
            "-(33-18)=-15",
            "100-(-15)=115",
            "-(-(-50-(-15)))=-35",
            "100--99=199",
            "0-0=0",
            ".123-.063=0.06",
            Integer.MAX_VALUE + "-0=" + Integer.MAX_VALUE,
            Integer.MIN_VALUE + "-0=" + Integer.MIN_VALUE,
            Integer.MAX_VALUE +"-1000000000=" + (Integer.MAX_VALUE - 1_000_000_000),
            "-(-15)-(-60)=75",
    })
    public void testSolveExpressionSimpleSub(String expr) {
        Object[] data = convertor(expr);
        Assertions.assertEquals(data[0], solveExpression((String)data[1]));
    }

    @ParameterizedTest
    @ValueSource(strings = {"5*9=45",
            "-5*9=-45", "5*-9=-45", "-5*-9=45", "(-5)*9=-45",
            "5*(-9)=-45", "(-5)*(-9)=45", "5*9=45", "5*0=0",
            "6*-0=0", "7*+0=0", "8*(-0)=0", "7*(+0)=0",
            "0*5=0", "-0*6=0", "+0*7=0", "(-0)*8=0",
            "(+0)*7=0",
            "3(7)=21",
            "(7)4=28",
            "3(-7)=-21",
            "3(-7)5+9(2)-4(2)(-4)=-55",
            "(-7)4=-28",
            "(+2)*+7=14",
            "0*0=0",
            "2*9.=18",
            Integer.MIN_VALUE + "*1=" + Integer.MIN_VALUE ,
            Integer.MIN_VALUE + "*0=0",
            Integer.MAX_VALUE + "*1=" + Integer.MAX_VALUE ,
            Integer.MAX_VALUE + "*0=0",
            })
    public void testSolveExpressionSimpleMul(String expr) {
        Object[] data = convertor(expr);
        Assertions.assertEquals(data[0], solveExpression((String)data[1]));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "44/2=22",
            "11/1=11",
            "16/-4=-4",
            "30/(-5)=-6",
            "-5/2=-2.5",
            "(-15)/4=-3.75",
            "(-100)/(-25)=4",
            "-70/-5=14",
            "(-14)/-4=3.5",
            "-20/-4=5",
            "-20/(-5)=4",
            "11/14="+(11.0/14.0),
            Integer.MIN_VALUE + "/1=" + Integer.MIN_VALUE ,
            Integer.MAX_VALUE + "/1=" + Integer.MAX_VALUE ,
            Integer.MAX_VALUE + "/-1=" + (-Integer.MAX_VALUE),
            })
    public void testSolveExpressionSimpleDel(String expr) {
        Object[] data = convertor(expr);
        Assertions.assertEquals(data[0], solveExpression((String)data[1]));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "2^4=16",
            "3^-3="+(1.0/27.0),
            "1^1=1",
            "1^100=1",
            "1^0=1",
            "1^(-0)=1",
            "1^(+0)=1",
            "1^-0=1",
            "1^+0=1",
            "0^0=1",
            "3^-1="+(1.0/3.0),
            "25^0.5="+5,
            "216^(1.0/3.0)="+6,
            "256^0.25=4",
            "625^-(1/4)=0.2",
            "2^(-2)=0.25",
            "2^(3.0)=8",
            "(-2)^(3.0)=-8"
    })
    public void testSolveExpressionSimplePow(String expr) {
        Object[] data = convertor(expr);
        Assertions.assertEquals(data[0], solveExpression((String)data[1]));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "e^2="+(Math.E * Math.E),
            "2*π="+(2*Math.PI),
            "e+3-π*0.5="+(Math.E + 3.0 - Math.PI * 0.5),
            "4/3*π*5^3="+(4.0 / 3.0 * Math.PI * 125.0),
            "=0"
    })
    public void testSolveExpressionConstants(String expr) {
        Object[] data = convertor(expr);
        Assertions.assertEquals(data[0], solveExpression((String)data[1]));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "(2*5^3-1000)/0.3="+(int)((2*125-1000)/0.3),
            "12/14=0.8571428571428571",
            "45/93+33*29+67*45+60*100-80-72/69=9891.440392706872",
            "((4/5)*(7/2))+((3/2)-(1/4))-((1/3)+(1/6))="+(((4.0/5.0)*(7.0/2.0))+((3.0/2.0)-(1.0/4.0))-((1.0/3.0)+(1.0/6.0))),
            "(6/11)/((2/3.)^(1/2))-(5/6)=-0.16529067621064852",
            "(5/3)*((6/5)+(2/5))/(2/3)-((4/7)*(2/3.))=3.61904761904762",
            "(9/2)-((7/3)-(4/5))*((5/8)+(1/8))/(1./2)=2.1999999999999997",
            "(4/3)^(3/2)-(3/5)^(5/3)-4+3^(5/3)/5^(3/2)=-2.3290814194308016",
            "(16/3)*((3/4.)+(1/4))+(4/(2*2))=6.333333333333333",
            "(25/2)-((6/3)-(2/6))*(3/1)=7.5",
            "(72/9)/(4/1)+(3/2)*((9/5)-(5/7))=3.628571428571429",
            "(8/3)^2-(6/4)^2=4.861111111111111",
            "((11/3)-(5/4))^2+(6/1)=11.840277777777777",
            "(3*4)+(6-1)-(2+1)=14",
            "32/(8*(2^2))-6=-5",
            "5*(6+2)/3-(4*2)=5.333333333333334",
            "9-(7-4)*(5+1)/2=0",
            "4^3-3^2=55",
            "16*(3+1)+4/2^2=65",
            "25-(6-2)*3=13",
            "72/4+3*(9-5)=30",
            "8^2-6^2=28",
            "(11-5)^2-6=30",
            "12+(11-13)^3-6=-2",
            "12-(11-13)^3-6=14",
    })
    public void testSolveExpressionComplex(String expr) {
        Object[] data = convertor(expr);
        Assertions.assertEquals(data[0], solveExpression((String)data[1]));
    }


    @ParameterizedTest
    @ValueSource(strings = {
            "4+2-3/0=Can't divide by zero",
            "1-5^(11/0.0)=Can't divide by zero",
            "5^100=Stack overflow",
            "2147459123+7459123=Stack overflow",
            "-2147459123-13312573=Stack overflow",
            "-1000000*1000000=Stack overflow",
            "999999/0.00000001=Stack overflow",
            "(20-1=Numbers of left and right brackets must be equal",
            "(20+11)-1*25)=Numbers of left and right brackets must be equal",
            "4^^3-3^2=Error",
            "^3-3^2=Error",
            "66^=Error",
            "(3/*4)+(6-1)-(2+1)=Error",
            "32/(*(2^2))-6=Error",
            "(3/4)+(6-^1)-(2+1)=Error",
            "(4/^4)+(6-^1)-(2**+1)=Error",
            "(4../4)+(6-1)-(2*+1)=Error",
            "(4+4)+(6-1)-(2*.....+1)=Error",
    })
    public void testSolveExpressionExceptions(String expr) {
        Object[] data = convertor(expr);
        try{
            solveExpression((String)data[1]);
        }
        catch (ArithmeticException | StackOverflowError | IllegalArgumentException ex){
            Assertions.assertEquals(data[0], ex.getMessage());
        }

    }
}
