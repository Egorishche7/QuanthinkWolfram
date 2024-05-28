package by.quantumquartet.quanthink.rest.requests.calculations;

import by.quantumquartet.quanthink.math.Matrix;
import by.quantumquartet.quanthink.models.calculations.ELibrary;

public class MatrixReverseRequest extends CalculationRequest {
    private Matrix matrix;

    public MatrixReverseRequest(long userId, ELibrary library, Matrix matrix) {
        super(userId, library);
        this.matrix = matrix;
    }

    public Matrix getMatrix() {
        return matrix;
    }

    public void setMatrix(Matrix matrix) {
        this.matrix = matrix;
    }
}
