package by.quantumquartet.quanthink.services;

import by.quantumquartet.quanthink.entities.Message;
import by.quantumquartet.quanthink.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MessageService {
    private final MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public List<Message> getAllMessages() {
        return (List<Message>) messageRepository.findAll();
    }

    public Optional<Message> getMessageById(long id) {
        return messageRepository.findById(id);
    }

    public Message createMessage(Message message) {
        return messageRepository.save(message);
    }

    public Message updateMessage(long id, Message message) {
        Optional<Message> messageData = messageRepository.findById(id);
        if (messageData.isPresent()) {
            Message existingMessage = messageData.get();
            existingMessage.setSender(message.getSender());
            existingMessage.setContent(message.getContent());
            existingMessage.setDate(message.getDate());
            return messageRepository.save(existingMessage);
        } else {
            // Обработка ошибки, если сообщение не найдено
            return null;
        }
    }

    public void deleteMessage(long id) {
        messageRepository.deleteById(id);
    }
}
