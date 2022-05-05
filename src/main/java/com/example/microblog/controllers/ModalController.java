package com.example.microblog.controllers;

import com.example.microblog.entity.User;
import com.example.microblog.mail.ConfirmCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ModalController {

    @Getter
    @Setter
    private static User user = null;

    @GetMapping("/registration/code")
    public String modalWindow(Model model) {
        model.addAttribute("code", new String());
        return "/modal";
    }

    @PostMapping("/registration/code")
    public String modalWindow(Model model, String code, BindingResult result) {
        if (user != null) {
            if (ConfirmCode.compareCodes(user.getEmail(), code)) {
                return "/home";
            } else
                result.rejectValue("code", "Wrong.code");
        }
        if (result.hasErrors()) {
            return "/registration/code";
        }
    return "/registration/code";

    }
}
