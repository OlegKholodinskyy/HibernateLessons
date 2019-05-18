package lesson4_hw.controller;

import lesson4_hw.Exception.BadRequestException;
import lesson4_hw.Exception.ObjectNotFoundInBDException;
import lesson4_hw.model.Hotel;
import lesson4_hw.model.Room;
import lesson4_hw.model.User;
import lesson4_hw.service.CurrentUser;
import lesson4_hw.service.UserService;

import java.util.Date;
import java.util.List;

public class UserController implements ObjectController<User> {
    UserService userService = new UserService();
    CurrentUser currentUser = new CurrentUser();

    @Override
    public User save(User user) {
        try {
            userService.save(user);
        } catch (BadRequestException e) {
            System.out.println(e.getMessage());
        }
        return user;
    }

    @Override
    public void delete(long id) {
        userService.delete(id);
    }

    @Override
    public User update(User user) {
        return userService.update(user);
    }

    @Override
    public User findById(long id) {
        return userService.findById(id);
    }

    public Hotel findHoteByName(String name) {
        return userService.findHoteByName(name);
    }

    public List<Hotel> findHotelByCity(String city) {
        return userService.findHotelByCity(city);
    }

    public List<Room> findRooms(String city, Integer numberOfGuests, double price, Date date) {
        return userService.findRooms(city, numberOfGuests, price, date);
    }

    public void bookRoom(long roomId, long userId, Date dateFrom, Date dateTo, double moneyPaid) {
        try {
            userService.bookRoom(roomId, userId, dateFrom, dateTo, moneyPaid);
        } catch (ObjectNotFoundInBDException e) {
            System.out.println(e.getMessage());
        } catch (BadRequestException e) {
            System.out.println(e.getMessage());
        }
    }

    public void login(String userName, String password) {
        try {
            userService.login(userName, password);
        } catch (BadRequestException e) {
            e.printStackTrace();
        }
    }

    public void cancelReservation(long roomId, long userId) {
        try {
            userService.cancelReservation(roomId, userId);
        } catch (BadRequestException e) {
            System.out.println(e.getMessage());
        } catch (ObjectNotFoundInBDException e) {
            System.out.println(e.getMessage());
        }
    }

    public User registerUser(User user) {
        save(user);
        return user;
    }

    public void logout(){
        userService.logout();
    }
}
