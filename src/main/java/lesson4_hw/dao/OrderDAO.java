package lesson4_hw.dao;

import lesson4_hw.Exception.BadRequestException;
import lesson4_hw.Exception.ObjectNotFoundInBDException;
import lesson4_hw.model.Order;
import lesson4_hw.model.Room;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.hibernate.type.DateType;

import javax.persistence.NoResultException;
import javax.persistence.TemporalType;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;


public class OrderDAO extends SessionFactoryBuilder {
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

    /*
    if one user try to order one room multiple times and the other orders is now active - catch exception
    */
    public static Order bookRoom(Order order, Date dateTo) throws BadRequestException {

        if (findOrderByRoomIDUserID(order.getRoom().getId(), order.getUserOrdered().getId()) != null) {
            throw new BadRequestException("Orders with USER_ID " + order.getUserOrdered().getId() + " and ROOM_ID " + order.getRoom().getId() + " now is active in DB. You can not order the same room multiple times. You should to cancel previous order first");
        }

        RoomDAO roomDAO = new RoomDAO();
        Session session = null;
        Transaction tr = null;
        try {
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();
            save(order);
            roomDAO.updateDateInOrderProcess(order.getRoom(), dateTo);
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
            orderForUpdate.setAvialable(order.isAvialable());
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
        Order order = null;
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

    public static Order findOrderByRoomIDUserID(long roomId, long userId) {
        Session session = null;
        Transaction tr = null;
        Order order = null;

        try {
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();
            Query sqlQuery = session.createSQLQuery("SELECT * FROM ORDERS WHERE USER_ID = ? AND ROOM_ID = ? AND AVIALABLE = 1  ").addEntity(Order.class);
            sqlQuery.setParameter(0, userId);
            sqlQuery.setParameter(1, roomId);

            // todo check
            try {
                order = (Order) sqlQuery.getSingleResult();
                tr.commit();
            } catch (NoResultException e) {
                return null;
            }

        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return order;
    }

// todo (Why this query not work in Idea but work in Oracle Developer?????)
//  SELECT MAX(DATE_TO) FROM ORDERS WHERE USER_ID = 51 AND ROOM_ID = 72 AND AVIALABLE = 0;

    public static Date findDateAvialableRoomBeforeCurrentOrder(Order order) {
        Session session = null;
        Transaction tr = null;
        List<Order> orders = null;
        Order preLastOrder = null;
        try {
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();
            Query sqlQuery = session.createSQLQuery("SELECT * FROM ORDERS WHERE USER_ID = ? AND ROOM_ID = ? AND AVIALABLE = 0 ORDER BY DATE_TO DESC").addEntity(Order.class);
            sqlQuery.setParameter(0, order.getUserOrdered().getId());
            sqlQuery.setParameter(1, order.getRoom().getId());
            orders = sqlQuery.list();
            tr.commit();
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
        // if previous Order is exist -> retutn his dateTo
        // else -> return curent date
        if (orders != null && !orders.isEmpty()) {
            preLastOrder = orders.get(0);
            return preLastOrder.getDateTo();
        } else {
            return new Date();
        }
    }

    public void cancelOrder(Order order, long roomId, Date previousDateAvialable) throws ObjectNotFoundInBDException {
        Session session = null;
        Transaction tr = null;
        RoomDAO roomDAO = new RoomDAO();
        try {
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();
            order.setAvialable(false);
            update(order);
            Room room = roomDAO.findById(roomId);
            room.setDateAvailableFrom(previousDateAvialable);
            roomDAO.update(room);
            tr.commit();
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
