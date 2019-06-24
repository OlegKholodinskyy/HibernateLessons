package lesson4_hw.dao;

import lesson4_hw.Exception.BadRequestException;
import lesson4_hw.Exception.ObjectNotFoundInBDException;
import lesson4_hw.model.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.NoResultException;
import java.util.List;

public class UserDAO extends SessionFactoryBuilder {


    public static User save(User user) {
        Transaction tr = null;
        try (Session session = createSessionFactory().openSession()) {
            tr = session.getTransaction();
            tr.begin();
            session.save(user);
            tr.commit();
            System.out.println("User with name: " + user.getUserName() + " saved");
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
            if (tr != null) {
                tr.rollback();
            }
        }
        return user;
    }

    public void delete(User user) {

        Transaction tr = null;
        try (Session session = createSessionFactory().openSession()) {
            tr = session.getTransaction();
            tr.begin();
            session.delete(user);
            tr.commit();
            System.out.println("User with name: " + user.getUserName() + " was deleted");
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
            if (tr != null) {
                tr.rollback();
            }
        }
    }

    public List<User> findAllUsers() {
        Transaction tr = null;
        List<User> users = null;
        try (Session session = createSessionFactory().openSession()) {
            tr = session.getTransaction();
            tr.begin();
            Query sqlQuery = session.createSQLQuery("SELECT * FROM USERS").addEntity(User.class);
            users = sqlQuery.list();
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
            if (tr != null) {
                tr.rollback();
            }
        }
        return users;
    }


    public void update(User user) throws ObjectNotFoundInBDException {

        User userForUpdate = findById(user.getId());
        Transaction tr = null;
        try (Session session = createSessionFactory().openSession()) {
            tr = session.getTransaction();
            tr.begin();
            userForUpdate.setUserType(user.getUserType());
            userForUpdate.setPassword(user.getPassword());
            userForUpdate.setCountry(user.getCountry());
            userForUpdate.setUserName(user.getUserName());
            session.update(userForUpdate);
            tr.commit();
            System.out.println("User with name: " + user.getUserName() + " was updated");
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
            if (tr != null) {
                tr.rollback();
            }
        }
    }

    public User findById(long id) {
        Transaction tr = null;
        User user = null;
        try (Session session = createSessionFactory().openSession()) {
            tr = session.getTransaction();
            tr.begin();
            user = session.get(User.class, id);
            tr.commit();
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
            if (tr != null) {
                tr.rollback();
            }
        }
        return user;
    }

    public boolean checkIfUserNameisExist(String userName) {
        Transaction tr = null;
        User user = null;
        try (Session session = createSessionFactory().openSession()) {
            tr = session.getTransaction();
            tr.begin();
            List<User> users = findAllUsers();

            for (User user1 : users) {
                if (user1.getUserName().equals(userName)) {
                    return true;
                }
            }
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public User getUserByLoginAndPass(String userName, String password) {
        Transaction tr = null;
        try (Session session = createSessionFactory().openSession()) {
            tr = session.getTransaction();
            tr.begin();

            List<User> users = findAllUsers();

            for (User user1 : users) {
                if (user1.getUserName().equals(userName) && user1.getPassword().equals(password)) {
                    return user1;
                }
            }
            tr.commit();
            return null;
        }
    }
}

