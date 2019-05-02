package lesson3_hw.repository;


import lesson3_hw.model.Room;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import javax.persistence.Query;

public class RoomDAO {
    static SessionFactory sessionFactory = null;

    public static long save(Room room) {
        Session session = null;
        Transaction tr = null;
        try {
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();
            session.save(room);
            tr.commit();
            System.out.println("room id : " + room.getId() + " saved.");
        } catch (HibernateException e) {
            System.err.println("Something wrong. Method SaveRoom.");
            System.out.println(e.getMessage());
            if (tr != null) {
                tr.rollback();
            }
        } finally {
            if (session != null)
                session.close();
        }
        return 0;
    }

    public static SessionFactory createSessionFactory() {
        if (sessionFactory == null) {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        }
        return sessionFactory;
    }

    public void delete(long id)  {

        Room room = findById(id);

        if (room == null) {
            System.out.println("Nothing delete.");
            return;
        }

        Session session = null;
        Transaction tr = null;
        try {
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();
            session.delete(room);
            tr.commit();
            System.out.println("room was deleted");
        } catch (HibernateException e) {
            System.err.println("Something wrong. Method deleteRoom().");
            System.out.println(e.getMessage());
            if (tr != null) {
                tr.rollback();
            }
        } finally {
            if (session != null)
                session.close();
        }
    }

    public Room findById(long id) {
        Session session = null;
        Transaction tr = null;
        Room room = null;
        try {
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();
            String sql = "SELECT * FROM ROOMS WHERE ID_ROOM = :id";
            Query query = session.createNativeQuery(sql).addEntity(Room.class);
            query.setParameter("id", id);
            try {
                room = (Room) query.getSingleResult();
                tr.commit();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        } catch (HibernateException e) {
            System.err.println("Something wrong. Method findByIdRoom().");
            System.out.println(e.getMessage());
            if (tr != null) {
                tr.rollback();
            }
        } finally {
            if (session != null)
                session.close();
        }
        return room;
    }

    public void update(Room room) {
        Session session = null;
        Transaction tr = null;
        try {
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();
            session.update(room);
            tr.commit();
            System.out.println("room id : " + room.getId() + " was updated");
        } catch (HibernateException e) {
            System.err.println("Something wrong. Method updateRoom().");
            System.out.println(e.getMessage());
            if (tr != null) {
                tr.rollback();
            }
        } finally {
            if (session != null)
                session.close();
        }
    }
}
