package lesson3_hw;

import lesson3_hw.model.Hotel;
import lesson3_hw.model.Room;
import lesson3_hw.service.RoomService;

import java.util.Date;

public class Demo {
    public static void main(String[] args) {
        Hotel hotel1 = new Hotel();
        hotel1.setName("Some Hotel 11");
        hotel1.setCountry("Ukraine11");
        hotel1.setCity("Ternopil11");
        hotel1.setStreet("Shevchenka str.11");

        Hotel hotel2 = new Hotel();
        hotel2.setName("Some Hotel 22");
        hotel2.setCountry("USA22");
        hotel2.setCity("NewYork22");
        hotel2.setStreet("Shevchenka str.22");


        Room room_1 = new Room();
        room_1.setBreakfastIncluded(1);
        room_1.setDateAvailableFrom(new Date());
        room_1.setNumberOfGuests(3);
        room_1.setPetsAllowed(1);
        room_1.setPrice(100);
        room_1.setHotel(hotel1);

        Room room_2 = new Room();
        room_2.setBreakfastIncluded(1);
        room_2.setDateAvailableFrom(new Date());
        room_2.setNumberOfGuests(5);
        room_2.setPetsAllowed(1);
        room_2.setPrice(120);
        room_2.setHotel(hotel2);
/*
        RoomService.save(room_1);
        RoomService.save(room_2);

        System.out.println(HotelService.findById(2));

        Room room = RoomService.findById(2);
        System.out.println(room);
        room.setNumberOfGuests(100);
        RoomService.update(room);
        System.out.println(RoomService.findById(2));
*/
  //      RoomService.delete(2);
        System.out.println(RoomService.findById(2));


    }
}
