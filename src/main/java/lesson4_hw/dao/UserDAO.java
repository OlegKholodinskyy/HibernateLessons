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
        Session session = null;
        Transaction tr = null;
        try {
            session = createSessionFactory().openSession();
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
        } finally {
            if (session != null)
                session.close();
        }
        return user;
    }

    public void delete(long id) throws ObjectNotFoundInBDException {
        User user = findById(id);
        if (user == null) {
            throw new ObjectNotFoundInBDException("User id : " + id + "not found in Data Base");
        }
        Session session = null;
        Transaction tr = null;
        try {
            session = createSessionFactory().openSession();
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
        } finally {
            if (session != null)
                session.close();
        }
    }

    public void update(User user) throws ObjectNotFoundInBDException {
            /*
            1-found user
            2-update if exist
             */
        User userForUpdate = findById(user.getId());
        if (userForUpdate == null) {
            throw new ObjectNotFoundInBDException("User id : " + user.getId() + "not found in Data Base");
        }
        Session session = null;
        Transaction tr = null;
        try {
            session = createSessionFactory().openSession();
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
        } finally {
            if (session != null)
                session.close();
        }
    }

    public  User findById(long id) {
        Session session = null;
        Transaction tr = null;
        User user = null;
        try {
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();
            user = session.get(User.class, id);
            tr.commit();
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
            if (tr != null) {
                tr.rollback();
            }
        } finally {
            if (session != null)
                session.close();
        }
        return user;
    }

    // if user_name is free - return in NoResultException
    public void checkIfUserNameisExist(String userName) throws BadRequestException {
        Session session = null;
        Transaction tr = null;
        User user = null;
        try {
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();
            Query sqlQuery = session.createSQLQuery("SELECT * FROM USERS where USER_NAME =?").addEntity(User.class);
            sqlQuery.setParameter(0, userName);
            try {
                user = (User) sqlQuery.getSingleResult();
            } catch (NoResultException e) {
                return;
            }
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
        throw new BadRequestException("User with name " + userName + "is already registred.");
    }

    public User getUserByLoginAndPass(String userName, String password) throws BadRequestException {
        Session session = null;
        Transaction tr = null;
        User user = null;
        try {
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();
            Query sqlQuery = session.createSQLQuery("SELECT * FROM USERS where USER_NAME =? AND USER_PASSWORD = ?").addEntity(User.class);
            sqlQuery.setParameter(0, userName);
            sqlQuery.setParameter(1, password);
               try {
                   user = (User) sqlQuery.getSingleResult();
               } catch (NoResultException e) {
                   throw new BadRequestException("Login fail. Check login and Password");
               }
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return user;
    }
}

