package by.quantumquartet.quanthink.controllers;

import static by.quantumquartet.quanthink.services.AppLogger.*;

import by.quantumquartet.quanthink.math.Matrix;
import by.quantumquartet.quanthink.rest.requests.calculations.*;
import by.quantumquartet.quanthink.rest.responses.ErrorResponse;
import by.quantumquartet.quanthink.rest.responses.SuccessResponse;
import by.quantumquartet.quanthink.rest.responses.calculations.CalculationDto;
import by.quantumquartet.quanthink.rest.responses.calculations.CalculationResult;
import by.quantumquartet.quanthink.services.CalculationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Calculations Management", description = "Endpoints for managing calculations")
public class CalculationController {
    private final CalculationService calculationService;

    @Autowired
    public CalculationController(CalculationService calculationService) {
        this.calculationService = calculationService;
    }

    @Operation(summary = "Get all calculations",
            description = "Retrieve a list of all calculations.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Calculations retrieved successfully",
                            content = @Content(schema = @Schema(implementation = SuccessResponse.class))),
                    @ApiResponse(responseCode = "404", description = "No calculations found",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            })
    @GetMapping
    public ResponseEntity<?> getAllCalculations() {
        List<CalculationDto> calculations = calculationService.getAllCalculations();
        if (calculations.isEmpty()) {
            logWarn(CalculationController.class, "No calculations found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("No calculations found"));
        }
        logInfo(CalculationController.class, "Calculations retrieved successfully");
        return ResponseEntity.status(HttpStatus.OK)
                .body(new SuccessResponse<>("Calculations retrieved successfully", calculations));
    }

    @Operation(summary = "Get calculation by ID",
            description = "Retrieve calculation information by calculation ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Calculation retrieved successfully",
                            content = @Content(schema = @Schema(implementation = SuccessResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Calculation not found",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            })
    @GetMapping("/{id}")
    public ResponseEntity<?> getCalculationById(@PathVariable("id") long id) {
        Optional<CalculationDto> calculationData = calculationService.getCalculationById(id);
        if (calculationData.isEmpty()) {
            logWarn(CalculationController.class, "Calculation with id = " + id + " not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("Calculation not found"));
        }
        logInfo(CalculationController.class, "Calculation with id = " + id + " retrieved successfully");
        return ResponseEntity.status(HttpStatus.OK)
                .body(new SuccessResponse<>("Calculation retrieved successfully", calculationData.get()));
    }

    @Operation(summary = "Get calculations by user ID",
            description = "Retrieve a list of calculations by user ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User calculations retrieved successfully",
                            content = @Content(schema = @Schema(implementation = SuccessResponse.class))),
                    @ApiResponse(responseCode = "404", description = "No calculations found for user",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            })
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getCalculationsByUserId(@PathVariable("userId") long userId) {
        List<CalculationDto> calculations = calculationService.getCalculationsByUserId(userId);
        if (calculations.isEmpty()) {
            logWarn(CalculationController.class, "No calculations found for user with id = " + userId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("No calculations found"));
        }
        logInfo(CalculationController.class,
                "Calculations for user with id = " + userId + " retrieved successfully");
        return ResponseEntity.status(HttpStatus.OK)
                .body(new SuccessResponse<>("User calculations retrieved successfully", calculations));
    }

    @Operation(summary = "Delete calculation",
            description = "Delete a calculation by ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Calculation deleted successfully",
                            content = @Content(schema = @Schema(implementation = SuccessResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Calculation not found",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCalculation(@PathVariable("id") long id) {
        Optional<CalculationDto> calculationData = calculationService.getCalculationById(id);
        if (calculationData.isEmpty()) {
            logWarn(CalculationController.class, "Calculation with id = " + id + " not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("Calculation not found"));
        }
        try {
            calculationService.deleteCalculation(id);
            logInfo(CalculationController.class, "Calculation with id = " + id + " deleted successfully");
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new SuccessResponse<>("Calculation deleted successfully", id));
        } catch (Exception e) {
            logError(CalculationController.class, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Internal server error"));
        }
    }

    @Operation(summary = "Delete calculations by user ID",
            description = "Delete all calculations by user ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User calculations deleted successfully",
                            content = @Content(schema = @Schema(implementation = SuccessResponse.class))),
                    @ApiResponse(responseCode = "404", description = "No calculations found for user",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            })
    @DeleteMapping("/user/{userId}")
    public ResponseEntity<?> deleteCalculationsByUserId(@PathVariable("userId") long userId) {
        List<CalculationDto> calculations = calculationService.getCalculationsByUserId(userId);
        if (calculations.isEmpty()) {
            logWarn(CalculationController.class, "No calculations found for user with id = " + userId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("No calculations found"));
        }
        try {
            calculationService.deleteCalculationsByUserId(userId);
            logInfo(CalculationController.class,
                    "Calculations for user with id = " + userId + " deleted successfully");
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new SuccessResponse<>("User calculations deleted successfully", userId));
        } catch (Exception e) {
            logError(CalculationController.class, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Internal server error"));
        }
    }

    @Operation(summary = "Solve basic arithmetic",
            description = "Perform a basic arithmetic operation.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Calculation performed successfully",
                            content = @Content(schema = @Schema(implementation = SuccessResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            })
    @PostMapping("/basicArithmetic")
    public ResponseEntity<?> solveBasicArithmetic(@Valid @RequestBody BasicArithmeticRequest basicArithmeticRequest) {
        try {
            CalculationResult<String> result = calculationService.solveBasicArithmetic(basicArithmeticRequest);
            logInfo(CalculationController.class, "Basic arithmetic calculation performed successfully");
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

    @Operation(summary = "Solve equation",
            description = "Solve a mathematical equation.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Calculation performed successfully",
                            content = @Content(schema = @Schema(implementation = SuccessResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            })
    @PostMapping("/equation")
    public ResponseEntity<?> solveEquation(@Valid @RequestBody EquationRequest equationRequest) {
        try {
            CalculationResult<String> result = calculationService.solveEquation(equationRequest);
            logInfo(CalculationController.class, "Equation calculation performed successfully");
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

    @Operation(summary = "Solve matrix sum",
            description = "Calculate the sum of two matrices.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Calculation performed successfully",
                            content = @Content(schema = @Schema(implementation = SuccessResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            })
    @PostMapping("/matrixSum")
    public ResponseEntity<?> solveMatrixSum(@Valid @RequestBody MatrixSumRequest matrixSumRequest) {
        try {
            CalculationResult<Matrix> result = calculationService.solveMatrixSum(matrixSumRequest);
            logInfo(CalculationController.class, "Matrix sum calculation performed successfully");
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

    @Operation(summary = "Solve matrix subtraction",
            description = "Calculate the subtraction of two matrices.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Calculation performed successfully",
                            content = @Content(schema = @Schema(implementation = SuccessResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            })
    @PostMapping("/matrixSub")
    public ResponseEntity<?> solveMatrixSub(@Valid @RequestBody MatrixSubRequest matrixSubRequest) {
        try {
            CalculationResult<Matrix> result = calculationService.solveMatrixSub(matrixSubRequest);
            logInfo(CalculationController.class, "Matrix subtraction calculation performed successfully");
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

    @Operation(summary = "Solve matrix multiplication",
            description = "Calculate the multiplication of two matrices.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Calculation performed successfully",
                            content = @Content(schema = @Schema(implementation = SuccessResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            })
    @PostMapping("/matrixMul")
    public ResponseEntity<?> solveMatrixMul(@Valid @RequestBody MatrixMulRequest matrixMulRequest) {
        try {
            CalculationResult<Matrix> result = calculationService.solveMatrixMul(matrixMulRequest);
            logInfo(CalculationController.class, "Matrix multiplication calculation performed successfully");
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

    @Operation(summary = "Solve matrix multiplication by number",
            description = "Calculate the multiplication of a matrix by a number.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Calculation performed successfully",
                            content = @Content(schema = @Schema(implementation = SuccessResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            })
    @PostMapping("/matrixMulByNum")
    public ResponseEntity<?> solveMatrixMulByNum(@Valid @RequestBody MatrixMulByNumRequest matrixMulByNumRequest) {
        try {
            CalculationResult<Matrix> result = calculationService.solveMatrixMulByNum(matrixMulByNumRequest);
            logInfo(CalculationController.class,
                    "Matrix multiplication by number calculation performed successfully");
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

    @Operation(summary = "Solve matrix transpose",
            description = "Calculate the transpose of a matrix.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Calculation performed successfully",
                            content = @Content(schema = @Schema(implementation = SuccessResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            })
    @PostMapping("/matrixTranspose")
    public ResponseEntity<?> solveMatrixTranspose(@Valid @RequestBody MatrixTransposeRequest matrixTransposeRequest) {
        try {
            CalculationResult<Matrix> result = calculationService.solveMatrixTranspose(matrixTransposeRequest);
            logInfo(CalculationController.class, "Matrix transpose calculation performed successfully");
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

    @Operation(summary = "Solve matrix reverse",
            description = "Calculate the reverse of a matrix.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Calculation performed successfully",
                            content = @Content(schema = @Schema(implementation = SuccessResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            })
    @PostMapping("/matrixReverse")
    public ResponseEntity<?> solveMatrixReverse(@Valid @RequestBody MatrixReverseRequest matrixReverseRequest) {
        try {
            CalculationResult<Matrix> result = calculationService.solveMatrixReverse(matrixReverseRequest);
            logInfo(CalculationController.class, "Matrix reverse calculation performed successfully");
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

    @Operation(summary = "Solve matrix determinant",
            description = "Calculate the determinant of a matrix.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Calculation performed successfully",
                            content = @Content(schema = @Schema(implementation = SuccessResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            })
    @PostMapping("/matrixDeterminant")
    public ResponseEntity<?> solveMatrixDeterminant(@Valid @RequestBody MatrixDeterminantRequest
                                                            matrixDeterminantRequest) {
        try {
            CalculationResult<Double> result = calculationService.solveMatrixDeterminant(matrixDeterminantRequest);
            logInfo(CalculationController.class, "Matrix determinant calculation performed successfully");
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

    @Operation(summary = "Solve matrix system of equations",
            description = "Solve a system of equations represented by matrices.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Calculation performed successfully",
                            content = @Content(schema = @Schema(implementation = SuccessResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            })
    @PostMapping("/matrixSystem")
    public ResponseEntity<?> solveSystem(@Valid @RequestBody MatrixSystemRequest matrixSystemRequest) {
        try {
            CalculationResult<String> result = calculationService.solveSystem(matrixSystemRequest);
            logInfo(CalculationController.class, "Matrix system calculation performed successfully");
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
