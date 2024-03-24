package by.quantumquartet.quanthink.entities;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "message")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "m_id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "m_sender_id", nullable = false)
    private User sender;

    @Column(name = "m_content", nullable = false)
    private String content;

    @Column(name = "m_date", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date date;

    public Message() {
        this.sender = null;
        this.content = "";
        this.date = null;
    }

    public Message(User sender, String content, Date date) {
        this.sender = sender;
        this.content = content;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", sender=" + sender +
                ", content='" + content + '\'' +
                ", date=" + date +
                '}';
    }
}
