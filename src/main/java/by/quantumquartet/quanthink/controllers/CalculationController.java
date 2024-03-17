package by.quantumquartet.quanthink.controllers;

import by.quantumquartet.quanthink.entities.Calculation;
import by.quantumquartet.quanthink.repositories.CalculationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

//TODO: сервисы

@RestController
//@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/calculations")
public class CalculationController {
    private final CalculationRepository calculationRepository;

    @Autowired
    public CalculationController(CalculationRepository calculationRepository) {
        this.calculationRepository = calculationRepository;
    }

    @GetMapping
    public ResponseEntity<List<Calculation>> getAllCalculations() {
        List<Calculation> calculations = (List<Calculation>) calculationRepository.findAll();
        return new ResponseEntity<>(calculations, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Calculation> getCalculationById(@PathVariable("id") long id) {
        Optional<Calculation> calculationData = calculationRepository.findById(id);
        return calculationData.map(calculation -> new ResponseEntity<>(calculation, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Calculation> createCalculation(@RequestBody Calculation calculation) {
        try {
            Calculation newCalculation = calculationRepository.save(calculation);
            return new ResponseEntity<>(newCalculation, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Calculation> updateCalculation(@PathVariable("id") long id,
                                                         @RequestBody Calculation calculation) {
        Optional<Calculation> calculationData = calculationRepository.findById(id);
        if (calculationData.isPresent()) {
            Calculation existingCalculation = calculationData.get();
            existingCalculation.setUser(calculation.getUser());
            existingCalculation.setType(calculation.getType());
            existingCalculation.setExpression(calculation.getExpression());
            existingCalculation.setResult(calculation.getResult());
            existingCalculation.setDate(calculation.getDate());
            existingCalculation.setThreadsUsed(calculation.getThreadsUsed());
            return new ResponseEntity<>(calculationRepository.save(existingCalculation), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteCalculation(@PathVariable("id") long id) {
        try {
            calculationRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
