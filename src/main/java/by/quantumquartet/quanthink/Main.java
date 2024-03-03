package by.quantumquartet.quanthink;

import by.quantumquartet.quanthink.connection.ConnectionPoolException;
import by.quantumquartet.quanthink.model.Request;
import by.quantumquartet.quanthink.model.User;
import by.quantumquartet.quanthink.modelDAO.DAOException;
import by.quantumquartet.quanthink.modelDAO.UserDAO;
import  static by.quantumquartet.quanthink.modelDAO.RequestDAO.*;
import  static by.quantumquartet.quanthink.modelDAO.UserDAO.*;

// ВРЕМЕННО ДЛЯ ПРОВЕРКИ РАБОТЫ БД
public class Main {
    public static void main(String[] args) throws DAOException {
//        User uset = new User("pascha.kostetskii@gmail.com", "overwees", "12345678");
//        createUser(uset);
//        Request u = new Request("math", "gfdhfdhdf", uset);
//        Request u2 = new Request("math", "gfdhfdhdf", uset);
//        createRequest(u);
//        createRequest(u2);
        Request u = readRequestById(1);
        Request u2 = readRequestById(2);
        System.out.println(u);
        System.out.println(u2);
    }
}