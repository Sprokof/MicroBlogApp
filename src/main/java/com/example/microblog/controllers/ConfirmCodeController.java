package com.example.microblog.controllers;

import com.example.microblog.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class ConfirmCodeController {

    @Getter
    @Setter
    private static User user = null;

    @GetMapping("/registration/code")
    public String modalWindow(Model model) {
        model.addAttribute("code", new String());
        return "/registration/modal";
    }

    @PostMapping("/registration/code")
    public String modalWindow(Model model, @Valid String code, BindingResult result) {
        if (user != null) {
            if (ConfirmCode.compareCodes(user.getEmail(), code)) {
                model.addAttribute("code", code);
                return "/home";
            } else
                result.rejectValue("code", "Wrong.code");
        }
        if (result.hasErrors()) {
            return "/registration/modal";
        }
    return "/registration/modal";

    }
}
