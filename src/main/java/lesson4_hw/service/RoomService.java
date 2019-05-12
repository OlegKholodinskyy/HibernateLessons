package lesson4_hw.service;

import lesson4_hw.Exception.ObjectNotFoundInBDException;
import lesson4_hw.dao.RoomDAO;
import lesson4_hw.model.Room;

import java.util.List;

public class RoomService {
    RoomDAO roomDAO = new RoomDAO();
    public Room save(Room room) {
        RoomDAO.save(room);
        return room;
    }

    public void delete(long id) {
        try {
            roomDAO.delete(id);
        } catch (ObjectNotFoundInBDException e) {
            System.out.println(e.getMessage());
        }
    }

    public Room update(Room room) {
        try {
            roomDAO.update(room);
        } catch (ObjectNotFoundInBDException e) {
            e.printStackTrace();
        }
        return room;
    }

    public Room findById(long id) {
        return roomDAO.findById(id);
    }

    public List<Room> getAllRooms() {
        return roomDAO.getAllRooms();
    }
}
