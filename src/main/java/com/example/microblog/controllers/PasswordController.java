package com.example.microblog.controllers;

import com.example.microblog.dto.PasswordDTO;
import com.example.microblog.entity.User;
import com.example.microblog.hash.MD5;
import com.example.microblog.mail.MailSender;
import com.example.microblog.service.UserService;
import com.example.microblog.service.UserServiceImpl;
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


    @Autowired
    MailSender mailSender;

    @ModelAttribute("password")
    public PasswordDTO getPasswordDTO(){
        return new PasswordDTO();
    }

    @GetMapping("/changepassword/username")
    public String username(){
        return "username";
    }


    @PostMapping("/changepassword/username")
    public String username(@Valid @ModelAttribute("password") PasswordDTO passwordDTO,
                         BindingResult bindingResult) {
        passwordValidation.validateUsername(passwordDTO, bindingResult);
        if(bindingResult.hasErrors()){
            return "username";
        }
        else {
            String username = passwordDTO.getUsername();
            ((UserServiceImpl) userService).
                    setCurrentUserByLogin(userService.getUserByLogin(username));
            return "passwords";
        }
    }

    @GetMapping("/changepassword/passwords")
    public String passwords(){
        return "passwords";
    }

    @PostMapping("/changepassword/passwords")
    public String passwords(@Valid @ModelAttribute("password") PasswordDTO passwordDTO,
                            BindingResult bindingResult) {
        User user = ((UserServiceImpl) userService).getCurrentUserByLogin();
        passwordValidation.validatePasswords(passwordDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            return "passwords";
        } else {
                mailSender.send(user.getEmail(),
                        "Change password code", mailSender.changePasswordMessage(user));
            }

            user.setPassword(MD5.hash(passwordDTO.getNewPassword()));
            return "code";

    }

    @GetMapping("/changepassword/code")
    public String code(){
        return "code";
    }

    @PostMapping("/changepassword/code")
    public String code(@Valid @ModelAttribute("password") PasswordDTO passwordDTO,
                       BindingResult bindingResult, Model model){

        User user = ((UserServiceImpl) userService).
                getCurrentUserByLogin();

        model.addAttribute("email", user.getEmail());

        passwordValidation.validateCode(new Object[]{user, passwordDTO}, bindingResult);

        if(bindingResult.hasErrors()){
           return "code";
        }
        else {
            passwordDTO.setChanged(true);
            return "code";
       }
    }


}
