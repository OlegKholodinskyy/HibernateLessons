package lesson4_hw.service;

import com.sun.org.apache.xpath.internal.operations.Or;
import lesson4_hw.Exception.ObjectNotFoundInBDException;
import lesson4_hw.Exception.PermissionException;
import lesson4_hw.dao.OrderDAO;
import lesson4_hw.model.Order;
import lesson4_hw.model.User;

public class OrderService {
    OrderDAO orderDAO = new OrderDAO();
    public Order save(Order order, User user) throws PermissionException {
        checkIfUserLoggedIn(user);
        return orderDAO.save(order);
    }

    private void checkIfUserLoggedIn(User user) throws PermissionException {
        if(CurrentUser.currentLoggedUser == null)
            throw  new PermissionException("User is not logged in");
    }


    public void delete(long id, User user) throws PermissionException {
        checkIfUserLoggedIn(user);
        try {
            orderDAO.delete(id);
        } catch (ObjectNotFoundInBDException e) {
            System.out.println(e.getMessage());
        }
    }

    public Order update(Order order,User user) throws PermissionException {
        checkIfUserLoggedIn(user);
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
