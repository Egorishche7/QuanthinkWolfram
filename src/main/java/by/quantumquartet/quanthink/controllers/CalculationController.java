package by.quantumquartet.quanthink.controllers;

import by.quantumquartet.quanthink.entities.Calculation;
import by.quantumquartet.quanthink.services.CalculationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/calculations")
public class CalculationController {
    private final CalculationService calculationService;

    @Autowired
    public CalculationController(CalculationService calculationService) {
        this.calculationService = calculationService;
    }

    @GetMapping
    public ResponseEntity<List<Calculation>> getAllCalculations() {
        List<Calculation> calculations = calculationService.getAllCalculations();
        return new ResponseEntity<>(calculations, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Calculation> getCalculationById(@PathVariable("id") long id) {
        Optional<Calculation> calculationData = calculationService.getCalculationById(id);
        return calculationData.map(calculation -> new ResponseEntity<>(calculation, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Calculation> createCalculation(@RequestBody Calculation calculation) {
        try {
            Calculation newCalculation = calculationService.createCalculation(calculation);
            return new ResponseEntity<>(newCalculation, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

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
