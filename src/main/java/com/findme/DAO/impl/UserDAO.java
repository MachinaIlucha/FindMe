package com.findme.DAO.impl;

import com.findme.DAO.GenericDao;
import com.findme.Exceptions.InternalServerError;
import com.findme.models.Login;
import com.findme.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;

@Repository
@Transactional
public class UserDAO implements GenericDao<User> {

    private final String SQL_findUser = "FROM User WHERE phone = :phone or email = :email";
    private final String SQL_loginUser = "FROM User WHERE email = :email and password = :password";

    @Autowired
    private GeneralDAO<User> generalDAO;

    @Override
    public User save(User t) throws InternalServerError {
        try {
            return generalDAO.save(t);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServerError("InternalServerError");
        }
    }

    @Override
    public User read(Long id) throws InternalServerError {
        try {
            generalDAO.seteClass(User.class);
            return generalDAO.read(id);
        } catch (Exception e) {
            throw new InternalServerError("InternalServerError");
        }
    }

    @Override
    public User update(User t) throws InternalServerError {
        try {
            return generalDAO.update(t);
        } catch (Exception e) {
            throw new InternalServerError("InternalServerError");
        }
    }

    @Override
    public void delete(User t) throws InternalServerError {
        try {
            generalDAO.delete(t);
        } catch (Exception e) {
            throw new InternalServerError("InternalServerError");
        }
    }


    public User findUserByQuery(String phone, String email) {
        Query query1 = generalDAO.getEntityManager().createQuery(SQL_findUser);
        query1.setParameter("phone", phone);
        query1.setParameter("email", email);
        User user;
        try {
            user = (User) query1.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
        return user;
    }

    public User loginUser(String email, String password) {
        Query query1 = generalDAO.getEntityManager().createQuery(SQL_loginUser);
        query1.setParameter("email", email);
        query1.setParameter("password", password);
        User user;
        try {
            user = (User) query1.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
        return user;
    }

    public List<User> getUserTagged(String idsUserTagged) throws InternalServerError {
        List<User> userTagged = new LinkedList<>();

        String[] ids = idsUserTagged.split(" ");

        if (ids.length != 0) {
            for (String id : ids) {
                User user = read(Long.parseLong(id));
                userTagged.add(user);
            }
        } else return null;

        return userTagged;
    }

    /***********************************/

    public boolean verifyUser(User user) {
        User userExist = findUserByQuery(user.getPhone(), user.getEmail());
        return userExist != null;
    }

    public boolean verifyLogin(Login login) {
        User userExist = loginUser(login.getEmail(), login.getPassword());
        return userExist != null;
    }
}