package by.quantumquartet.quanthink.model;

import by.quantumquartet.quanthink.modelDAO.UserDAO;
import jakarta.persistence.*;

@Entity
@Table(name = "request")
@NamedQueries({
        @NamedQuery(
                name = "readAllRequests",
                query = "SELECT r FROM Request r"
        ),
        @NamedQuery(
                name = "readRequestById",
                query = "SELECT r FROM Request r WHERE r.id = :in_id"
        ),
})
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int id;

    @Column(name = "category", nullable = false)
    protected String category;

    @Column(name = "text", nullable = false)
    protected String text;

    @OneToOne
    @JoinColumn(name = "user_email", nullable = false)
    protected User user;

    public Request(String _category, String _text, User _user) {
        setCategory(_category);
        setText(_text);
        setUser(_user);
    }

    public Request() {

    }

    public void setCategory(String _category) {
        this.category = _category;
    }

    public void setText(String _text) {
        this.text = _text;
    }

    public void setUser(User _user) {
        this.user= _user;
    }

    public int getId(){
        return this.id;
    }

    public String getCategory(){
        return this.category;
    }

    public String getText(){
        return this.text;
    }

    public String getUserEmail(){
        return this.user.getEmail();
    }

    @Override
    public String toString() {
        return  "Id: " + getId() + "\n" +
                "Category: " + getCategory() + "\n" +
                "text: " + getText() + "\n" +
                "User email: " + getUserEmail() + "\n";
    }
}
