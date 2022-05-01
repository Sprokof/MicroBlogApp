package com.example.microblog.controllers;

import com.example.microblog.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Calendar;

import static com.example.microblog.service.UserServiceImpl.*;


@Controller
public class RegistrationController {

    @GetMapping("/registration")
    public String registration(Model model){
        User user = new User();
        user.setJoinDate((String.valueOf(Calendar.getInstance().getTime())));

        System.out.println(user.toString());
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(User user, Model model){
        User userFromDB = getUserService().
                getUserByEmail(user.getEmail());

        if(userFromDB != null){
            return "login";
        }
        else
            getUserService().saveUser(user);
            model.addAttribute("user", user);
        return "login";
    }
}
