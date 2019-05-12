package lesson4_hw;

import lesson4_hw.controller.HotelController;
import lesson4_hw.controller.OrderController;
import lesson4_hw.controller.RoomController;
import lesson4_hw.controller.UserController;
import lesson4_hw.model.*;

import java.util.Date;

public class Main {

    public static void main(String[] args) {
        HotelController hotelController = new HotelController();
        RoomController roomController = new RoomController();
        UserController userController = new UserController();
        OrderController orderController = new OrderController();

//        Hotel hotel = new Hotel();
//        hotel.setName("TestHotel");
//        hotel.setCity("TestHotel");
//        hotel.setCountry("TestHotel");
//        hotel.setStreet("TestHotel str.");
//        hotelController.save(hotel);

//        User user = new User();
//        user.setUserName("UserTestNew");
//        user.setCountry("SomeCountry");
//        user.setPassword("xxx");
//        user.setUserType(UserType.USER);
//        userController.save(user);

//        Room room = new Room();
//        room.setBreakfastIncluded(true);
//        room.setPetsAllowed(true);
//        room.setDateAvailableFrom(new Date());
//        room.setNumberOfGuests(5);
//        room.setPrice(1000);
//        room.setHotel(hotel);
//        roomController.save(room);

    /*    Order order = new Order();
        order.setDateFrom(new Date());
        order.setDateTo(new Date());
        order.setMoneyPaid(100);
        order.setUserOrdered(user);
        order.setRoom(room);
        orderController.save(order);
*/
//        userController.delete(73);
//        System.out.println("Delete done");
//
//        User testUser = userController.findById(52);
//        System.out.println("Find by ID: " + testUser.toString());
//        testUser.setUserName("ChangedUserName");
//        userController.update(testUser);

//        Room testRoom = roomController.findById(57);
//        System.out.println("Find by ID: " + testRoom.toString());
//        testRoom.setPrice(999);
//        roomController.update(testRoom);
//        Room testRoomAfter = roomController.findById(57);
//        System.out.println("Find by ID: " + testRoomAfter.toString());

    }
}
