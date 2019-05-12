package lesson4_hw.controller;

import lesson4_hw.model.Order;
import lesson4_hw.service.OrderService;

public class OrderController implements ObjectController<Order>{
    OrderService orderService = new OrderService();

    @Override
    public Order save(Order order) {
        return orderService.save(order);
    }

    @Override
    public void delete(long id) {
        orderService.delete(id);
    }

    @Override
    public Order update(Order order) {
        return orderService.update(order);
    }

    @Override
    public Order findById(long id) {
        return orderService.findById(id);
    }
}
