package by.quantumquartet.quanthink.services;

import by.quantumquartet.quanthink.entities.ERole;
import by.quantumquartet.quanthink.entities.Role;
import by.quantumquartet.quanthink.entities.User;
import by.quantumquartet.quanthink.repositories.UserRepository;
import by.quantumquartet.quanthink.rest.request.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
    private final PasswordEncoder encoder;
    private final RoleService roleService;
    private final UserRepository userRepository;

    @Autowired
    public UserService(PasswordEncoder encoder, RoleService roleService, UserRepository userRepository) {
        this.encoder = encoder;
        this.roleService = roleService;
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(long id) {
        return userRepository.findById(id);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public long registerUser(RegisterRequest registerRequest) {
        User newUser = new User(
                registerRequest.getEmail(),
                registerRequest.getUsername(),
                encoder.encode(registerRequest.getPassword())
        );

        Set<Role> roles = new HashSet<>();
        Optional<Role> roleData = roleService.findRoleByName(ERole.ROLE_USER);
        if (roleData.isPresent()) {
            roles.add(roleData.get());
        } else {
            throw new RuntimeException("Role not found");
        }
        newUser.setRoles(roles);

        return userRepository.save(newUser).getId();
    }

    public User updateUser(long id, User user) {
        Optional<User> userData = userRepository.findById(id);
        if (userData.isPresent()) {
            User existingUser = userData.get();
            existingUser.setEmail(user.getEmail());
            existingUser.setUsername(user.getUsername());
            existingUser.setPassword(user.getPassword());
            existingUser.setRoles(user.getRoles());
            existingUser.setCalculations(user.getCalculations());
            existingUser.setMessages(user.getMessages());
            return userRepository.save(existingUser);
        } else {
            throw new RuntimeException("User not found");
        }
    }

    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }
}
