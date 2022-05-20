package com.example.microblog.controllers;

import com.example.microblog.entity.Post;
import com.example.microblog.entity.User;
import com.example.microblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import static com.example.microblog.service.PostServiceImpl.*;

import javax.servlet.http.HttpSession;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @GetMapping("/home")
    public String home(Model model){
        model.addAttribute("post", new Post());
        return "home";
    }

    @PostMapping("/home")
    public String home(Post post, Model model){
        User user = getCurrentUser();
        System.out.println(user.toString());
        user.addPost(post);
        model.addAttribute("post", post);
        userService.updateUser(user);
        return "home";
    }

    private User getCurrentUser(){
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();

        String username = ((UserDetails) authentication.getPrincipal()).getUsername();


        return userService.getUserByLogin(username);
    }
}
