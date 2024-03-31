package by.quantumquartet.quanthink;

import by.quantumquartet.quanthink.math.Equations;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static by.quantumquartet.quanthink.math.Equations.SolveEquation;
public class TestEquations {

    @ParameterizedTest
    @CsvSource({
            "x+5, -5",
            "2*x-9, 4.5",
            "-3.0x, 0",
            "6+1.5*x, -4",
            "x, 0",
            "-x-11.1, -11.1",
            "x*5+35, -7",

    })
    public void testSolveLinearEquations(String equation, String answer) {
        Assertions.assertEquals(answer, SolveEquation(equation));
    }


    @ParameterizedTest
    @CsvSource({
            "x^2-6x+5, 1 5",
    })
    public void testSolveSquareEquations(String equation, String answer) {
        Assertions.assertEquals(answer, SolveEquation(equation));
    }
//            "x^2-6x+5, 1 5",
//            "x^2-2x+1, 1",
//
//            "x^3-3x^2+3, -0.879385242 1.347296355 2.532088885",
//            " "
//    @ParameterizedTest
//    @CsvSource({
//            "x^2-2+99, No roots found",
//    })
//    public void testSolveEquationsExceptions(double leftBorder, double rightBorder, String expected) {
//        Exception exception = assertThrows(Exception.class, () -> {
//            nonLinearEquation.solve(leftBorder, rightBorder);
//        });
//        assertEquals(expected, exception.getMessage());
//    }
}
