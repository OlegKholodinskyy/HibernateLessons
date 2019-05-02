package lesson3_hw.repository;

import lesson3_hw.model.Hotel;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import javax.persistence.Query;

public class HotelDAO {
    static SessionFactory sessionFactory = null;

    public static SessionFactory createSessionFactory() {
        // singleton
        if (sessionFactory == null) {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        }
        return sessionFactory;
    }

    public static void save(Hotel hotel) {
        Session session = null;
        Transaction tr = null;
        try {
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();
            session.save(hotel);
            tr.commit();
            System.out.println("hotel id: " + hotel.getId() + " saved");
        } catch (HibernateException e) {
            System.err.println("Something wrong. Method SaveHotel.");
            System.out.println(e.getMessage());
            if (tr != null) {
                tr.rollback();
            }
        } finally {
            if (session != null)
                session.close();
        }
    }

    public void delete(long id) throws Exception {

        Hotel hotel = findById(id);

        if (hotel == null) {
            System.out.println("Nothing delete.");
            return;
        }

        Session session = null;
        Transaction tr = null;
        try {
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();
            session.delete(hotel);
            tr.commit();
            System.out.println("hotel was deleted");
        } catch (HibernateException e) {
            System.err.println("Something wrong. Method deleteHotel.");
            System.out.println(e.getMessage());
            if (tr != null) {
                tr.rollback();
            }
        } finally {
            if (session != null)
                session.close();
        }
    }

    public Hotel findById(long id) {
        Session session = null;
        Transaction tr = null;
        Hotel hotel = null;
        try {
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();
            String sql = "SELECT * FROM HOTELS WHERE ID_HOTEL = :id";
            Query query = session.createNativeQuery(sql).addEntity(Hotel.class);
            query.setParameter("id", id);
            try {
                hotel = (Hotel) query.getSingleResult();
                tr.commit();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        } catch (HibernateException e) {
            System.err.println("Something wrong. Method findByIdHotel.");
            System.out.println(e.getMessage());
            if (tr != null) {
                tr.rollback();
            }
        } finally {
            if (session != null)
                session.close();
        }
        return hotel;
    }

    public void update(Hotel hotel) {
        Session session = null;
        Transaction tr = null;
        try {
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();
            session.update(hotel);
            tr.commit();
            System.out.println("hotel id: " + hotel.getId() + " was updated");
        } catch (HibernateException e) {
            System.err.println("Something wrong. Method updateHotel.");
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
