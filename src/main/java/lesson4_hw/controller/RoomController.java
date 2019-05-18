package lesson4_hw.controller;

import lesson4_hw.Exception.PermissionException;
import lesson4_hw.model.Room;
import lesson4_hw.model.User;
import lesson4_hw.service.CurrentUser;
import lesson4_hw.service.RoomService;

import java.util.List;

public class RoomController implements ObjectController<Room> {
    RoomService roomService = new RoomService();

    @Override
    public Room save(Room room) {
        try {
            return roomService.save(room,CurrentUser.currentLoggedUser);
        } catch (PermissionException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public void delete(long id) {
        try {
            roomService.delete(id,CurrentUser.currentLoggedUser);
        } catch (PermissionException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Room update(Room room) {
        try {
            return roomService.update(room,CurrentUser.currentLoggedUser);
        } catch (PermissionException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public Room findById(long id) {
        return roomService.findById(id);
    }

    public List<Room> getAllRooms() {
        return roomService.getAllRooms();
    }
}
