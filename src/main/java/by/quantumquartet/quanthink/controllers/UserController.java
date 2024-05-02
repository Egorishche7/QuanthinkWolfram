package by.quantumquartet.quanthink.controllers;

import static by.quantumquartet.quanthink.services.AppLogger.logError;

import by.quantumquartet.quanthink.models.ERole;
import by.quantumquartet.quanthink.rest.requests.users.UpdateUserRequest;
import by.quantumquartet.quanthink.rest.responses.ErrorResponse;
import by.quantumquartet.quanthink.rest.responses.SuccessResponse;
import by.quantumquartet.quanthink.rest.responses.users.UserDto;
import by.quantumquartet.quanthink.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        List<UserDto> users = userService.getAllUsers();
        if (users.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("No users found"));
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(new SuccessResponse<>("Users retrieved successfully", users));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") long id) {
        Optional<UserDto> userData = userService.getUserById(id);
        if (userData.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("User not found"));
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(new SuccessResponse<>("User retrieved successfully", userData.get()));
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<?> updateUser(@PathVariable("id") long id, @RequestBody UpdateUserRequest updateUserRequest) {
        Optional<UserDto> userData = userService.getUserById(id);
        if (userData.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("User not found"));
        }

        if (userService.isEmailAlreadyExists(updateUserRequest.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("Email already exists"));
        }

        try {
            UserDto updatedUser = userService.updateUser(id, updateUserRequest);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new SuccessResponse<>("User updated successfully", updatedUser));
        } catch (Exception e) {
            logError(UserController.class, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Internal server error"));
        }
    }

    @PutMapping("/users/{id}/assignAdminRole")
    public ResponseEntity<?> assignAdminRole(@PathVariable("id") long id) {
        Optional<UserDto> userData = userService.getUserById(id);
        if (userData.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("User not found"));
        }

        boolean isAdmin = userData.get().getRoles().stream().anyMatch(role -> role == ERole.ROLE_ADMIN);
        if (isAdmin) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("User is already an admin"));
        }

        try {
            UserDto updatedUser = userService.assignAdminRole(id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new SuccessResponse<>("Admin role assigned successfully", updatedUser));
        } catch (Exception e) {
            logError(UserController.class, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Internal server error"));
        }
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") long id) {
        Optional<UserDto> userData = userService.getUserById(id);
        if (userData.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("User not found"));
        }
        try {
            userService.deleteUser(id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new SuccessResponse<>("User deleted successfully", id));
        } catch (Exception e) {
            logError(UserController.class, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Internal server error"));
        }
    }
}
