package com.example.microblog.controllers;

import com.example.microblog.entity.User;
import com.example.microblog.mail.MailSender;
import com.example.microblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ConfirmEmailController {

    @Autowired
    UserService userService;

    @Autowired
    MailSender mailSender;

    private User currentUser;

    @ModelAttribute("code")
    public String code(){
        return new String();
    }

    @GetMapping("/confirmemail")
    public String email(@RequestParam("email") String email) {
        currentUser = userService.getUserByLogin(email);
        mailSender.send(currentUser.getEmail(),
                "Activate code", activateMessage(currentUser));
        return "confirmemail";
    }

    @PostMapping("/confirmemail")
    public String email(@ModelAttribute("code") String code, BindingResult result) {
        String page = "confirmemail";
        if(code.equals(currentUser.getAcceptedCode())) {
            currentUser.setAccepted(true);
            userService.updateUser(currentUser);
            page = "redirect:confirmemail?success";
        }
        else {
            result.rejectValue("code", "Wrong.code");
        }
        if(result.hasErrors()){
            page =  "confirmemail";
        }

    return page;

    }

    private String activateMessage(User user){
        return String.format("\t Message from MicroBlog app. \n \t\t " +
                "Code for activate account %s", user.getAcceptedCode());
    }

}

