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
    static RoomDAO roomDAO = new RoomDAO();


    public static Order save(Order order) {
        Transaction tr = null;
        try (Session session = createSessionFactory().openSession()) {
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
        }
        return order;
    }


    public static Order bookRoom(Order order, Date dateTo) {

        Transaction tr = null;
        try (Session session = createSessionFactory().openSession()) {
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
        }
        return order;
    }

    public void delete(long id) {
        Order order = findById(id);
        Transaction tr = null;

        try (Session session = createSessionFactory().openSession()) {
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
        }
    }

    public void update(Order order) {
            /*
            1-found order
            2-update if exist
             */
        Order orderForUpdate = findById(order.getId());

        Transaction tr = null;
        try (Session session = createSessionFactory().openSession()) {
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
        }
    }

    public Order findById(long id) {
        Transaction tr = null;
        Order order = null;
        try (Session session = createSessionFactory().openSession()) {
            tr = session.getTransaction();
            tr.begin();
            order = session.get(Order.class, id);
            tr.commit();
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
            if (tr != null) {
                tr.rollback();
            }
        }
        return order;
    }

    public static Order findOrderByRoomIDUserID(long roomId, long userId) {
        Transaction tr = null;
        Order order = null;

        try (Session session = createSessionFactory().openSession()) {
            tr = session.getTransaction();
            tr.begin();

            List<Order> orders = findAll();
            for (Order order1 : orders) {
                if (order1.getRoom().getId().equals(roomId) && order1.getUserOrdered().getId().equals(userId)) {
                    return order1;
                }
            }
            tr.commit();
        }
        return null;
    }

    private static List<Order> findAll() {
        Transaction tr = null;
        List<Order> orders = null;
        try (Session session = createSessionFactory().openSession()) {
            tr = session.getTransaction();
            tr.begin();
            Query sqlQuery = session.createSQLQuery("SELECT * FROM ORDERS").addEntity(Order.class);
            orders = sqlQuery.list();
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
            if (tr != null) {
                tr.rollback();
            }
        }
        return orders;
    }

    public static Date findDateAvialableRoomBeforeCurrentOrder(Order order) {
        Transaction tr = null;
        List<Order> orders = null;
        Order preLastOrder = null;
        try (Session session = createSessionFactory().openSession()) {
            tr = session.getTransaction();
            tr.begin();
            Query sqlQuery = session.createSQLQuery("SELECT * FROM ORDERS WHERE USER_ID = ? AND ROOM_ID = ? AND AVIALABLE = 0 ORDER BY DATE_TO DESC").addEntity(Order.class);
            sqlQuery.setParameter(0, order.getUserOrdered().getId());
            sqlQuery.setParameter(1, order.getRoom().getId());
            orders = sqlQuery.list();
            tr.commit();
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
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

        Transaction tr = null;
        try (Session session = createSessionFactory().openSession()) {
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
        }
    }
}
