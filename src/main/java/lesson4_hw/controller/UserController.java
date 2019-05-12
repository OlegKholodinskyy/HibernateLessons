package lesson4_hw.controller;

import lesson4_hw.Exception.BadRequestException;
import lesson4_hw.Exception.ObjectNotFoundInBDException;
import lesson4_hw.model.User;
import lesson4_hw.service.UserService;

public class UserController implements ObjectController<User> {
    UserService userService = new UserService();

    @Override
    public User save(User user) {
        try {
            userService.save(user);
        } catch (BadRequestException e) {
            System.out.println(e.getMessage());
        }
        return user;
    }

    @Override
    public void delete(long id) {
        userService.delete(id);
    }

    @Override
    public User update(User user) {
        return userService.update(user);
    }

    @Override
    public User findById(long id) {
        return userService.findById(id);
    }
}
