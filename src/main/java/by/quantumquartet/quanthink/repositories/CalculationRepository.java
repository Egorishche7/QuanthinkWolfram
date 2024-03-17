package by.quantumquartet.quanthink.repositories;

import by.quantumquartet.quanthink.entities.Calculation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CalculationRepository extends CrudRepository<Calculation, Long> {
}
