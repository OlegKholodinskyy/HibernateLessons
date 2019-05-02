package lesson3_hw.service;


import lesson3_hw.model.Room;
import lesson3_hw.repository.RoomDAO;

public class RoomService {
    static RoomDAO roomDAO = new RoomDAO();

    public static void save(Room room) {
        roomDAO.save(room);
    }

    public static void delete(long id) {
        try {
            roomDAO.delete(id);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public static Room findById(long id) {
        return roomDAO.findById(id);
    }

    public static void update(Room room) {
        roomDAO.update(room);
    }
}
