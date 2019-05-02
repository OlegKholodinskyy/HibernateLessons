package lesson3_hw.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ROOMS")
public class Room {
    /*
long id
int numberOfGuests
double price
int breakfastIncluded (1 или 0)
int petsAllowed (1 или 0)
Date dateAvailableFrom
Hotel hotel
     */
    @Id
    @SequenceGenerator(name = "ROOM_SEQ", sequenceName = "ROOM_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ROOM_SEQ")
    @Column(name = "ID_ROOM")
    private long id;
    @Column(name= "NUMBER_OF_GUESTS")
    private int numberOfGuests;
    @Column(name = "PRICE")
    private double price;
    @Column(name = "BREAKFAST_INCLUDED")
    private int breakfastIncluded;
    @Column(name = "PETS_ALLOWED")
    private int petsAllowed;
    @Column(name = "DATE_AVIABLE_FROM")
    private Date dateAvailableFrom;

    @OneToOne (cascade =  CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn (name = "HOTEL_ID")
    private Hotel hotel;

    public Room() {
    }

    public long getId() {
        return id;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public void setNumberOfGuests(int numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getBreakfastIncluded() {
        return breakfastIncluded;
    }

    public void setBreakfastIncluded(int breakfastIncluded) {
        this.breakfastIncluded = breakfastIncluded;
    }

    public int getPetsAllowed() {
        return petsAllowed;
    }

    public void setPetsAllowed(int petsAllowed) {
        this.petsAllowed = petsAllowed;
    }

    public Date getDateAvailableFrom() {
        return dateAvailableFrom;
    }

    public void setDateAvailableFrom(Date dateAvailableFrom) {
        this.dateAvailableFrom = dateAvailableFrom;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", numberOfGuests=" + numberOfGuests +
                ", price=" + price +
                ", breakfastIncluded=" + breakfastIncluded +
                ", petsAllowed=" + petsAllowed +
                ", dateAvailableFrom=" + dateAvailableFrom +
                ", hotel ID =" + hotel.getId() +
                '}';
    }
}
