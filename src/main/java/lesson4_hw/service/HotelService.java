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
    Hotel hotel =null;
    public Hotel save(Hotel hotel, User user) throws BadRequestException, PermissionException {
        checkPermission(user);

        if (hotelDAO.findHotelByName(hotel.getName()) == null) {
            return hotelDAO.save(hotel);
        }
        throw new BadRequestException("Hotel with name = " + hotel.getName() + " is already registred.");
    }

    public void delete(long id, User user) throws PermissionException, ObjectNotFoundInBDException {
        checkPermission(user);
        hotel = findById(id);
        if (hotel != null) {
            hotelDAO.delete(hotel);
        }
        throw new ObjectNotFoundInBDException("Hotel id : " + id + "not found in Data Base");
    }

    public Hotel update(Hotel hotel, User user) throws PermissionException, ObjectNotFoundInBDException {
        checkPermission(user);

        Hotel hotelForUpdate = findById(hotel.getId());
        if (hotelForUpdate != null) {
            hotelDAO.update(hotel, hotelForUpdate);
            return hotel;
        }
        throw new ObjectNotFoundInBDException("Hotel id : " + hotel.getId() + "not found in Data Base");
    }

    public Hotel findById(long id) {
        return hotelDAO.findById(id);
    }

    private void checkPermission(User user) throws PermissionException {
        if (user.getUserType().equals("USER"))
            throw new PermissionException("User ID : " + user.getId() + "have not rights to make operation.");
    }
}
