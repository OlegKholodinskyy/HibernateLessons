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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HotelDAO extends SessionFactoryBuilder {

    public static Hotel findHotelByName(String name) {
        Hotel hotel = null;
        Transaction tr = null;
        try (Session session = createSessionFactory().openSession()) {
            tr = session.getTransaction();
            tr.begin();

            List<Hotel> hotels = findAllHotels();
            for(Hotel hotel1 : hotels){
                if(hotel1.getName().equals(name))
                    return hotel1;
            }

            tr.commit();

        } catch (HibernateException e) {
            System.out.println(e.getMessage());
            if (tr != null) {
                tr.rollback();
            }
        }
        return null;
    }

    public Hotel save(Hotel hotel) {
        Transaction tr = null;
        try (Session session = createSessionFactory().openSession()) {
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
        }
        return hotel;
    }

    public void delete(Hotel hotel)  {

        Transaction tr = null;
        try (Session session = createSessionFactory().openSession()) {
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
        }
    }

    public void update(Hotel hotel, Hotel hotelForUpdate) {

        Transaction tr = null;
        try (Session session = createSessionFactory().openSession()){
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
        }
    }

    public Hotel findById(long id) {
        Transaction tr = null;
        Hotel hotel = null;
        try (Session session = createSessionFactory().openSession()){
            tr = session.getTransaction();
            tr.begin();
            hotel = session.get(Hotel.class, id);
            tr.commit();
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
            if (tr != null) {
                tr.rollback();
            }
        }
        return hotel;
    }

    public List<Hotel> findHotelByCity(String city) {
        Transaction tr = null;
        List<Hotel> hotels = null;
        try (Session session = createSessionFactory().openSession()){
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
        }
        return hotels;
    }

    public static  List<Hotel> findAllHotels() {
        Transaction tr = null;
        List<Hotel> hotels = null;
        try (Session session = createSessionFactory().openSession()){
            tr = session.getTransaction();
            tr.begin();
            Query sqlQuery = session.createSQLQuery("SELECT * FROM HOTELS").addEntity(Hotel.class);
            hotels = sqlQuery.list();
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
            if (tr != null) {
                tr.rollback();
            }
        }
        return hotels;
    }

}
