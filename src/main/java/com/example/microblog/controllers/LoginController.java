package com.example.microblog.controllers;

import com.example.microblog.dto.UserLoginDTO;
import com.example.microblog.entity.User;
import com.example.microblog.service.UserServiceImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    @GetMapping(value = {"/", "/login"})
    public String login(Model model) {
        model.addAttribute("user", new UserLoginDTO());
        return "login";
    }


    @PostMapping( value = {"/", "/login"})
    public String login(@ModelAttribute("user") UserLoginDTO userDTO) {
        String encodePassword =
                new BCryptPasswordEncoder().encode(userDTO.getPassword());
        User user = (User) UserServiceImpl.getUserService().
                loadUserByUsername(userDTO.getLogin());
        if (user != null) {
            if (user.getPassword().equals(encodePassword)) {
                return "home";
            } else
                return "login";
        }
        return "/registration";
}
}
