package by.quantumquartet.quanthink.controllers;

import static by.quantumquartet.quanthink.services.AppLogger.logError;

import by.quantumquartet.quanthink.models.Calculation;
import by.quantumquartet.quanthink.rest.requests.CalculationRequest;
import by.quantumquartet.quanthink.services.CalculationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controller class to handle HTTP requests related to Calculation entity.
 */
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/calculations")
public class CalculationController {
    private final CalculationService calculationService;

    @Autowired
    public CalculationController(CalculationService calculationService) {
        this.calculationService = calculationService;
    }

    /**
     * Endpoint to retrieve all calculations.
     *
     * @return List of calculations if found, otherwise 404 NOT FOUND.
     */
    @Operation(summary = "Get all calculations", description = "Retrieves a list of all calculations.")
    @GetMapping
    public ResponseEntity<List<Object>> getAllCalculations() {
        List<Object> calculations = calculationService.getAllCalculations();
        return new ResponseEntity<>(calculations, HttpStatus.OK);
    }

    /**
     * Endpoint to retrieve a calculation by ID.
     *
     * @param id The ID of the calculation to retrieve.
     * @return Calculation if found, otherwise 404 NOT FOUND.
     */
    @Operation(summary = "Get calculation by ID", description = "Retrieves a calculation by ID.")
    @ApiResponse(responseCode = "200", description = "Calculation found",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Calculation.class))})
    @ApiResponse(responseCode = "404", description = "Calculation not found")
    @GetMapping("/{id}")
    public ResponseEntity<Calculation> getCalculationById(@Parameter(description = "Calculation ID")
                                                          @PathVariable("id") long id) {
        Optional<Calculation> calculationData = calculationService.getCalculationById(id);
        return calculationData.map(calculation -> new ResponseEntity<>(calculation, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<String> performCalculation(@Valid @RequestBody CalculationRequest calculationRequest) {
        try {
            String result = calculationService.performCalculation(calculationRequest);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } catch (Exception e) {
            logError(CalculationController.class, e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Endpoint to update a calculation.
     *
     * @param id          The ID of the calculation to update.
     * @param calculation The updated calculation object.
     * @return Updated calculation with HTTP status 200 OK if successful, otherwise 404 NOT FOUND.
     */
    @Operation(summary = "Update calculation", description = "Updates a calculation.")
    @ApiResponse(responseCode = "200", description = "Calculation updated",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Calculation.class))})
    @ApiResponse(responseCode = "404", description = "Calculation not found")
    @PutMapping("/{id}")
    public ResponseEntity<Calculation> updateCalculation(@Parameter(description = "Calculation ID") @PathVariable("id")
                                                         long id, @RequestBody Calculation calculation) {
        Calculation updatedCalculation = calculationService.updateCalculation(id, calculation);
        if (updatedCalculation != null) {
            return new ResponseEntity<>(updatedCalculation, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Endpoint to delete a calculation by ID.
     *
     * @param id The ID of the calculation to delete.
     * @return HTTP status 204 NO CONTENT if successful, otherwise 500 INTERNAL SERVER ERROR.
     */
    @Operation(summary = "Delete calculation", description = "Deletes a calculation by ID.")
    @ApiResponse(responseCode = "204", description = "Calculation deleted")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteCalculation(@Parameter(description = "Calculation ID")
                                                        @PathVariable("id") long id) {
        try {
            calculationService.deleteCalculation(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            logError(CalculationController.class, e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
