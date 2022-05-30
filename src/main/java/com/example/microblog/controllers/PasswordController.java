package com.example.microblog.controllers;

import com.example.microblog.config.AuthProvider;
import com.example.microblog.dto.PasswordDTO;
import com.example.microblog.entity.User;
import com.example.microblog.hash.MD5;
import com.example.microblog.service.UserService;
import com.example.microblog.validation.PasswordValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class PasswordController {

    @Autowired
    private PasswordValidation passwordValidation;
    @Autowired
    private UserService userService;

    @ModelAttribute("password")
    public PasswordDTO getPasswordDTO(){
        return new PasswordDTO();
    }

    @GetMapping("/changepassword")
    public String change(){
        return "changepassword";
    }

    @PostMapping("/changepassword")
    public String change(@Valid @ModelAttribute("password") PasswordDTO passwordDTO, BindingResult bindingResult){
        passwordValidation.validate(passwordDTO, bindingResult);

    if(bindingResult.hasErrors()){
        return "changepassword";
    }
    else {
        User user = AuthProvider.getUser();
        String hashNewPassword = MD5.hash(passwordDTO.getNewPassword());
        user.setPassword(hashNewPassword);
        userService.updateUser(user);
        return "login";
    }
    }
}
