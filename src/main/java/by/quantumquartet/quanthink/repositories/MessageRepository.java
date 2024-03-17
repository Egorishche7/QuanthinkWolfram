package by.quantumquartet.quanthink.repositories;

import by.quantumquartet.quanthink.entities.Message;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends CrudRepository<Message, Long> {
}
