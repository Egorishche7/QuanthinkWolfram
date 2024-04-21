package by.quantumquartet.quanthink.repositories;

import by.quantumquartet.quanthink.models.Calculation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CalculationRepository extends JpaRepository<Calculation, Long> {
}
