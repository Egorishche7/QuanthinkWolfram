package by.quantumquartet.quanthink.controllers;

import by.quantumquartet.quanthink.entities.Message;
import by.quantumquartet.quanthink.services.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controller class to handle HTTP requests related to Message entity.
 */
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/messages")
public class MessageController {
    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    /**
     * Endpoint to retrieve a list of all messages.
     *
     * @return List of messages if found, otherwise 404 NOT FOUND.
     */
    @Operation(summary = "Get all messages", description = "Retrieves a list of all messages.")
    @GetMapping
    public ResponseEntity<List<Message>> getAllMessages() {
        List<Message> messages = messageService.getAllMessages();
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }

    /**
     * Endpoint to retrieve a message by ID.
     *
     * @param id The ID of the message to retrieve.
     * @return Message if found, otherwise 404 NOT FOUND.
     */
    @Operation(summary = "Get message by ID", description = "Retrieves a message by ID.")
    @ApiResponse(responseCode = "200", description = "Message found",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))})
    @ApiResponse(responseCode = "404", description = "Message not found")
    @GetMapping("/{id}")
    public ResponseEntity<Message> getMessageById(@Parameter(description = "Message ID") @PathVariable("id") long id) {
        Optional<Message> messageData = messageService.getMessageById(id);
        return messageData.map(message -> new ResponseEntity<>(message, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Endpoint to create a new message.
     *
     * @param message The message object to be created.
     * @return Newly created message with HTTP status 201 CREATED, or 500 INTERNAL SERVER ERROR if creation fails.
     */
    @Operation(summary = "Create message", description = "Creates a new message.")
    @ApiResponse(responseCode = "201", description = "Message created",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))})
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @PostMapping
    public ResponseEntity<Message> createMessage(@RequestBody Message message) {
        try {
            Message newMessage = messageService.createMessage(message);
            return new ResponseEntity<>(newMessage, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Endpoint to update a message.
     *
     * @param id      The ID of the message to update.
     * @param message The updated message object.
     * @return Updated message with HTTP status 200 OK if successful, otherwise 404 NOT FOUND.
     */
    @Operation(summary = "Update message", description = "Updates a message.")
    @ApiResponse(responseCode = "200", description = "Message updated",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))})
    @ApiResponse(responseCode = "404", description = "Message not found")
    @PutMapping("/{id}")
    public ResponseEntity<Message> updateMessage(@Parameter(description = "Message ID") @PathVariable("id") long id,
                                                 @RequestBody Message message) {
        Message updatedMessage = messageService.updateMessage(id, message);
        if (updatedMessage != null) {
            return new ResponseEntity<>(updatedMessage, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Endpoint to delete a message by ID.
     *
     * @param id The ID of the message to delete.
     * @return HTTP status 204 NO CONTENT if successful, otherwise 500 INTERNAL SERVER ERROR.
     */
    @Operation(summary = "Delete message", description = "Deletes a message by ID.")
    @ApiResponse(responseCode = "204", description = "Message deleted")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteMessage(@Parameter(description = "Message ID")
                                                    @PathVariable("id") long id) {
        try {
            messageService.deleteMessage(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
