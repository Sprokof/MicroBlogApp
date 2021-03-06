package com.example.microblog.controllers;

import com.example.microblog.dto.UserRegistrationDTO;
import com.example.microblog.entity.User;
import com.example.microblog.service.UserServiceImpl;
import com.example.microblog.validation.UserDTOValidation;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;


@Controller
public class RegistrationController {

    private final UserServiceImpl userService = new UserServiceImpl();
    private final UserDTOValidation userDTOValidation = new UserDTOValidation();

    @ModelAttribute("userDTO")
    public UserRegistrationDTO userDTO() {
        return new UserRegistrationDTO();
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("userDTO") @Valid UserRegistrationDTO userDTO, BindingResult result) {
        userDTOValidation.validate(userDTO, result);
        if (result.hasErrors()) {
            return "/registration";
        } else {
            User user = UserRegistrationDTO.
                    builder().email(userDTO.getEmail()).
                    username(userDTO.getUsername()).
                    password(userDTO.getPassword()).build();
            user.setAccepted(false);
            userService.saveUser(user);
            return "login";
        }
    }
}
