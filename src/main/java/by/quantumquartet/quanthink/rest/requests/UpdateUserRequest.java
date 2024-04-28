package by.quantumquartet.quanthink.rest.requests;

public class UpdateUserRequest extends RegisterRequest {

    public UpdateUserRequest(String email, String username, String password) {
        super(email, username, password);
    }
}
