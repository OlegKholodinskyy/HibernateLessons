package lesson4_hw.service;

import lesson4_hw.Exception.BadRequestException;
import lesson4_hw.Exception.ObjectNotFoundInBDException;
import lesson4_hw.dao.HotelDAO;
import lesson4_hw.model.Hotel;

public class HotelService {
    HotelDAO hotelDAO = new HotelDAO();


    public Hotel save(Hotel hotel) throws BadRequestException {
        hotelDAO.checkIfHotelIsExist(hotel);
        return hotelDAO.save(hotel);
    }

    public void delete(long id) {
        try {
            hotelDAO.delete(id);
        } catch (ObjectNotFoundInBDException e) {
            System.out.println(e.getMessage());
        }
    }

    public Hotel update(Hotel hotel) {
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
}
