package lesson4_hw.dao;

import lesson4_hw.Exception.BadRequestException;
import lesson4_hw.Exception.ObjectNotFoundInBDException;
import lesson4_hw.model.Hotel;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.NoResultException;

public class HotelDAO extends SessionFactoryBuilder {
    public Hotel save(Hotel hotel) {
        Session session = null;
        Transaction tr = null;
        try {
            session = SessionFactoryBuilder.createSessionFactory().openSession();
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

    public void checkIfHotelIsExist(Hotel hotel) throws BadRequestException {
        Session session = null;
        Transaction tr = null;
        Hotel hotel1 = null;
        try {
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();
            Query sqlQuery = session.createSQLQuery("SELECT * FROM HOTELS WHERE HOTEL_NAME =? AND HOTEL_CITY= ?").addEntity(Hotel.class);
            sqlQuery.setParameter(0, hotel.getName());
            sqlQuery.setParameter(1, hotel.getCity());
            try {
                hotel1 = (Hotel) sqlQuery.getSingleResult();
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
        throw new BadRequestException("Hotel with name = " + hotel.getName() + "and city = " + hotel.getCity()+ "  is already registred.");
    }

}
