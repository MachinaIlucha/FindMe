package com.findme.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String home() {
        return "index";
    }

    @RequestMapping(path = "/loginUser", method = RequestMethod.GET)
    public String loginPage() {
        return "login";
    }
}
