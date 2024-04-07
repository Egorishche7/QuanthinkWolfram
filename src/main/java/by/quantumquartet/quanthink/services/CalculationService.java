package by.quantumquartet.quanthink.services;

import by.quantumquartet.quanthink.entities.Calculation;
import by.quantumquartet.quanthink.math.BasicArithmetic;
import by.quantumquartet.quanthink.repositories.CalculationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CalculationService {
    private final CalculationRepository calculationRepository;

    @Autowired
    public CalculationService(CalculationRepository calculationRepository) {
        this.calculationRepository = calculationRepository;
    }

    public List<Calculation> getAllCalculations() {
        return (List<Calculation>) calculationRepository.findAll();
    }

    public Optional<Calculation> getCalculationById(long id) {
        return calculationRepository.findById(id);
    }

    public Calculation createCalculation(Calculation calculation) {
        String expression = calculation.getExpression();
        String result = BasicArithmetic.solveExpression(expression);
        calculation.setResult(result);
        return calculationRepository.save(calculation);
    }

    public Calculation updateCalculation(long id, Calculation calculation) {
        Optional<Calculation> calculationData = calculationRepository.findById(id);
        if (calculationData.isPresent()) {
            Calculation existingCalculation = calculationData.get();
            existingCalculation.setUserId(calculation.getUserId());
            existingCalculation.setType(calculation.getType());
            existingCalculation.setExpression(calculation.getExpression());
            existingCalculation.setResult(calculation.getResult());
            existingCalculation.setDate(calculation.getDate());
            existingCalculation.setThreadsUsed(calculation.getThreadsUsed());
            return calculationRepository.save(existingCalculation);
        } else {
            return null;
        }
    }

    public void deleteCalculation(long id) {
        calculationRepository.deleteById(id);
    }
}
