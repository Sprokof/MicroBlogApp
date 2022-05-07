package com.example.microblog.controllers;

import com.example.microblog.dto.UserRegistrationDTO;
import com.example.microblog.entity.User;
import com.example.microblog.service.UserServiceImpl;
import com.example.microblog.validation.UserDTOValidation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import static com.example.microblog.mail.ConfirmCode.sendCodeToEmail;


@Controller
public class RegistrationController {

    private UserDTOValidation userDTOValidation =
            new UserDTOValidation();

    @ModelAttribute("user")
    public UserRegistrationDTO userDTO(){
        return new UserRegistrationDTO();
}

    @GetMapping("/registration")
    public String registration(Model model){
        UserRegistrationDTO userDTO = new UserRegistrationDTO();
        model.addAttribute("userDto", userDTO);
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("user") UserRegistrationDTO userDTO, BindingResult result) {
        userDTOValidation.validate(userDTO, result);
        User userFromDb = UserServiceImpl.
                getUserService().getUserByEmail(userDTO.getEmail());
        if(userFromDb != null){
            result.rejectValue("email", "User.already.exist");
        }
        if (result.hasErrors()) {
            return "/registration";
        } else {
            UserServiceImpl.
                    getUserService().saveUser(userDTO.toUser());
            return "/home";
        }
    }

}
