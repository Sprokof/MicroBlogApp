package com.example.microblog.controllers;

import com.example.microblog.config.AuthProvider;
import com.example.microblog.dto.UserRegistrationDTO;
import com.example.microblog.entity.Post;
import com.example.microblog.entity.User;
import com.example.microblog.httpSession.Session;
import com.example.microblog.service.PostService;
import com.example.microblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @ModelAttribute("currentUser")
    public User getCurrentUser(){
        return AuthProvider.getCurrentUser();
    }

    @GetMapping("/home")
    public String home(Model model){
        model.addAttribute("post", new Post());
        return "home";
    }

    @PostMapping("/home")
    public String home(Post post,  Model model){
        User user = AuthProvider.getCurrentUser();
        post.setPostDate(postDate());
        user.addPost(post);
        model.addAttribute("post", post);
        userService.updateUser(user);
        return "home";
    }

    private String postDate(){
        String date = UserRegistrationDTO.currentDate();
        String time = UserRegistrationDTO.currentTime();

    return String.format("%s, %s", date, time);
    }
}
