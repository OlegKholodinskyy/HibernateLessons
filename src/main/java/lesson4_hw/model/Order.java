package lesson4_hw.model;
import javax.persistence.*;
import java.util.Date;
@Entity
@Table(name = "ORDERS")
public class Order {
    @Id
    @SequenceGenerator(name = "Order_seq" , sequenceName = "ORDER_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Order_seq")
    @Column(name = "ORDER_ID", nullable = false, unique = true)
    private Long id;
    @ManyToOne
    @JoinColumn(name="USER_ID", nullable=false)
    private User userOrdered;
    @OneToOne
    @JoinColumn (name = "ROOM_ID")
    private Room room;
    @Column(name = "DATE_FROM", nullable = false)
    private Date dateFrom;
    @Column(name = "DATE_TO", nullable = false)
    private Date dateTo;
    @Column(name = "MONEY_PAID")
    private double moneyPaid;
    @Column(name = "AVIALABLE")
    private boolean avialable;

    public Order() {
    }

    public Order(User userOrdered, Room room, Date dateFrom, Date dateTo, double moneyPaid) {
        this.userOrdered = userOrdered;
        this.room = room;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.moneyPaid = moneyPaid;
        this.avialable = true;
    }

    public Long getId() {
        return id;
    }

    public User getUserOrdered() {
        return userOrdered;
    }

    public void setUserOrdered(User userOrdered) {
        this.userOrdered = userOrdered;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public double getMoneyPaid() {
        return moneyPaid;
    }

    public void setMoneyPaid(double moneyPaid) {
        this.moneyPaid = moneyPaid;
    }

    public boolean isAvialable() {
        return avialable;
    }

    public void setAvialable(boolean avialable) {
        this.avialable = avialable;
    }
}
