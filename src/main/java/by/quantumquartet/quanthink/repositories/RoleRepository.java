package by.quantumquartet.quanthink.repositories;

import by.quantumquartet.quanthink.entities.Role;
import by.quantumquartet.quanthink.entities.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
