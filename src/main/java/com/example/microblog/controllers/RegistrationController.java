package com.example.microblog.controllers;

import com.example.microblog.dto.UserRegistrationDTO;
import com.example.microblog.entity.User;
import com.example.microblog.service.UserServiceImpl;
import com.example.microblog.validation.UserDTOValidation;
import lombok.Getter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;


@Controller
public class RegistrationController {

    private final UserServiceImpl userService = new UserServiceImpl();
    private final UserDTOValidation userDTOValidation = new UserDTOValidation();

    @ModelAttribute("user")
    public UserRegistrationDTO userDTO(){
        return new UserRegistrationDTO();
}

    @GetMapping("/registration")
    public String registration(Model model){
        UserRegistrationDTO userDTO = new UserRegistrationDTO();
        model.addAttribute("user", userDTO);
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("user") @Valid UserRegistrationDTO userDTO, BindingResult result) {
        userDTOValidation.validate(userDTO, result);
        if (result.hasErrors()) {
            return "/registration";
        } else {
            userService.saveUser(userDTO.toUser());
            return "login";
        }
    }



}
