package by.quantumquartet.quanthink.controllers;

import static by.quantumquartet.quanthink.services.AppLogger.logError;

import java.util.List;
import java.util.Optional;

import by.quantumquartet.quanthink.models.User;
import by.quantumquartet.quanthink.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller class to handle HTTP requests related to User entity.
 */
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Endpoint to retrieve all users.
     *
     * @return List of users if found, otherwise 404 NOT FOUND.
     */
    @Operation(summary = "Get all users", description = "Retrieves a list of all users.")
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    /**
     * Endpoint to retrieve a user by ID.
     *
     * @param id The ID of the user to retrieve.
     * @return User if found, otherwise 404 NOT FOUND.
     */
    @Operation(summary = "Get user by ID", description = "Retrieves a user by ID.")
    @ApiResponse(responseCode = "200", description = "User found",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = User.class))})
    @ApiResponse(responseCode = "404", description = "User not found")
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@Parameter(description = "User ID") @PathVariable("id") long id) {
        Optional<User> userData = userService.getUserById(id);
        return userData.map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Endpoint to update a user.
     *
     * @param id   The ID of the user to update.
     * @param user The updated user object.
     * @return Updated user with HTTP status 200 OK if successful, otherwise 404 NOT FOUND.
     */
    @Operation(summary = "Update user", description = "Updates a user.")
    @ApiResponse(responseCode = "200", description = "User updated",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = User.class))})
    @ApiResponse(responseCode = "404", description = "User not found")
    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@Parameter(description = "User ID") @PathVariable("id") long id,
                                           @RequestBody User user) {
        User updatedUser = userService.updateUser(id, user);
        if (updatedUser != null) {
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Endpoint to delete a user by ID.
     *
     * @param id The ID of the user to delete.
     * @return HTTP status 204 NO CONTENT if successful, otherwise 500 INTERNAL SERVER ERROR.
     */
    @Operation(summary = "Delete user", description = "Deletes a user by ID.")
    @ApiResponse(responseCode = "204", description = "User deleted")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @DeleteMapping("/users/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@Parameter(description = "User ID") @PathVariable("id") long id) {
        try {
            userService.deleteUser(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            logError(UserController.class, e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
