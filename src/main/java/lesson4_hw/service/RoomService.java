package lesson4_hw.service;

import lesson4_hw.Exception.ObjectNotFoundInBDException;
import lesson4_hw.Exception.PermissionException;
import lesson4_hw.dao.RoomDAO;
import lesson4_hw.model.Room;
import lesson4_hw.model.User;

import java.util.List;

public class RoomService {
    RoomDAO roomDAO = new RoomDAO();
    public Room save(Room room, User user) throws PermissionException {
        checkPermission(user);
        RoomDAO.save(room);
        return room;
    }

    public void delete(long id, User user) throws PermissionException {
        checkPermission(user);
        try {
            roomDAO.delete(id);
        } catch (ObjectNotFoundInBDException e) {
            System.out.println(e.getMessage());
        }
    }

    public Room update(Room room, User user) throws PermissionException {
        checkPermission(user);
        try {
            roomDAO.update(room);
        } catch (ObjectNotFoundInBDException e) {
            e.printStackTrace();
        }
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
