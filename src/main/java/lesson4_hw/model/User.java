package lesson4_hw.model;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

import static javax.persistence.CascadeType.ALL;

@Entity
@Table(name = "USERS")
public class User {
    @Id
    @SequenceGenerator(name = "User_seq", sequenceName = "USER_SEQENSE")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "User_seq")
    @Column(name = "USER_ID", nullable = false, unique = true)
    private Long id;
    @Column(name = "USER_NAME", length = 50, nullable = false)
    private String userName;
    @Column(name = "USER_PASSWORD", length = 40, nullable = false)
    private String password;
    @Column(name = "USER_COUNTRY", length = 40)
    private String country;
    @Column(name = "USER_TYPE", length = 10, nullable = false)
    private UserType userType;
    @OneToMany(cascade = ALL, mappedBy = "userOrdered")
    @Column(name = "ID_ORDER")
    private List<Order> orders;

    public User() {
    }

    public User(String userName, String password, String country, UserType userType) {
        this.userName = userName;
        this.password = password;
        this.country = country;
        this.userType = userType;
    }

    public Long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userName, user.userName) &&
                Objects.equals(country, user.country);
    }

    @Override
    public int hashCode() {

        return Objects.hash(userName, country);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", country='" + country + '\'' +
                ", userType=" + userType +
                '}';
    }
}
