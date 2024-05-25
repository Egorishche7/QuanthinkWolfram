package by.quantumquartet.quanthink.testCPlusPlus;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static by.quantumquartet.quanthink.cmath.NativeMath.solveEquationCStub;

class TestEquationsC {

    @ParameterizedTest
    @CsvSource({
            "x+5, -5",
            "2*x-9, 4.500000",
            "-3.0x, 0",
            "6+1.5*x, -4",
            "x, 0",
            "-x-11.1, -11.100000",
            "x*5+35, -7",
    })
    void TestSolveLinearEquations(String equation, String answer) {
        Assertions.assertEquals(answer, solveEquationCStub(equation).getStringResult());
    }


    @ParameterizedTest
    @CsvSource({
            "x^2-6x+5, 1 5",
            "3x^2+7x-6, -3 0.666667",
            "-x^2+7x+8, -1 8",
            "4x^2+4x+1, -0.500000",
            "2x^2+x+1, -0.250000±0.661438i",
            "-x^2+2x+8, -2 4",
            "9x^2-6x+1, 0.333333",
            "x^2+3x+2, -2 -1",
            "x^2-5x+6, 2 3",
    })
    void TestSolveSquareEquations(String equation, String answer) {
        Assertions.assertEquals(answer, solveEquationCStub(equation).getStringResult());
    }

    @ParameterizedTest
    @CsvSource({
            "x^3+8, -2 1.000000±1.732051i",
            "7x^3+3x^2-x-9, 1 -0.714286±0.880631i",
            "4.5x^3-3x^2-0.5x+7, -1 0.833333±0.927961i",
            "8x^3+12x^2+6x-26, 1 -1.250000±1.299038i",
            "8x^3+12x^2+6x+28, -2 0.250000±1.299038i",
            "x^3-7x-12, 3.266975 -1.633487±1.002418i",
            "2x^3+5x^2-4x+1, -3.178678 0.339339±0.205298i",
            "x^2-6x-8+x^3, -2 -1.561553 2.561553",
            "-x^2+x+x^3-1, 1 0.000000±1.000000i",
            "-9x^2+x^3+28x-24, 1.365635 3.817183±1.733021i",
            "x^3+24x-20-9x^2, 2 5",
            "5x^3-7x+3x^2+1, -1.571711 0.155998 0.815713",
            "-x^2+2x^3-x+6, -1.396451 0.948226±1.117663i",
            "x^3+x^2+4-4x, -2.875130 0.937565±0.715691i",
            "-10x+4x^3-3x^2+5, -1.490309 0.475179 1.765130",
            "x^3+9x+7x^2-1, -5.249141 -1.853635 0.102775",
            "x^3-5x^2+x-12, 5.245484 -0.122742±1.507520i",
            "6x^3-8x^2+3-2x, -0.601701 0.643308 1.291726",
            "7x^3-3+5x^2-6x, -1.136662 -0.438153 0.860530",
            "-4+3x^3+6x^2-5x, -2.457427 -0.542573 1",

    })
    void TestSolveCubeEquations(String equation, String answer) {
        Assertions.assertEquals(answer, solveEquationCStub(equation).getStringResult());
    }

    @ParameterizedTest
    @CsvSource({
            "x^4-7x^2+12, -2 -1.732051 1.732051 2",
            "x^4+2x^3-9x^2-20x+24, 0.929035 2.754676 -2.841856±1.140972i",
            "x^4-x^3+x^2-x, 0 1 0.000000±1.000000i",
            "x^4+x^3+x^2+x, -1 0 0.000000±1.000000i",
            "x^4-x^2+x-6, -1.828172 1.627857 0.100157±1.416367i",
            "x^4-x^3+3x^2-x, 0 0.361103 0.319448±1.633170i",
            "x^4-x^3+x^2-x+1, -0.309017±0.951057i 0.809017±0.587785i",
            "x^4-x^2-x+15, -1.479302±1.362840i 1.479302±1.232613i",
            "x^4-2x^3+x^2-x, 0 1.754878 0.122561±0.744862i",
    })
    void TestSolveQuadraticEquations(String equation, String answer) {
        Assertions.assertEquals(answer, solveEquationCStub(equation).getStringResult());
    }

    @ParameterizedTest
    @CsvSource({
            "x^5-10x^3+25x^2-30x+12, -4.216285 0.698598 1.826392 0.845647±1.231066i",
            "x^5+3x^4+7x^3+5x^2+8x+6, -0.812444 -1.373117±1.870912i 0.279339±1.137190i",
            "x^5+x^4-2x^3-x^2+x-1, 1.217736 -1.418784±0.219166i 0.309916±0.549910i",
            "x^5-x-1, 1.167304 -0.764884±0.352472i 0.181232±1.083954i",
            "x^5-6x^4+11x^3-10x^2+5x, 0 1.381966 3.618034 0.500000±0.866025i",
            "x^5-8x^4+26x^3-40x^2+32x, 0 2.956145±1.383551i 2.956145±1.383551i",
            "x^5-10x^4+35x^3-60x^2+60x, 0 3.505692 4.656585 0.918861±1.682599i",
            "x^5-12x^4+54x^3-96x^2+96x, 0 1.087965±1.444600i 4.912035±2.285801i",
            "x^5-14x^4+84x^3-168x^2+196x-128, 1.409218 0.520414±1.207931i 5.774977±4.376647i",
            "x^5-15x^4+105x^3-225x^2+315x-243, 1.444527 0.509549±1.461173i 6.268188±5.564002i",
    })
    void TestSolvePentaEquations(String equation, String answer) {
        Assertions.assertEquals(answer, solveEquationCStub(equation).getStringResult());
    }

}
