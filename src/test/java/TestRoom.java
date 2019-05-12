import lesson4_hw.controller.RoomController;
import lesson4_hw.model.Hotel;
import lesson4_hw.model.Room;
import org.junit.Assert;
import org.junit.Test;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

public class TestRoom {
    RoomController roomController = new RoomController();
    @Test
    @Transactional
    public void testAddDepartment()
    {

        Hotel hotel = new Hotel();
        hotel.setName("TestHotel");
        hotel.setCity("TestHotel");
        hotel.setCountry("TestHotel");
        hotel.setStreet("TestHotel str.");

        Room room = new Room();
        room.setBreakfastIncluded(true);
        room.setPetsAllowed(true);
        room.setDateAvailableFrom(new Date());
        room.setNumberOfGuests(10);
        room.setPrice(1000);
        room.setHotel(hotel);
        roomController.save(room);

        List<Room> rooms = roomController.getAllRooms();
        Assert.assertEquals(1, rooms.size());
    }
}
