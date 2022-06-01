package com.example.microblog.controllers;

import com.example.microblog.mail.ConfirmCode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ConfirmEmailController {

    @ModelAttribute("code")
    public ConfirmCode code() {
        return new ConfirmCode();
    }

    @GetMapping("/confirmemail")
    public String email(Model model) {
        return "confirmemail";
    }

    @PostMapping("/confirmemail")
    public String email(@ModelAttribute("code") ConfirmCode code) {

        return "redirect:confirmemail?success";
    }

}

