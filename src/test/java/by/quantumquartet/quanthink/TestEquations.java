package by.quantumquartet.quanthink;

import by.quantumquartet.quanthink.math.Equations;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static by.quantumquartet.quanthink.math.Equations.SolveEquation;

class TestEquations {

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
    void testSolveLinearEquations(String equation, String answer) {
        Assertions.assertEquals(answer, SolveEquation(equation));
    }


    @ParameterizedTest
    @CsvSource({
            "x^2-6x+5, 1 5",
            "3x^2+7x-6, -3 0.6666666666666666",
            "-x^2+7x+8, -1 8",
            "4x^2+4x+1, -0.5",
            "2x^2+x+1, -0.25±0.6614378277661477i",
            "-x^2+2x+8, -2 4",
            "9x^2-6x+1, 0.3333333333333333",
            "x^2+3x+2, -2 -1",
            "x^2-5x+6, 2 3",
    })
    void testSolveSquareEquations(String equation, String answer) {
        Assertions.assertEquals(answer, SolveEquation(equation));
    }

    @ParameterizedTest
    @CsvSource({
            "x^3+8, -2 1.0±1.7320508075688772i",
            "7x^3+3x^2-x-9, 1 -0.7142857142857142±0.8806305718527109i",
            "4.5x^3-3x^2-0.5x+7, -1 0.8333333333333334±0.9279607271383371i",
            "8x^3+12x^2+6x-26, 1 -1.25±1.299038105676658i",
            "8x^3+12x^2+6x+28, -2 0.25±1.299038105676658i",
            "x^3-7x-12, 3.2669746134015614 -1.6334873067007807±1.0024182477677213i",
            "2x^3+5x^2-4x+1, -3.178677812942944 0.3393389064714719±0.2052978633428067i",
            "x^2-6x-8+x^3, -2 -1.5615528128088305 2.56155281280883",
            "-x^2+x+x^3-1, 1 0.0±0.9999999999999999i",
            "-9x^2+x^3+28x-24, 1.3656347069864567 3.8171826465067715±1.7330211866147032i",
            "x^3+24x-20-9x^2, 2 5",
            "5x^3-7x+3x^2+1, -1.5717113770338489 0.15599825933436598 0.8157131176994825",
            "-x^2+2x^3-x+6, -1.3964514544926823 0.9482257272463411±1.1176629018578081i",
            "x^3+x^2+4-4x, -2.875129794162779 0.9375648970813895±0.7156909967859647i",
            "-10x+4x^3-3x^2+5, -1.4903088990173123 0.4751787119798226 1.7651301870374891",
            "x^3+9x+7x^2-1, -5.2491405381295495 -1.8536345109670924 0.10277504909664037",
            "x^3-5x^2+x-12, 5.245483955093902 -0.12274197754695093±1.5075200014016128i",
            "6x^3-8x^2+3-2x, -0.6017006814573194 0.6433084208496991 1.291725593940953",
            "7x^3-3+5x^2-6x, -1.1366619372322326 -0.4381533239482579 0.8605295468947758",
            "-4+3x^3+6x^2-5x, -2.457427107756338 -0.5425728922436623 1",

    })
    void testSolveCubeEquations(String equation, String answer) {
        Assertions.assertEquals(answer, SolveEquation(equation));
    }

    @ParameterizedTest
    @CsvSource({
            "x^4-7x^2+12, -2 -1.7320508075688776 1.7320508075688776 2",
            "x^4+2x^3-9x^2-20x+24, 0.9290353233824729 2.754676168907842 -2.8418557461451575±1.140972302783389i",
            "x^4-x^3+x^2-x, 0 1 0.0±1.0i",
            "x^4+x^3+x^2+x, -1 0 0.0±1.0i",
            "x^4-x^2+x-6, -1.8281719014085922 1.6278565879609501 0.10015765672382117±1.4163673048451426i",
            "x^4-x^3+3x^2-x, 0 0.36110308052864737 0.3194484597356767±1.6331702409152362i",
            "x^4-x^3+x^2-x+1, -0.30901699437494745±0.9510565162951539i 0.8090169943749475±0.5877852522924729i",
            "x^4-x^2-x+15, -1.4793019877548672±1.3628400473634645i 1.4793019877548672±1.2326133810945565i",
            "x^4-2x^3+x^2-x, 0 1.7548776662466927 0.1225611668766537±0.7448617666197442i",
    })
    void testSolveQuadraticEquations(String equation, String answer) {
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
