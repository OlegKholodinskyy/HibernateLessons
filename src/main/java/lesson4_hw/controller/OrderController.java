package lesson4_hw.controller;

import lesson4_hw.Exception.PermissionException;
import lesson4_hw.model.Order;
import lesson4_hw.model.User;
import lesson4_hw.service.CurrentUser;
import lesson4_hw.service.OrderService;

public class OrderController implements ObjectController<Order>{
    OrderService orderService = new OrderService();

    @Override
    public Order save(Order order) {
        try {
            return orderService.save(order, CurrentUser.currentLoggedUser);
        } catch (PermissionException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public void delete(long id) {
        try {
            orderService.delete(id,CurrentUser.currentLoggedUser);
        } catch (PermissionException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Order update(Order order) {
        try {
            return orderService.update(order, CurrentUser.currentLoggedUser);
        } catch (PermissionException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public Order findById(long id) {
        return orderService.findById(id);
    }
}
