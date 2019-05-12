package lesson4_hw.controller;

import lesson4_hw.model.Room;
import lesson4_hw.service.RoomService;

import java.util.List;

public class RoomController implements ObjectController<Room> {
    RoomService roomService = new RoomService();

    @Override
    public Room save(Room room) {
        return roomService.save(room);
    }

    @Override
    public void delete(long id) {
        roomService.delete(id);
    }

    @Override
    public Room update(Room room) {
        return roomService.update(room);
    }

    @Override
    public Room findById(long id) {
        return roomService.findById(id);
    }

    public List<Room> getAllRooms() {
        return roomService.getAllRooms();
    }
}
