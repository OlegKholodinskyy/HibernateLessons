package lesson4_hw.dao;

import lesson4_hw.Exception.BadRequestException;
import lesson4_hw.Exception.ObjectNotFoundInBDException;
import lesson4_hw.model.Order;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.NoResultException;

public class OrderDAO extends SessionFactoryBuilder{
    public static Order save(Order order) {
        Session session = null;
        Transaction tr = null;
        try {
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();
            session.save(order);
            tr.commit();
            System.out.println("Order with name: " + order + " saved");
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
            if (tr != null) {
                tr.rollback();
            }
        } finally {
            if (session != null)
                session.close();
        }
        return order;
    }
    public void delete(long id) throws ObjectNotFoundInBDException {
        Order order = findById(id);
        if (order == null) {
            throw new ObjectNotFoundInBDException("Order id : " + id + "not found in Data Base");
        }
        Session session = null;
        Transaction tr = null;
        try {
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();
            session.delete(order);
            tr.commit();
            System.out.println("Order with ID: " + order.getId() + " was deleted");
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

    public void update(Order order) throws ObjectNotFoundInBDException {
            /*
            1-found order
            2-update if exist
             */
        Order orderForUpdate = findById(order.getId());
        if (orderForUpdate == null) {
            throw new ObjectNotFoundInBDException("Order with id : " + order.getId() + "not found in Data Base");
        }
        Session session = null;
        Transaction tr = null;
        try {
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();
            orderForUpdate.setRoom(order.getRoom());
            orderForUpdate.setUserOrdered(order.getUserOrdered());
            orderForUpdate.setMoneyPaid(order.getMoneyPaid());
            orderForUpdate.setDateTo(order.getDateTo());
            orderForUpdate.setDateTo(order.getDateTo());
            session.update(orderForUpdate);
            tr.commit();
            System.out.println("Order with ID: " + order.getId() + " was updated");
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

    public Order findById(long id) {
        Session session = null;
        Transaction tr = null;
        Order order= null;
        try {
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();
            order = session.get(Order.class, id);
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
        return order;
    }
}
