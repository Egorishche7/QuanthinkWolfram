package by.quantumquartet.quanthink.controllers;

import static by.quantumquartet.quanthink.services.AppLogger.logError;

import by.quantumquartet.quanthink.math.Matrix;
import by.quantumquartet.quanthink.rest.requests.calculations.*;
import by.quantumquartet.quanthink.rest.responses.ErrorResponse;
import by.quantumquartet.quanthink.rest.responses.SuccessResponse;
import by.quantumquartet.quanthink.rest.responses.calculations.CalculationResponse;
import by.quantumquartet.quanthink.services.CalculationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/calculations")
public class CalculationController {
    private final CalculationService calculationService;

    @Autowired
    public CalculationController(CalculationService calculationService) {
        this.calculationService = calculationService;
    }

    @GetMapping
    public ResponseEntity<?> getAllCalculations() {
        List<CalculationResponse> calculations = calculationService.getAllCalculations();
        if (calculations.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("No calculations found"));
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(new SuccessResponse<>("Calculations retrieved successfully", calculations));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCalculationById(@PathVariable("id") long id) {
        Optional<CalculationResponse> calculationData = calculationService.getCalculationById(id);
        if (calculationData.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("Calculation not found"));
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(new SuccessResponse<>("Calculation retrieved successfully", calculationData.get()));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getCalculationsByUserId(@PathVariable("userId") long userId) {
        List<CalculationResponse> calculations = calculationService.getCalculationsByUserId(userId);
        if (calculations.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("No calculations found"));
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(new SuccessResponse<>("User calculations retrieved successfully", calculations));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCalculation(@PathVariable("id") long id) {
        Optional<CalculationResponse> calculationData = calculationService.getCalculationById(id);
        if (calculationData.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("Calculation not found"));
        }
        try {
            calculationService.deleteCalculation(id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new SuccessResponse<>("Calculation deleted successfully", id));
        } catch (Exception e) {
            logError(CalculationController.class, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Internal server error"));
        }
    }

    @DeleteMapping("/user/{userId}")
    public ResponseEntity<?> deleteCalculationsByUserId(@PathVariable("userId") long userId) {
        List<CalculationResponse> calculations = calculationService.getCalculationsByUserId(userId);
        if (calculations.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("No calculations found"));
        }
        try {
            calculationService.deleteCalculationsByUserId(userId);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new SuccessResponse<>("User calculations deleted successfully", userId));
        } catch (Exception e) {
            logError(CalculationController.class, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Internal server error"));
        }
    }

    @PostMapping("/basicArithmetic")
    public ResponseEntity<?> solveBasicArithmetic(@Valid @RequestBody BasicArithmeticRequest basicArithmeticRequest) {
        try {
            String result = calculationService.solveBasicArithmetic(basicArithmeticRequest);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new SuccessResponse<>("Calculation performed successfully", result));
        } catch (IllegalArgumentException e) {
            logError(CalculationController.class, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            logError(CalculationController.class, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Internal server error"));
        }
    }

    @PostMapping("/equation")
    public ResponseEntity<?> solveEquation(@Valid @RequestBody EquationRequest equationRequest) {
        try {
            String result = calculationService.solveEquation(equationRequest);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new SuccessResponse<>("Calculation performed successfully", result));
        } catch (IllegalArgumentException e) {
            logError(CalculationController.class, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            logError(CalculationController.class, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Internal server error"));
        }
    }

    @PostMapping("/matrixSum")
    public ResponseEntity<?> solveMatrixSum(@Valid @RequestBody MatrixSumRequest matrixSumRequest) {
        try {
            Matrix result = calculationService.solveMatrixSum(matrixSumRequest);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new SuccessResponse<>("Calculation performed successfully", result));
        } catch (IllegalArgumentException e) {
            logError(CalculationController.class, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            logError(CalculationController.class, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Internal server error"));
        }
    }

    @PostMapping("/matrixSub")
    public ResponseEntity<?> solveMatrixSub(@Valid @RequestBody MatrixSubRequest matrixSubRequest) {
        try {
            Matrix result = calculationService.solveMatrixSub(matrixSubRequest);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new SuccessResponse<>("Calculation performed successfully", result));
        } catch (IllegalArgumentException e) {
            logError(CalculationController.class, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            logError(CalculationController.class, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Internal server error"));
        }
    }

    @PostMapping("/matrixMul")
    public ResponseEntity<?> solveMatrixMul(@Valid @RequestBody MatrixMulRequest matrixMulRequest) {
        try {
            Matrix result = calculationService.solveMatrixMul(matrixMulRequest);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new SuccessResponse<>("Calculation performed successfully", result));
        } catch (IllegalArgumentException e) {
            logError(CalculationController.class, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            logError(CalculationController.class, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Internal server error"));
        }
    }

    @PostMapping("/matrixMulByNum")
    public ResponseEntity<?> solveMatrixMulByNum(@Valid @RequestBody MatrixMulByNumRequest matrixMulByNumRequest) {
        try {
            Matrix result = calculationService.solveMatrixMulByNum(matrixMulByNumRequest);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new SuccessResponse<>("Calculation performed successfully", result));
        } catch (IllegalArgumentException e) {
            logError(CalculationController.class, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            logError(CalculationController.class, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Internal server error"));
        }
    }

    @PostMapping("/matrixTranspose")
    public ResponseEntity<?> solveMatrixTranspose(@Valid @RequestBody MatrixTransposeRequest matrixTransposeRequest) {
        try {
            Matrix result = calculationService.solveMatrixTranspose(matrixTransposeRequest);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new SuccessResponse<>("Calculation performed successfully", result));
        } catch (IllegalArgumentException e) {
            logError(CalculationController.class, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            logError(CalculationController.class, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Internal server error"));
        }
    }

    @PostMapping("/matrixReverse")
    public ResponseEntity<?> solveMatrixReverse(@Valid @RequestBody MatrixReverseRequest matrixReverseRequest) {
        try {
            Matrix result = calculationService.solveMatrixReverse(matrixReverseRequest);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new SuccessResponse<>("Calculation performed successfully", result));
        } catch (IllegalArgumentException e) {
            logError(CalculationController.class, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            logError(CalculationController.class, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Internal server error"));
        }
    }

    @PostMapping("/matrixDeterminant")
    public ResponseEntity<?> solveMatrixDeterminant(@Valid @RequestBody MatrixDeterminantRequest
                                                                matrixDeterminantRequest) {
        try {
            double result = calculationService.solveMatrixDeterminant(matrixDeterminantRequest);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new SuccessResponse<>("Calculation performed successfully", result));
        } catch (IllegalArgumentException e) {
            logError(CalculationController.class, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            logError(CalculationController.class, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Internal server error"));
        }
    }

    @PostMapping("/matrixSystem")
    public ResponseEntity<?> solveSystem(@Valid @RequestBody MatrixSystemRequest matrixSystemRequest) {
        try {
            String result = calculationService.solveSystem(matrixSystemRequest);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new SuccessResponse<>("Calculation performed successfully", result));
        } catch (IllegalArgumentException e) {
            logError(CalculationController.class, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            logError(CalculationController.class, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Internal server error"));
        }
    }
}
