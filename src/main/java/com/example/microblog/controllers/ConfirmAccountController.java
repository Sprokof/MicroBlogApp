package com.example.microblog.controllers;

import com.example.microblog.dto.MailDTO;
import com.example.microblog.entity.User;
import com.example.microblog.mail.MailSender;
import com.example.microblog.service.UserService;
import com.example.microblog.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
public class ConfirmAccountController {

    @Autowired
    UserService userService;

    @Autowired
    MailSender mailSender;

    @ModelAttribute("mail")
    public MailDTO getMailDTO(){
        return new MailDTO();
    }

    @GetMapping("/confirm")
    public String email(@RequestParam("email") String email, @ModelAttribute("mail") MailDTO mailDTO){
        ((UserServiceImpl) userService).setCurrentUserByLogin(userService.existUserByEmail(email));
        User user = ((UserServiceImpl) userService).getCurrentUserByLogin();
        String message = mailSender.activateMessage(user);
        mailSender.send(user.getEmail(), "Activation code", message);
        mailDTO.setCurrentUser(user);
        return "confirmemail";
    }

    @PostMapping("/confirm")
    public String email(@ModelAttribute("mail") MailDTO mailDTO,
                         BindingResult result) {
        mailDTO.setCurrentUser(((UserServiceImpl) userService).getCurrentUserByLogin());
        if (!mailDTO.getCurrentUser().getAcceptedCode().equals(mailDTO.getCode())) {
            System.out.println(mailDTO.getCurrentUser().getAcceptedCode() + " not equals " + mailDTO.getCode());
            result.rejectValue("code", "Wrong.code");
        }
        if (result.hasErrors()) {
            return "confirmemail";
        }
        User user = mailDTO.getCurrentUser();;
        user.setAccepted(true);
        userService.updateUser(user);
        return "confirmemail";
    }

}


