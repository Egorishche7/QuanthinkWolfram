package by.quantumquartet.quanthink.model;

import jakarta.persistence.*;

@Entity
@Table(name = "user")
@NamedQueries({
        @NamedQuery(
                name = "readAllUsers",
                query = "SELECT u FROM User u"
        ),
        @NamedQuery(
                name = "readUserByEmail",
                query = "SELECT u FROM User u WHERE u.email = :in_email"
        )
})
public class User {

    @Id @Column(name = "email", nullable = false, unique = true)
    protected String email;

    @Column(name = "nickname", nullable = false)
    protected String nickname;

    @Column(name = "password", nullable = false)
    protected String password;

    public User(String _email, String _nickname, String _password) {
        setEmail(_email);
        setNickmame(_nickname);
        setPassword(_password);
    }

    public User() {

    }

    public void setEmail(String _email) {
        this.email = _email;
    }

    public void setNickmame(String _nickname) {
        this.nickname = _nickname;
    }

    public void setPassword(String _password) {
        this.password = _password;
    }

    public String getEmail(){
        return this.email;
    }

    public String getNickmame(){
        return this.nickname;
    }

    public String getPassword(){
        return this.password;
    }

    @Override
    public String toString() {
        return "Email: " + getEmail() + "\n" +
                "Nickname: " + getNickmame() + "\n" +
                "Password: " + getPassword() + "\n";
    }
}
