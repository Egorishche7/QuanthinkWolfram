package by.quantumquartet.quanthink.controllers;

import by.quantumquartet.quanthink.auth.AuthenticationRequest;
import by.quantumquartet.quanthink.entities.User;
import by.quantumquartet.quanthink.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static by.quantumquartet.quanthink.services.LoggerManager.logException;

/**
 * Controller class to handle HTTP requests related to User entity.
 */
@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Endpoint to retrieve all users.
     * @return List of users if found, otherwise 404 NOT FOUND.
     */
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
     * @param id The ID of the user to retrieve.
     * @return User if found, otherwise 404 NOT FOUND.
     */
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") long id) {
        Optional<User> userData = userService.getUserById(id);
        return userData.map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Endpoint to create a new user.
     * @param user The user object to be created.
     * @return Newly created user with HTTP status 201 CREATED, or 400 BAD REQUEST if email already exists.
     */
    @PostMapping(value = {"/users", "/signup"})
    public ResponseEntity<?> createUser(@RequestBody User user) {
        try {
            Optional<User> existingUser = userService.findByEmail(user.getEmail());
            if (existingUser.isPresent()) {
                return new ResponseEntity<>("Email already exists", HttpStatus.BAD_REQUEST);
            }

            User newUser = userService.createUser(user);
            return new ResponseEntity<>(newUser, HttpStatus.CREATED);
        } catch (Exception e) {
            logException(e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Endpoint to authenticate a user.
     * @param authenticationRequest The authentication request containing email and password.
     * @return Authenticated user with HTTP status 200 OK if successful, otherwise 401 UNAUTHORIZED.
     */
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody AuthenticationRequest authenticationRequest) {
        String email = authenticationRequest.getEmail();
        String password = authenticationRequest.getPassword();

        Optional<User> userOptional = userService.findByEmail(email);
        if (userOptional.isEmpty()) {
            return new ResponseEntity<>("User not found", HttpStatus.UNAUTHORIZED);
        }

        User user = userOptional.get();

        if (!user.getPassword().equals(password)) {
            return new ResponseEntity<>("Wrong password", HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    /**
     * Endpoint to update a user.
     * @param id The ID of the user to update.
     * @param user The updated user object.
     * @return Updated user with HTTP status 200 OK if successful, otherwise 404 NOT FOUND.
     */
    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") long id, @RequestBody User user) {
        User updatedUser = userService.updateUser(id, user);
        if (updatedUser != null) {
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Endpoint to delete a user by ID.
     * @param id The ID of the user to delete.
     * @return HTTP status 204 NO CONTENT if successful, otherwise 500 INTERNAL SERVER ERROR.
     */
    @DeleteMapping("/users/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") long id) {
        try {
            userService.deleteUser(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            logException(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
