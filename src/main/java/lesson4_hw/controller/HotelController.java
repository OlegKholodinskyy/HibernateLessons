package lesson4_hw.controller;

import lesson4_hw.Exception.BadRequestException;
import lesson4_hw.Exception.PermissionException;
import lesson4_hw.model.Hotel;
import lesson4_hw.model.User;
import lesson4_hw.service.CurrentUser;
import lesson4_hw.service.HotelService;

public class HotelController implements ObjectController<Hotel> {
    HotelService hotelService = new HotelService();

    @Override
    public  Hotel  save(Hotel hotel) {
        try {
            return hotelService.save(hotel, CurrentUser.currentLoggedUser);
        } catch (BadRequestException e) {
            System.out.println(e.getMessage());
        } catch (PermissionException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public void delete(long id) {
        try {
            hotelService.delete(id, CurrentUser.currentLoggedUser);
        } catch (PermissionException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Hotel update(Hotel hotel) {
        try {
            return hotelService.update(hotel, CurrentUser.currentLoggedUser);
        } catch (PermissionException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    //  this operation user can do without login
    @Override
    public Hotel findById(long id) {
        return hotelService.findById(id);
    }
}
