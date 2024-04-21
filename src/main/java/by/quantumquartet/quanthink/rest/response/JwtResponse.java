package by.quantumquartet.quanthink.rest.response;

import java.util.List;

public class JwtResponse {
    private long id;
    private String type = "Bearer";
    private String token;
    private String email;
    private List<String> roles;

    public JwtResponse(String token, long id, String email, List<String> roles) {
        this.token = token;
        this.id = id;
        this.email = email;
        this.roles = roles;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
