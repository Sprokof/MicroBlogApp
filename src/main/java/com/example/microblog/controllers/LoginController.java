package com.example.microblog.controllers;

import com.example.microblog.dto.UserLoginDTO;
import com.example.microblog.dto.UserRegistrationDTO;
import com.example.microblog.entity.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import static com.example.microblog.service.UserServiceImpl.getUserService;

@Controller
@SessionAttributes("user")
public class LoginController {

    @GetMapping(value = {"/", "/login"})
    public String login(Model model){
        model.addAttribute("user", new UserLoginDTO());
        return "/login";
    }

    @ModelAttribute("user")
    public User createUser(){
        return new User();
    }

    @PostMapping(value = {"/", "/login"})
    public String login(@ModelAttribute("user") UserLoginDTO userDTO, Model model,
                        BindingResult result){
        if(!getUserService().isExist(userDTO)){
            result.rejectValue("email", "Invalid.password.email");
        }
        if(result.hasErrors()){
            return "login";
        }
            model.addAttribute("user", userDTO);
        return "home";
    }

}
