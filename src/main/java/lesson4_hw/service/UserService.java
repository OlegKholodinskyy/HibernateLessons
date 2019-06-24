package lesson4_hw.service;

import lesson4_hw.Exception.BadRequestException;
import lesson4_hw.Exception.ObjectNotFoundInBDException;
import lesson4_hw.Exception.PermissionException;
import lesson4_hw.dao.HotelDAO;
import lesson4_hw.dao.OrderDAO;
import lesson4_hw.dao.RoomDAO;
import lesson4_hw.dao.UserDAO;
import lesson4_hw.model.Hotel;
import lesson4_hw.model.Order;
import lesson4_hw.model.Room;
import lesson4_hw.model.User;

import java.util.Date;
import java.util.List;

public class UserService {
    UserDAO userDAO = new UserDAO();
    HotelDAO hotelDAO = new HotelDAO();
    RoomDAO roomDAO = new RoomDAO();
    OrderDAO orderDAO = new OrderDAO();

    public User save(User user) throws BadRequestException {

       if (userDAO.checkIfUserNameisExist(user.getUserName())){
           throw new BadRequestException("User with name " + user.getUserName() + " is exist");
       }
        userDAO.save(user);
        return user;
    }

    private void cheskIfExist(Long id) throws ObjectNotFoundInBDException {
        if (findById(id) == null) {
            throw new ObjectNotFoundInBDException("User id : " + id + "not found in Data Base");
        }
    }

    public void delete(long id) throws ObjectNotFoundInBDException {
        cheskIfExist(id);
        userDAO.delete(findById(id));

    }

    public User update(User user) throws ObjectNotFoundInBDException {
        cheskIfExist(user.getId());
        userDAO.update(user);
        return user;
    }

    public User findById(long id) {
        return userDAO.findById(id);
    }

    public Hotel findHoteByName(String name) {
        return hotelDAO.findHotelByName(name);
    }

    public List<Hotel> findHotelByCity(String city) {
        return hotelDAO.findHotelByCity(city);
    }

    public List<Room> findRooms(String city, Integer numberOfGuests, double price, Date date) {
        return roomDAO.findRooms(city, numberOfGuests, price, date);
    }

    public void bookRoom(long roomId, long userId, Date dateFrom, Date dateTo, double moneyPaid) throws ObjectNotFoundInBDException, BadRequestException {

        Room currentRoom = roomDAO.finByID(roomId);
        if (currentRoom == null) {
            throw new ObjectNotFoundInBDException(" Room ID :" + roomId + " not found in DataBase");
        }
        if (currentRoom.getDateAvailableFrom().getTime() > dateFrom.getTime()) {
            throw new BadRequestException(" Room ID :" + roomId + " not avialable in order period");
        }
        if (orderDAO.findOrderByRoomIDUserID(roomId, userId) != null) {
            throw new BadRequestException("Orders with USER_ID " + userId + " and ROOM_ID " + roomId + " now is active in DB. You can not order the same room multiple times. You should to cancel previous order first");
        }


        Order order = new Order();
        order.setDateFrom(dateFrom);
        order.setDateTo(dateTo);
        order.setMoneyPaid(moneyPaid);
        order.setUserOrdered(userDAO.findById(userId));
        order.setRoom(currentRoom);
        order.setAvialable(true);

        orderDAO.bookRoom(order, dateTo);
    }

    public void cancelReservation(long roomId, long userId) throws BadRequestException, ObjectNotFoundInBDException {
        // 1 -find list orders by userID and roomID (if one user can order one room multiple times -> throw exception)
        // 2 - check if  date_From is more than current date
        // 3 -find dateAaviableFrom room before this order
        // 4 - delete order
        // 5 - change dateAaviableFrom in room

        Order order = orderDAO.findOrderByRoomIDUserID(roomId, userId);

        if (order == null) {
            throw new BadRequestException("Order with USER_ID " + userId + " and ROOM_ID " + roomId + "not found in DB");
        }

        if (curentDateIsMoreOrderDateCheck(order.getDateFrom())) {
            throw new BadRequestException("You can`t cancel order because date order is equal current date or date has passed\n");
        }

        // 3 -find dateAaviableFrom object Room before this order
        Date previousDateAvialable = orderDAO.findDateAvialableRoomBeforeCurrentOrder(order);
        Date currentDate = new Date();
        //del Order + modify Room
        if (previousDateAvialable.getTime() < currentDate.getTime()) {
            previousDateAvialable = currentDate;
        }
        orderDAO.cancelOrder(order, roomId, previousDateAvialable);

    }

    private boolean curentDateIsMoreOrderDateCheck(Date dateFrom) {
        Date currentDate = new Date();
        if (currentDate.getTime() >= dateFrom.getTime()) {
            return true;
        }
        return false;
    }

    public void login(String userName, String password) throws BadRequestException {
        User user = userDAO.getUserByLoginAndPass(userName, password);
        if (user != null) {
            CurrentUser.currentLoggedUser = user;
        }
    }

    private void checkPermission(User user) throws PermissionException {
        if (user.getUserType().equals("USER"))
            throw new PermissionException("User ID : " + user.getId() + "have not rights to make operation.");
    }

    public void logout() {
        CurrentUser.currentLoggedUser = null;
        System.out.println("User is logged OUT");
    }
}
