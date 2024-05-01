package by.quantumquartet.quanthink.services;

import by.quantumquartet.quanthink.models.ERole;
import by.quantumquartet.quanthink.models.Role;
import by.quantumquartet.quanthink.models.User;
import by.quantumquartet.quanthink.repositories.UserRepository;
import by.quantumquartet.quanthink.rest.requests.users.RegisterRequest;
import by.quantumquartet.quanthink.rest.requests.users.UpdateUserRequest;
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

    public boolean isEmailAlreadyExists(String email) {
        return userRepository.existsByEmail(email);
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
            throw new RuntimeException("Role " + ERole.ROLE_USER + " not found");
        }
        newUser.setRoles(roles);

        return userRepository.save(newUser).getId();
    }

    public User updateUser(long id, UpdateUserRequest updateUserRequest) {
        Optional<User> userData = userRepository.findById(id);
        if (userData.isPresent()) {
            User existingUser = userData.get();
            existingUser.setEmail(updateUserRequest.getEmail());
            existingUser.setUsername(updateUserRequest.getUsername());
            existingUser.setPassword(encoder.encode(updateUserRequest.getPassword()));
            return userRepository.save(existingUser);
        } else {
            throw new RuntimeException("User with id = " + id + " not found");
        }
    }

    public User assignAdminRole(long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            User existingUser = user.get();
            Set<Role> roles = existingUser.getRoles();

            Optional<Role> roleData = roleService.findRoleByName(ERole.ROLE_ADMIN);
            if (roleData.isPresent()) {
                roles.add(roleData.get());
            } else {
                throw new RuntimeException("Role " + ERole.ROLE_ADMIN + " not found");
            }
            existingUser.setRoles(roles);
            return userRepository.save(existingUser);
        } else {
            throw new RuntimeException("User with id = " + id + " not found");
        }
    }

    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }
}
