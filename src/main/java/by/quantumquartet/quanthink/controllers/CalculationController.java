package by.quantumquartet.quanthink.controllers;

import by.quantumquartet.quanthink.entities.Calculation;
import by.quantumquartet.quanthink.services.CalculationService;
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
@CrossOrigin(origins = "http://localhost:4200")
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
    @GetMapping
    public ResponseEntity<List<Calculation>> getAllCalculations() {
        List<Calculation> calculations = calculationService.getAllCalculations();
        return new ResponseEntity<>(calculations, HttpStatus.OK);
    }

    /**
     * Endpoint to retrieve a calculation by ID.
     *
     * @param id The ID of the calculation to retrieve.
     * @return Calculation if found, otherwise 404 NOT FOUND.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Calculation> getCalculationById(@PathVariable("id") long id) {
        Optional<Calculation> calculationData = calculationService.getCalculationById(id);
        return calculationData.map(calculation -> new ResponseEntity<>(calculation, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Endpoint to create a new calculation.
     *
     * @param calculation The calculation object to be created.
     * @return Newly created calculation with HTTP status 201 CREATED, or 500 INTERNAL SERVER ERROR if creation fails.
     */
    @PostMapping
    public ResponseEntity<Calculation> createCalculation(@RequestBody Calculation calculation) {
        try {
            Calculation newCalculation = calculationService.createCalculation(calculation);
            return new ResponseEntity<>(newCalculation, HttpStatus.CREATED);
        } catch (Exception e) {
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
    @PutMapping("/{id}")
    public ResponseEntity<Calculation> updateCalculation(@PathVariable("id") long id,
                                                         @RequestBody Calculation calculation) {
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
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteCalculation(@PathVariable("id") long id) {
        try {
            calculationService.deleteCalculation(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
