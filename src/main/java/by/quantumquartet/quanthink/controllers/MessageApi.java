package by.quantumquartet.quanthink.controllers;

import by.quantumquartet.quanthink.models.Message;
import by.quantumquartet.quanthink.rest.request.MessageRequest;
import by.quantumquartet.quanthink.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class MessageApi {
    private final MessageService messageService;

    @Autowired
    public MessageApi(MessageService messageService) {
        this.messageService = messageService;
    }

    @MessageMapping("/chat")
    @SendTo("/secured/topic/messages")
    public Message send(MessageRequest messageRequest) {
        System.out.println(messageRequest);
        return messageService.writeMessage(messageRequest);
    }
}
