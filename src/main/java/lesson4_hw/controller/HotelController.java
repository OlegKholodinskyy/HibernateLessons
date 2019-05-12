package lesson4_hw.controller;
import lesson4_hw.model.Hotel;
import lesson4_hw.service.HotelService;

public class HotelController implements ObjectController<Hotel> {
    HotelService hotelService = new HotelService();
    @Override
    public Hotel save(Hotel hotel) {
        return hotelService.save(hotel);
    }

    @Override
    public void delete(long id) {
        hotelService.delete(id);
    }

    @Override
    public Hotel update(Hotel hotel) {
        return hotelService.update(hotel);
    }

    @Override
    public Hotel findById(long id) {
        return hotelService.findById(id);
    }
}
