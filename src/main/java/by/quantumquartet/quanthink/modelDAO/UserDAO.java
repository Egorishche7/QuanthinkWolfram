package by.quantumquartet.quanthink.modelDAO;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.StoredProcedureQuery;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import by.quantumquartet.quanthink.connection.ConnectionPool;
import by.quantumquartet.quanthink.connection.ConnectionPoolException;
import by.quantumquartet.quanthink.model.User;

public class UserDAO {
    @SuppressWarnings("unchecked")
    public static List<User> readAllUsers() throws DAOException {
        List<User> users = new ArrayList<>();
        EntityManager em = null;
        try {
            em = ConnectionPool.getConnection();
            Query query = em.createNamedQuery("readAllUsers");
            users = query.getResultList();
            if (users.isEmpty())
                throw new SQLException("Receiving users failed, no users found");
        } catch (Exception e) {
            throw new DAOException(e);
        } finally {
            if (em != null) {
                try {
                    ConnectionPool.releaseConnection(em);
                } catch(ConnectionPoolException e) {
                    // logException(e);
                }
            }
        }
        return users;
    }

    public static User readUserByEmail(String _email) throws DAOException {
        User user = null;
        EntityManager em = null;
        try {
            em = ConnectionPool.getConnection();
            Query query = em.createNamedQuery("readUserByEmail");
            query.setParameter("in_email", _email);
            user = (User) query.getSingleResult();
            if (user == null)
                throw new SQLException("Receiving user failed, no user found");
        } catch (Exception e) {
            throw new DAOException(e);
        } finally {
            if (em != null) {
                try {
                    ConnectionPool.releaseConnection(em);
                } catch(ConnectionPoolException e) {
                    //logException(e);
                }
            }
        }
        return user;
    }

    public static void createUser(User _user) throws DAOException {
        EntityManager em = null;
        EntityTransaction transaction = null;
        try {
            em = ConnectionPool.getConnection();
            transaction = em.getTransaction();
            transaction.begin();
            em.persist(_user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();

                throw new DAOException("Creating user failed, no rows affected");
            }
            throw new DAOException(e.getMessage());
        } finally {
            if (em != null) {
                try {
                    ConnectionPool.releaseConnection(em);
                } catch(ConnectionPoolException e) {
                    //logException(e);
                }
            }
        }
    }

    public static void deleteUser(User _user) throws DAOException {
        EntityManager em = null;
        EntityTransaction transaction = null;
        try {
            em = ConnectionPool.getConnection();
            transaction = em.getTransaction();
            transaction.begin();
            User u = em.find(User.class, _user.getEmail());
            em.remove(u);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
                throw new DAOException("Deleting user failed, no rows affected");
            }
            throw new DAOException(e);
        } finally {
            if (em != null) {
                try {
                    ConnectionPool.releaseConnection(em);
                } catch(ConnectionPoolException e) {
                    // logException(e);
                }
            }
        }
    }
    public static void updateUser(User _user) throws DAOException {
        EntityManager em = null;
        EntityTransaction transaction = null;
        try {
            em = ConnectionPool.getConnection();
            transaction = em.getTransaction();
            transaction.begin();
            em.merge(_user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw new DAOException("Updating user failed, no rows affected");
        } finally {
            if (em != null) {
                try {
                    ConnectionPool.releaseConnection(em);
                } catch(ConnectionPoolException e) {
                    // logException(e);
                }
            }
        }
    }

}
