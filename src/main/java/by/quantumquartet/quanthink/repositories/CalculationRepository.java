package by.quantumquartet.quanthink.repositories;

import by.quantumquartet.quanthink.models.Calculation;
import by.quantumquartet.quanthink.rest.responses.calculations.CalculationResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CalculationRepository extends JpaRepository<Calculation, Long> {
    @Query("select new by.quantumquartet.quanthink.rest.responses.calculations.CalculationResponse" +
            "(c.id, c.type, c.inputData, c.result, c.date, c.library, c.threadsUsed, c.user.id) from Calculation c")
    List<CalculationResponse> findAllCalculations();

    @Query("select new by.quantumquartet.quanthink.rest.responses.calculations.CalculationResponse" +
            "(c.id, c.type, c.inputData, c.result, c.date, c.library, c.threadsUsed, c.user.id) from Calculation c " +
            "where c.id=:id")
    Optional<CalculationResponse> findCalculationById(long id);

    @Query("select new by.quantumquartet.quanthink.rest.responses.calculations.CalculationResponse" +
            "(c.id, c.type, c.inputData, c.result, c.date, c.library, c.threadsUsed, c.user.id) from Calculation c " +
            "where c.user.id=:userId")
    List<CalculationResponse> findCalculationsByUserId(long userId);

    void deleteByUserId(long userId);
}
