package by.quantumquartet.quanthink.repositories;

import by.quantumquartet.quanthink.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
}