package lesson4_hw.service;

import lesson4_hw.Exception.BadRequestException;
import lesson4_hw.Exception.ObjectNotFoundInBDException;
import lesson4_hw.dao.UserDAO;
import lesson4_hw.model.User;

public class UserService {
    UserDAO userDAO = new UserDAO();

    public User save(User user) throws BadRequestException {
        // 1-check if user is exist (by name);
        // 2-save;
        userDAO.checkIfUserNameisExist(user.getUserName());
        userDAO.save(user);
        return user;
    }


    public void delete(long id) {
        try {
            userDAO.delete(id);
        } catch (ObjectNotFoundInBDException e) {
            System.out.println(e.getMessage());
        }
    }

    public User update(User user) {
        try {
            userDAO.update(user);
        } catch (ObjectNotFoundInBDException e) {
            e.printStackTrace();
        }
        return user;
    }

    public User findById(long id) {
        return userDAO.findById(id);
    }
}
