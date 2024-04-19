package by.quantumquartet.quanthink.rest.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class CalculationRequest {
    private long userId;

    @NotBlank
    private String type;

    @NotBlank
    private String expression;

    @Min(value = 1)
    private int threadsUsed;

    public CalculationRequest(long userId, String type, String expression, int threadsUsed) {
        this.userId = userId;
        this.type = type;
        this.expression = expression;
        this.threadsUsed = threadsUsed;
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

    public int getThreadsUsed() {
        return threadsUsed;
    }

    public void setThreadsUsed(int threadsUsed) {
        this.threadsUsed = threadsUsed;
    }
}
