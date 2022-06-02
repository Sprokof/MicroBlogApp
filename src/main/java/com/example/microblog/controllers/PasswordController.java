package com.example.microblog.controllers;

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

    private static User currentUser;

    @ModelAttribute("password")
    public PasswordDTO getPasswordDTO(){
        return new PasswordDTO();
    }

    @GetMapping("/changepassword/username")
    public String username(){
        return "changepasswordPage1";
    }


    @PostMapping("/changepassword/username")
    public String username(@Valid @ModelAttribute("password") PasswordDTO passwordDTO,
                         BindingResult bindingResult) {
        passwordValidation.validateUsername(passwordDTO, bindingResult);
        if(bindingResult.hasErrors()){
            return "changepasswordPage1";
        }
        else {
            currentUser = userService.getUserByLogin(passwordDTO.getUsername());
            return "changepasswordPage2";
        }
    }

    @GetMapping("/changepassword/passwords")
    public String passwords(){
        return "changepasswordPage2";
    }

    @PostMapping("/changepassword/passwords")
    public String passwords(@Valid @ModelAttribute("password") PasswordDTO passwordDTO,
                            BindingResult bindingResult) {
        passwordValidation.validatePasswords(passwordDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            return "changepasswordPage2";
        } else {

            currentUser.setPassword(MD5.hash(passwordDTO.getNewPassword()));

            return "changepasswordPage3";
        }

    }

    @GetMapping("/changepassword/code")
    public String code(){
        return "changepasswordPage3";
    }

    @PostMapping("/changepassword/code")
    public String code(@Valid @ModelAttribute("password") PasswordDTO passwordDTO,
                       BindingResult bindingResult, Model model){
        model.addAttribute("email", currentUser.getEmail());
        String code = currentUser.getAcceptedCode();
        currentUser.setChangePasswordCode(code);

        passwordValidation.validateCode(new Object[]{currentUser, passwordDTO}, bindingResult);

        if(bindingResult.hasErrors()){
           return "changepasswordPage3";
        }
        else {
            userService.updateUser(currentUser);
            return "redirect:changepassword/code?success";
       }
    }


}
