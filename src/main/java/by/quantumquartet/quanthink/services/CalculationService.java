package by.quantumquartet.quanthink.services;

import by.quantumquartet.quanthink.entities.Calculation;
import by.quantumquartet.quanthink.entities.User;
import by.quantumquartet.quanthink.math.BasicArithmetic;
import by.quantumquartet.quanthink.repositories.CalculationRepository;
import by.quantumquartet.quanthink.repositories.UserRepository;
import by.quantumquartet.quanthink.rest.request.CalculationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CalculationService {
    private final CalculationRepository calculationRepository;
    private final UserRepository userRepository;

    @Autowired
    public CalculationService(CalculationRepository calculationRepository, UserRepository userRepository) {
        this.calculationRepository = calculationRepository;
        this.userRepository = userRepository;
    }

    public List<Calculation> getAllCalculations() {
        return calculationRepository.findAll();
    }

    public Optional<Calculation> getCalculationById(long id) {
        return calculationRepository.findById(id);
    }

    public Calculation createCalculation(Calculation calculation) {
        return calculationRepository.save(calculation);
    }

    public String performCalculation(CalculationRequest calculationRequest) {
        String expression = calculationRequest.getExpression();
        String result = BasicArithmetic.solveExpression(expression);

        Optional<User> userData = userRepository.findById(calculationRequest.getUserId());
        if (userData.isPresent()) {
            Calculation newCalculation = new Calculation();
            newCalculation.setType(calculationRequest.getType());
            newCalculation.setExpression(expression);
            newCalculation.setResult(result);
            newCalculation.setDate(new Date());
            newCalculation.setThreadsUsed(calculationRequest.getThreadsUsed());
            newCalculation.setUser(userData.get());
            calculationRepository.save(newCalculation);
        }

        return result;
    }

    public Calculation updateCalculation(long id, Calculation calculation) {
        Optional<Calculation> calculationData = calculationRepository.findById(id);
        if (calculationData.isPresent()) {
            Calculation existingCalculation = calculationData.get();
            existingCalculation.setType(calculation.getType());
            existingCalculation.setExpression(calculation.getExpression());
            existingCalculation.setResult(calculation.getResult());
            existingCalculation.setDate(calculation.getDate());
            existingCalculation.setThreadsUsed(calculation.getThreadsUsed());
            existingCalculation.setUser(calculation.getUser());
            return calculationRepository.save(existingCalculation);
        } else {
            throw new RuntimeException("Calculation not found");
        }
    }

    public void deleteCalculation(long id) {
        calculationRepository.deleteById(id);
    }
}
