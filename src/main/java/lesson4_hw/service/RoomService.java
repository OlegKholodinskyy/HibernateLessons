package lesson4_hw.service;

import lesson4_hw.Exception.ObjectNotFoundInBDException;
import lesson4_hw.Exception.PermissionException;
import lesson4_hw.dao.RoomDAO;
import lesson4_hw.model.Room;
import lesson4_hw.model.User;

import java.util.List;

public class RoomService {
    RoomDAO roomDAO = new RoomDAO();
    Room room = null;

    public Room save(Room room, User user) throws PermissionException {
        checkPermission(user);
        RoomDAO.save(room);
        return room;
    }

    public void delete(long id, User user) throws PermissionException, ObjectNotFoundInBDException {
        checkPermission(user);
        room = roomDAO.findById(id);
        if (room == null) {
            throw new ObjectNotFoundInBDException("Room id : " + id + "not found in Data Base");
        }
        roomDAO.delete(room);
    }

    public Room update(Room room, User user) throws PermissionException, ObjectNotFoundInBDException {
        checkPermission(user);

        Room roomForUpdate = roomDAO.finByID(room.getId());
        if (roomForUpdate == null) {
            throw new ObjectNotFoundInBDException("Room id : " + room.getId() + "not found in Data Base");
        }
        roomDAO.update(room);
        return room;
    }

    public Room findById(long id) {
        return roomDAO.finByID(id);
    }

    public List<Room> getAllRooms() {
        return roomDAO.getAllRooms();
    }

    private void checkPermission(User user) throws PermissionException {
        if (user.getUserType().equals("USER"))
            throw new PermissionException("User ID : " + user.getId() + "have not rights to make operation.");
    }
}
