package by.quantumquartet.quanthink.rest.requests.messages;

import jakarta.validation.constraints.NotBlank;

public class MessageRequest {
    private long userId;

    @NotBlank
    private String content;

    public MessageRequest(long userId, String content) {
        this.userId = userId;
        this.content = content;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "MessageRequest{" +
                "userId=" + userId +
                ", content='" + content + '\'' +
                '}';
    }
}
