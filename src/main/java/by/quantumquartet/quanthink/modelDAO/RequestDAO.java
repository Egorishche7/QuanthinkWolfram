package by.quantumquartet.quanthink.modelDAO;

import by.quantumquartet.quanthink.connection.ConnectionPool;
import by.quantumquartet.quanthink.connection.ConnectionPoolException;
import by.quantumquartet.quanthink.model.Request;
import by.quantumquartet.quanthink.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RequestDAO {
    public static List<Request> readAllRequests() throws DAOException {
        List<Request> requests = new ArrayList<>();
        EntityManager em = null;
        try {
            em = ConnectionPool.getConnection();
            Query query = em.createNamedQuery("readAllRequests");
            requests = query.getResultList();
            if (requests.isEmpty())
                throw new SQLException("Receiving requests failed, no requests found");
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
        return requests;
    }

    public static Request readRequestById(int _id) throws DAOException {
        Request request = null;
        EntityManager em = null;
        try {
            em = ConnectionPool.getConnection();
            Query query = em.createNamedQuery("readRequestById");
            query.setParameter("in_id", _id);
            request = (Request) query.getSingleResult();
            if (request == null)
                throw new SQLException("Receiving request failed, no request found");
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
        return request;
    }

    public static void createRequest(Request _request) throws DAOException {
        EntityManager em = null;
        EntityTransaction transaction = null;
        try {
            em = ConnectionPool.getConnection();
            transaction = em.getTransaction();
            transaction.begin();
            em.persist(_request);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();

                throw new DAOException("Creating request failed, no rows affected");
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

    public static void deleteRequest(Request _request) throws DAOException {
        EntityManager em = null;
        EntityTransaction transaction = null;
        try {
            em = ConnectionPool.getConnection();
            transaction = em.getTransaction();
            transaction.begin();
            User u = em.find(User.class, _request.getClass());
            em.remove(u);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
                throw new DAOException("Deleting request failed, no rows affected");
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
    public static void updateRequest(Request _request) throws DAOException {
        EntityManager em = null;
        EntityTransaction transaction = null;
        try {
            em = ConnectionPool.getConnection();
            transaction = em.getTransaction();
            transaction.begin();
            em.merge(_request);
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
