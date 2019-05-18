package lesson4_hw.service;

import lesson4_hw.Exception.BadRequestException;
import lesson4_hw.Exception.ObjectNotFoundInBDException;
import lesson4_hw.Exception.PermissionException;
import lesson4_hw.dao.HotelDAO;
import lesson4_hw.dao.UserDAO;
import lesson4_hw.model.Hotel;
import lesson4_hw.model.User;

public class HotelService {
    HotelDAO hotelDAO = new HotelDAO();
    UserDAO userDAO = new UserDAO();

    public Hotel save(Hotel hotel, User user) throws BadRequestException, PermissionException {
        checkPermission(user);

        if (hotelDAO.findHotelByName(hotel.getName()) == null) {
            return hotelDAO.save(hotel);
        }
        throw new BadRequestException("Hotel with name = " + hotel.getName() + " is already registred.");
    }

    public void delete(long id, User user) throws PermissionException {
        checkPermission(user);
        try {
            hotelDAO.delete(id);
        } catch (ObjectNotFoundInBDException e) {
            System.out.println(e.getMessage());
        }
    }

    public Hotel update(Hotel hotel,User user) throws PermissionException {
        checkPermission(user);
        try {
            hotelDAO.update(hotel);
        } catch (ObjectNotFoundInBDException e) {
            e.printStackTrace();
        }
        return hotel;
    }

    public Hotel findById(long id) {
        return hotelDAO.findById(id);
    }

    private void checkPermission(User user) throws PermissionException {
        if (user.getUserType().equals("USER") )
            throw new PermissionException("User ID : " + user.getId() + "have not rights to make operation.");
    }
}
