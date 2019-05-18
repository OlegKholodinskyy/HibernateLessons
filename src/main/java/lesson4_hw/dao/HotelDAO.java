package lesson4_hw.dao;

import lesson4_hw.Exception.BadRequestException;
import lesson4_hw.Exception.ObjectNotFoundInBDException;
import lesson4_hw.model.Hotel;
import lesson4_hw.model.Room;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.NoResultException;
import java.util.Date;
import java.util.List;

public class HotelDAO extends SessionFactoryBuilder {

    public static Hotel findHotelByName(String name) {
        Session session = null;
        Transaction tr = null;
        Hotel hotel = null;
        try {
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();
            Query sqlQuery = session.createSQLQuery("SELECT * FROM HOTELS where  HOTEL_NAME =?").addEntity(Hotel.class);
            sqlQuery.setParameter(0, name);
            try {
                hotel = (Hotel) sqlQuery.getSingleResult();
                tr.commit();
            }catch (NoResultException e){
                return null;
            }
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        } finally {
            if (session != null)
                session.close();
        }
        return hotel;
    }

    public Hotel save(Hotel hotel) {
        Session session = null;
        Transaction tr = null;
        try {
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();
            session.save(hotel);
            tr.commit();
            System.out.println("Hotel with name: " + hotel.getName() + " saved");
        } catch (HibernateException e) {
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

    public void delete(long id) throws ObjectNotFoundInBDException {
        Hotel hotel = findById(id);
        if (hotel == null) {
            throw new ObjectNotFoundInBDException("Hotel id : " + id + "not found in Data Base");
        }
        Session session = null;
        Transaction tr = null;
        try {
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();
            session.delete(hotel);
            tr.commit();
            System.out.println("Hotel with ID: " + hotel.getId() + " was deleted");
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

    public void update(Hotel hotel) throws ObjectNotFoundInBDException {
            /*
            1-found hotel
            2-update if exist
             */
        Hotel hotelForUpdate = findById(hotel.getId());
        if (hotelForUpdate == null) {
            throw new ObjectNotFoundInBDException("Hotel id : " + hotel.getId() + "not found in Data Base");
        }
        Session session = null;
        Transaction tr = null;
        try {
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();
            hotelForUpdate.setStreet(hotel.getStreet());
            hotelForUpdate.setCountry(hotel.getCountry());
            hotelForUpdate.setCity(hotel.getCity());
            hotelForUpdate.setName(hotel.getName());
            hotelForUpdate.setRooms(hotel.getRooms());
            session.update(hotelForUpdate);
            tr.commit();
            System.out.println("Hotel with name: " + hotel.getName() + " was updated");
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

    public Hotel findById(long id) {
        Session session = null;
        Transaction tr = null;
        Hotel hotel = null;
        try {
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();
            hotel = session.get(Hotel.class, id);
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
        return hotel;
    }

    public List<Hotel> findHotelByCity(String city) {
        Session session =null;
        Transaction tr = null;
        List <Hotel> hotels =null;
        try {
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();
            Query sqlQuery = session.createSQLQuery("SELECT * FROM HOTELS where  HOTEL_CITY =?").addEntity(Hotel.class);
            sqlQuery.setParameter(0, city);
            hotels = sqlQuery.list();
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
            if (tr != null) {
                tr.rollback();
            }
        } finally {
            if (session != null)
                session.close();
        }
        return hotels;
    }


}
