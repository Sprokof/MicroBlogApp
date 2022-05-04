package com.example.microblog.controllers;

import com.example.microblog.dto.UserDTO;
import com.example.microblog.entity.User;
import com.example.microblog.validation.UserDTOValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Calendar;

import static com.example.microblog.service.UserServiceImpl.*;


@Controller
public class RegistrationController {

    private UserDTOValidation userDTOValidation =
            new UserDTOValidation();

    @GetMapping("/registration")
    public String registration(Model model){
        UserDTO userDTO = new UserDTO();
        model.addAttribute("dto", userDTO);
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(UserDTO userDTO, Model model, BindingResult result){
        userDTOValidation.validate(userDTO, result);
        if(result.hasErrors()){
            return "/registration";
        }

        else{
            getUserService().saveUser(userDTO.toUser());
            model.addAttribute("userDTO", userDTO);
        return "login";
    }
}
