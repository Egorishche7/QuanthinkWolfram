package by.quantumquartet.quanthink.services;

import by.quantumquartet.quanthink.models.Message;
import by.quantumquartet.quanthink.models.User;
import by.quantumquartet.quanthink.repositories.MessageRepository;
import by.quantumquartet.quanthink.repositories.UserRepository;
import by.quantumquartet.quanthink.rest.request.MessageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Optional<Message> getMessageById(long id) {
        return messageRepository.findById(id);
    }

    public Message createMessage(Message message) {
        return messageRepository.save(message);
    }

    public Message writeMessage(MessageRequest messageRequest) {
        Optional<User> userData = userRepository.findById(messageRequest.getUserId());
        if (userData.isPresent()) {
            Message newMessage = new Message();
            newMessage.setContent(messageRequest.getContent());
            newMessage.setDate(new Timestamp(System.currentTimeMillis()));
            newMessage.setUser(userData.get());
            messageRepository.save(newMessage);
            return newMessage;
        } else {
            throw new RuntimeException("User not found");
        }
    }

    public Message updateMessage(long id, Message message) {
        Optional<Message> messageData = messageRepository.findById(id);
        if (messageData.isPresent()) {
            Message existingMessage = messageData.get();
            existingMessage.setContent(message.getContent());
            existingMessage.setDate(message.getDate());
            existingMessage.setUser(message.getUser());
            return messageRepository.save(existingMessage);
        } else {
            throw new RuntimeException("Message not found");
        }
    }

    public void deleteMessage(long id) {
        messageRepository.deleteById(id);
    }
}
