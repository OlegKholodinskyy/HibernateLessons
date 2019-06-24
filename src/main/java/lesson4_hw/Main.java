package lesson4_hw;

import lesson4_hw.controller.HotelController;
import lesson4_hw.controller.OrderController;
import lesson4_hw.controller.RoomController;
import lesson4_hw.controller.UserController;
import lesson4_hw.model.*;
import lesson4_hw.service.CurrentUser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Main {

    public static void main(String[] args) {
        HotelController hotelController = new HotelController();
        RoomController roomController = new RoomController();
        UserController userController = new UserController();
        OrderController orderController = new OrderController();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");


        userController.login("IVAN", "xxx");
        System.out.println(CurrentUser.currentLoggedUser);
//        Hotel hotel = new Hotel();
//        hotel.setName("7-TestHotel");
//        hotel.setCity("7--TORONTO");
//        hotel.setCountry("7-TestHotel");
//        hotel.setStreet("7-TestHotel str.");
//        hotelController.save(hotel);
        userController.logout();


//        Hotel hotel2 = new Hotel();
//        hotel2.setName("4-TestHotel");
//        hotel2.setCity("TORONTO");
//        hotel2.setCountry("TestHotel");
//        hotel2.setStreet("TestHotel str.");
//        hotelController.save(hotel2);

//        User user = new User();
//        user.setUserName("Stepan");
//        user.setCountry("UAS");
//        user.setPassword("xxx");
//        user.setUserType(UserType.USER);
//        userController.save(user);
//
//        Room room = new Room();
//        room.setBreakfastIncluded(true);
//        room.setPetsAllowed(true);
//        room.setDateAvailableFrom(new Date());
//        room.setNumberOfGuests(5);
//        room.setPrice(300);
//        room.setHotel(hotelController.findById(93));
//        roomController.save(room);
//
//        Order order = new Order();
//        order.setDateFrom(new Date());
//        order.setDateTo(new Date());
//        order.setMoneyPaid(100);
//        order.setUserOrdered(user);
//        order.setRoom(room);
//        orderController.save(order);

//        userController.delete(73);
//        System.out.println("Delete done");
//
        User testUser = userController.findById(1);
        System.out.println("Find by ID: " + testUser.toString());
        testUser.setUserName("ChangedUserName-Oleg");
        userController.update(testUser);

//        Room testRoom = roomController.findById(57);
//        System.out.println("Find by ID: " + testRoom.toString());
//        testRoom.setPrice(999);
//        roomController.update(testRoom);
//        Room testRoomAfter = roomController.findById(72);
//        System.out.println("Find by ID: " + testRoomAfter.toString());

        //     System.out.println( userController.findHoteByName("TestHotelBAD"));
        // System.out.println( userController.findHoteByName("TestHotel"));
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.mm.yyyy");
//        System.out.println(simpleDateFormat.format(new Date(2019-05-10)));
//        Date dateFrom = null;
//        try {
//            dateFrom = simpleDateFormat.parse("12.05.2018");
//            System.out.println(userController.findRooms("2-TestHotel", 5, 1100, dateFrom));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//        Date dateFrom = null;
//        Date dateTo = null;
//        try {
//            dateFrom = simpleDateFormat.parse("20.07.2032");
//            dateTo = simpleDateFormat.parse("21.07.2032");
//            userController.bookRoom(72,51, dateFrom, dateTo, 1200);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
           userController.cancelReservation(176,92);

//        userController.login("IVAN", "xxx");
//        System.out.println(CurrentUser.currentLoggedUser);
//        userController.logout();
//        System.out.println(CurrentUser.currentLoggedUser == null);

        //   userController.cancelReservation(72,51);

//        try {
//            Date dateFrom = null;
//            Date dateTo = null;
//            dateFrom = simpleDateFormat.parse("17.05.2019");
//            dateTo = simpleDateFormat.parse("21.05.2019");
//            userController.bookRoom(76, 91, dateFrom,dateTo, 400);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }

//        userController.cancelReservation(76,91);
    }
}
