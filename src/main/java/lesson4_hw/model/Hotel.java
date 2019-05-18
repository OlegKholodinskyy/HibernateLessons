package lesson4_hw.model;

import javax.persistence.*;
import java.util.List;
@Entity
@Table(name = "HOTELS")
public class Hotel {
    @Id
    @SequenceGenerator(name = "Hotel_seq" , sequenceName = "HOTEL_SEQENSE")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Hotel_seq")
    @Column(name = "ID_HOTEL")
    private Long id;
    @Column(name = "HOTEL_NAME", nullable = false, unique = true)
    private String name;
    @Column(name = "HOTEL_COUNTRY", nullable = false)
    private String country;
    @Column(name = "HOTEL_CITY", nullable = false)
    private String city;
    @Column(name = "HOTEL_STREET", nullable = false)
    private String street;
    @OneToMany    //(cascade = CascadeType.ALL, mappedBy = "hotel" )
    private List<Room> rooms;

    public Hotel() {
    }

    public Hotel(Long id, String name, String country, String city, String street, List<Room> rooms) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.city = city;
        this.street = street;
        this.rooms = rooms;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                '}';
    }
}
