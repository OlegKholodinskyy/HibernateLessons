package lesson4_hw.dao;

import lesson4_hw.Exception.ObjectNotFoundInBDException;
import lesson4_hw.model.Room;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

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

    public void delete(long id) throws ObjectNotFoundInBDException {
        Room room = findById(id);
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
        Room roomForUpdate = findById(room.getId());
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
}
