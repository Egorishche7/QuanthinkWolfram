package by.quantumquartet.quanthink.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "u_id")
    private long id;

    @Column(name = "u_email", unique = true, nullable = false)
    private String email;

    @Column(name = "u_username", nullable = false)
    private String username;

    @Column(name = "u_password", nullable = false)
    private String password;

    @Column(name = "u_role", nullable = false, columnDefinition = "VARCHAR(10) DEFAULT 'user'")
    private String role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Calculation> calculations;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Message> messages;

    public User() {
        this.email = "";
        this.username = "";
        this.password = "";
        this.role = "user";
        this.calculations = null;
        this.messages = null;
    }

    public User(String email, String username, String password, String role,
                List<Calculation> calculations, List<Message> messages) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
        this.calculations = calculations;
        this.messages = messages;
    }

    public long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<Calculation> getCalculations() {
        return calculations;
    }

    public void setCalculations(List<Calculation> calculations) {
        this.calculations = calculations;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", calculations=" + calculations +
                ", messages=" + messages +
                '}';
    }
}
