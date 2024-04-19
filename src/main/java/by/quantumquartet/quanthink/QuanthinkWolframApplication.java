package by.quantumquartet.quanthink;

import by.quantumquartet.quanthink.entities.ERole;
import by.quantumquartet.quanthink.entities.Role;
import by.quantumquartet.quanthink.entities.User;
import by.quantumquartet.quanthink.services.RoleService;
import by.quantumquartet.quanthink.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class QuanthinkWolframApplication {
    public static void main(String[] args) {
        SpringApplication.run(QuanthinkWolframApplication.class, args);
    }

    @Bean
    CommandLineRunner init(UserService userService, RoleService roleService) {
        return args -> {
            for (int i = 1; i <= 5; i++) {
                User user = new User();
                user.setEmail("user" + i + "@example.com");
                user.setUsername("user" + i);
                user.setPassword("password" + i);
                userService.createUser(user);
            }

            roleService.createRole(new Role(ERole.ROLE_USER));
            roleService.createRole(new Role(ERole.ROLE_ADMIN));
        };
    }
}
