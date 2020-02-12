package com.findme.controller;

import com.findme.Exceptions.BadRequestException;
import com.findme.Exceptions.InternalServerError;
import com.findme.models.Login;
import com.findme.models.Relationship;
import com.findme.models.User;
import com.findme.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private FriendsRequestsController requestsController;

    @RequestMapping(path = "/myProfile", method = RequestMethod.GET)
    public String myProfile(HttpSession session, Model model) {
        try {
            if (session.getAttribute("login") != null) {
                User me = (User) session.getAttribute("user");
                List<Relationship> incomeRequests = requestsController.getIncomeRequests(session);
                List<Relationship> outcomeRequests = requestsController.getOutcomeRequests(session);
                model.addAttribute("user", me);
                model.addAttribute("incomeRequests", incomeRequests);
                model.addAttribute("outcomeRequests", outcomeRequests);
            } else return "login";
        } catch (InternalServerError e) {
            return "500Error";
        } catch (BadRequestException e1) {
            return "400Error";
        }
        return "myProfile";
    }

    /**
     * getUserById
     **/
    @RequestMapping(path = "/user/{userId}", method = RequestMethod.GET)
    public String profile(Model model, @PathVariable String userId) {
        User user;

        try {

            user = userService.read(Long.parseLong(userId));

            if (user == null)
                return "404Error";

            model.addAttribute("user", user);
        } catch (InternalServerError e) {
            return "500Error";
        } catch (NumberFormatException nEx) {
            return "400Error";
        }
        return "profile";
    }


    /**
     * registerUser
     **/
    @RequestMapping(path = "/user-registration", method = RequestMethod.POST)
    public String registerUser(@ModelAttribute User user) {

        try {
            userService.save(user);
        } catch (InternalServerError e) {
            return "500Error";
        } catch (BadRequestException e1) {
            return "400Error";
        }

        return "ok";
    }


    /**
     * login/logout
     **/
    @RequestMapping(path = "/login", method = RequestMethod.POST, produces = "text/plain")
    public String loginUser(HttpSession session, @ModelAttribute Login login) {
        if (session.getAttribute("login") == null) {
            try {
                session.setAttribute("user", userService.loginUser(login));
            } catch (InternalServerError e) {
                return "500Error";
            } catch (BadRequestException e1) {
                return "400Error";
            }
            session.setAttribute("login", login);
        } else return "400Error";

        return "index";
    }

    @RequestMapping(path = "/logout", method = RequestMethod.GET)
    public String loginUser(HttpSession session) {
        session.invalidate();
        return "index";
    }





    /**
     * CRUD
     **/
    @RequestMapping(method = RequestMethod.POST, value = "/user-item", produces = "text/plain")
    public String save(@RequestBody User user) {
        try {
            userService.save(user);
            return "ok-save";
        } catch (InternalServerError e) {
            return "500Error";
        } catch (BadRequestException e1) {
            return "400Error";
        }
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/user-update", produces = "text/plain")
    public String update(@RequestBody User user) {
        try {
            userService.update(user);
            return "ok-update";
        } catch (InternalServerError e) {
            return "500Error";
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/user-delete", produces = "text/plain")
    public String delete(@RequestBody User user) {
        try {
            userService.delete(user);
            return "ok-delete";
        } catch (InternalServerError e) {
            return "500Error";
        }
    }

    /********/
}
