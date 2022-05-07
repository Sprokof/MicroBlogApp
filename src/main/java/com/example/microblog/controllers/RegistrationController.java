package com.example.microblog.controllers;

import com.example.microblog.dto.UserDTO;
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
    public UserDTO userDTO(){
    return new UserDTO();
}

    @GetMapping("/registration")
    public String registration(Model model){
        UserDTO userDTO = new UserDTO();
        model.addAttribute("userDto", userDTO);
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("user") UserDTO userDTO, BindingResult result) {
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
