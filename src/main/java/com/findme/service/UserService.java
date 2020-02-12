package com.findme.service;

import com.findme.DAO.impl.UserDAO;
import com.findme.Exceptions.BadRequestException;
import com.findme.Exceptions.InternalServerError;
import com.findme.models.Login;
import com.findme.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;

    public User save(User user) throws BadRequestException, InternalServerError {
        if (isValidEmail(user.getEmail()) && userDAO.verifyUser(user)) {
            return userDAO.save(user);
        } else throw new BadRequestException("BadRequestException");
    }

    public User read(Long id) throws InternalServerError {
        return userDAO.read(id);
    }

    public User update(User user) throws InternalServerError {
        return userDAO.update(user);
    }

    public void delete(User user) throws InternalServerError {
        userDAO.delete(user);
    }

    /*******************************************************/


    public User loginUser(Login login) throws BadRequestException, InternalServerError {
        String email = login.getEmail();
        if (isValidEmail(email) && userDAO.verifyLogin(login)) {
            return userDAO.loginUser(email, login.getPassword());
        } else throw new BadRequestException("BadRequestException");
    }

    public boolean isValidEmail(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }
}
