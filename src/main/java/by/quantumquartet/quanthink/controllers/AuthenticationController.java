package by.quantumquartet.quanthink.controllers;

import static by.quantumquartet.quanthink.services.AppLogger.logError;

import by.quantumquartet.quanthink.models.User;
import by.quantumquartet.quanthink.rest.request.LoginRequest;
import by.quantumquartet.quanthink.rest.request.RegisterRequest;
import by.quantumquartet.quanthink.rest.response.JwtResponse;
import by.quantumquartet.quanthink.security.jwt.JwtUtils;
import by.quantumquartet.quanthink.models.UserDetailsImpl;
import by.quantumquartet.quanthink.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtUtils jwtUtils;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager,
                                    UserService userService,
                                    JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        try {
            Optional<User> existingUser = userService.findByEmail(registerRequest.getEmail());
            if (existingUser.isPresent()) {
                return new ResponseEntity<>("Email already exists", HttpStatus.BAD_REQUEST);
            }

            long newUserId = userService.registerUser(registerRequest);
            return new ResponseEntity<>(newUserId, HttpStatus.CREATED);
        } catch (Exception e) {
            logError(AuthenticationController.class, e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        JwtResponse jwtResponse = new JwtResponse(jwt, userDetails.getId(), userDetails.getEmail(), roles);
        return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
    }
}
