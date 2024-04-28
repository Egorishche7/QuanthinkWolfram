package by.quantumquartet.quanthink.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.sql.Timestamp;

@Entity
@Table(name = "calculation")
public class Calculation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    @Column(nullable = false)
    private String type;

    @NotBlank
    @Column(nullable = false)
    private String expression;

    @NotBlank
    @Column(nullable = false)
    private String result;

    @NotBlank
    @Column(nullable = false)
    private Timestamp date;

    @NotBlank
    @Column(nullable = false)
    private int threadsUsed;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Calculation() {
    }

    public Calculation(String type, String expression, int threadsUsed) {
        this.type = type;
        this.expression = expression;
        this.threadsUsed = threadsUsed;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public int getThreadsUsed() {
        return threadsUsed;
    }

    public void setThreadsUsed(int threadsUsed) {
        this.threadsUsed = threadsUsed;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}