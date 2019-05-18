package lesson4_hw.model;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
@Entity
@Table(name = "ROOMS")
public class Room {
    @Id
    @SequenceGenerator(name = "Room_seq" , sequenceName = "ROOM_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Room_seq")
    @Column(name = "ROOM_ID", nullable = false, unique = true)
    private Long id;
    @Column(name = "ROOM_NUMBER_OF_GUESTS", nullable = false)
    private Integer numberOfGuests;
    @Column(name = "ROOM_PRICE", nullable = false)
    private double price;
    @Column(name = "ROOM_BREAKFAST_INCLUDED")
    private boolean breakfastIncluded;
    @Column(name = "ROOM_RETS_ALLOVED")
    private boolean petsAllowed;
    @Temporal(TemporalType.DATE)
    @Column(name = "ROOM_DATE_AVIABLE_FROM", nullable = false)
    private Date dateAvailableFrom;
    //@ManyToOne (cascade = CascadeType.ALL)
    @ManyToOne
    @JoinColumn(name="HOTEL_ID", nullable = false)
    private Hotel hotel;

    public Room() {
    }

    public Room(Integer numberOfGuests, double price, boolean breakfastIncluded, boolean petsAllowed, Date dateAvailableFrom, Hotel hotel) {
        this.numberOfGuests = numberOfGuests;
        this.price = price;
        this.breakfastIncluded = breakfastIncluded;
        this.petsAllowed = petsAllowed;
        this.dateAvailableFrom = dateAvailableFrom;
        this.hotel = hotel;
    }

    public Long getId() {
        return id;
    }

    public Integer getNumberOfGuests() {
        return numberOfGuests;
    }

    public void setNumberOfGuests(Integer numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean getBreakfastIncluded() {
        return breakfastIncluded;
    }

    public void setBreakfastIncluded(boolean breakfastIncluded) {
        this.breakfastIncluded = breakfastIncluded;
    }

    public boolean getPetsAllowed() {
        return petsAllowed;
    }

    public void setPetsAllowed(boolean petsAllowed) {
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
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd");


        return "Room{" +
                "id= " + id +
                ", numberOfGuests= " + numberOfGuests +
                ", price= " + price +
                ", breakfastIncluded= " + breakfastIncluded +
                ", petsAllowed= " + petsAllowed +
                ", dateAvailableFrom= " + sdf.format(dateAvailableFrom)+
                ", hotel= " + hotel +
                '}';
    }
}
