package by.quantumquartet.quanthink.entities;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "calculation")
public class Calculation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "c_id")
    private long id;

    @Column(name = "c_user_id", nullable = false)
    private long userId;

    @Column(name = "c_type", nullable = false)
    private String type;

    @Column(name = "c_expression", nullable = false)
    private String expression;

    @Column(name = "c_result", nullable = false, columnDefinition = "TEXT")
    private String result;

    @Column(name = "c_date", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date date;

    @Column(name = "c_threads_used", nullable = false)
    private int threadsUsed;

    public Calculation() {
        this.userId = 0;
        this.type = "";
        this.expression = "";
        this.result = "";
        this.date = null;
        this.threadsUsed = 0;
    }

    public Calculation(long userId, String type, String expression, String result, Date date, int threadsUsed) {
        this.userId = userId;
        this.type = type;
        this.expression = expression;
        this.result = result;
        this.date = date;
        this.threadsUsed = threadsUsed;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getThreadsUsed() {
        return threadsUsed;
    }

    public void setThreadsUsed(int threadsUsed) {
        this.threadsUsed = threadsUsed;
    }

    @Override
    public String toString() {
        return "Calculation{" +
                "id=" + id +
                ", userId=" + userId +
                ", type='" + type + '\'' +
                ", expression='" + expression + '\'' +
                ", result='" + result + '\'' +
                ", date=" + date +
                ", threadsUsed=" + threadsUsed +
                '}';
    }
}
