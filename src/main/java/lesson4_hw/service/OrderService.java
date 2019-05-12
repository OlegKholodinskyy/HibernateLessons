package lesson4_hw.service;

import com.sun.org.apache.xpath.internal.operations.Or;
import lesson4_hw.Exception.ObjectNotFoundInBDException;
import lesson4_hw.dao.OrderDAO;
import lesson4_hw.model.Order;

public class OrderService {
    OrderDAO orderDAO = new OrderDAO();
    public Order save(Order order) {
        return orderDAO.save(order);
    }


    public void delete(long id) {
        try {
            orderDAO.delete(id);
        } catch (ObjectNotFoundInBDException e) {
            System.out.println(e.getMessage());
        }
    }

    public Order update(Order order) {
        try {
            orderDAO.update(order);
        } catch (ObjectNotFoundInBDException e) {
            e.printStackTrace();
        }
        return order;
    }

    public Order findById(long id) {
        return orderDAO.findById(id);
    }
}
