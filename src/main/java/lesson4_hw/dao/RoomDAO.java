package lesson4_hw.dao;

import lesson4_hw.Exception.ObjectNotFoundInBDException;
import lesson4_hw.model.Room;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.Date;
import java.util.List;

public class RoomDAO extends SessionFactoryBuilder{
    public static void save(Room room) {
        Session session = null;
        Transaction tr = null;
        try {
            session = SessionFactoryBuilder.createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();
            session.save(room);
            tr.commit();
            System.out.println("User with name: " + room + " saved");
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

    public static Room finByID(long roomId) {
        Session session = null;
        Transaction tr = null;
        Room room = null;
        try {
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();
            room = session.get(Room.class, roomId);
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
        return room;
    }

    public void delete(long id) throws ObjectNotFoundInBDException {
        Room room = finByID(id);
        if (room == null) {
            throw new ObjectNotFoundInBDException("Room id : " + id + "not found in Data Base");
        }
        Session session = null;
        Transaction tr = null;
        try {
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();
            session.delete(room);
            tr.commit();
            System.out.println("Room with ID: " + room.getId() + " was deleted");
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

    public void update(Room room) throws ObjectNotFoundInBDException {
            /*
            1-found room
            2-update if exist
             */
        Room roomForUpdate = finByID(room.getId());
        if (roomForUpdate == null) {
            throw new ObjectNotFoundInBDException("Room id : " + room.getId() + "not found in Data Base");
        }
        Session session = null;
        Transaction tr = null;
        try {
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();
            roomForUpdate.setPrice(room.getPrice());
            roomForUpdate.setNumberOfGuests(room.getNumberOfGuests());
            roomForUpdate.setDateAvailableFrom(room.getDateAvailableFrom());
            roomForUpdate.setPetsAllowed(room.getPetsAllowed());
            roomForUpdate.setBreakfastIncluded(room.getBreakfastIncluded());
            session.update(roomForUpdate);
            tr.commit();
            System.out.println("Room with ID: " + room.getId() + " was updated");
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

    public static void updateDateInOrderProcess(Room room, Date dateAvaibleFrom){
        Session session = null;
        Transaction tr = null;
        try {
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();
            room.setDateAvailableFrom(dateAvaibleFrom);
            session.update(room);
            tr.commit();
            System.out.println("Room with ID: " + room.getId() + " was updated");
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

    public Room findById(long id) {
        Session session = null;
        Transaction tr = null;
        Room room = null;
        try {
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();
            room = session.get(Room.class, id);
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
        return room;
    }
    public List<Room> getAllRooms() {
        Session session = null;
        Transaction tr = null;
        List<Room> rooms = null;
        try {
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();

            Query sqlQuery = session.createSQLQuery("SELECT * FROM ROOMS").addEntity(Room.class);
            rooms = sqlQuery.list();
            tr.commit();
        } catch (HibernateException e) {
            System.err.println("Something wrong during runing method \"getAllRooms\"");
            System.out.println(e.getMessage());
            if (tr != null) {
                tr.rollback();
            }
        } finally {
            if (session != null)
                session.close();
        }
        return rooms;
    }

    /*
find Rooms by filter:   city,
                        numberOfGuests,
                        price -  less than price,
                        dateAvailableFrom - more than dateAvailableFrom
 */
    public List<Room> findRooms(String city, Integer numberOfGuests, double price, Date dateAvailableFrom) {
        Session session =null;
        Transaction tr = null;
        List <Room> rooms =null;
        try {
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();
            Query sqlQuery = session.createSQLQuery("SELECT * FROM ROOMS " +
                    "INNER JOIN HOTELS ON HOTELS.ID_HOTEL = ROOMS.HOTEL_ID " +
                    "WHERE " +
                    "HOTELS.HOTEL_NAME = ? AND " +
                    "ROOM_NUMBER_OF_GUESTS = ? AND " +
                    "ROOM_PRICE < ? AND ROOM_DATE_AVIABLE_FROM < ?").addEntity(Room.class);
            sqlQuery.setParameter(0, city);
            sqlQuery.setParameter(1, numberOfGuests);
            sqlQuery.setParameter(2, price);
            sqlQuery.setParameter(3, dateAvailableFrom);

            rooms = sqlQuery.list();
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
            if (tr != null) {
                tr.rollback();
            }
        } finally {
            if (session != null)
                session.close();
        }
        return rooms;
    }
}
