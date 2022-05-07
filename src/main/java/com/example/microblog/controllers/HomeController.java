package com.example.microblog.controllers;

import com.example.microblog.entity.Post;
import com.example.microblog.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import static com.example.microblog.service.PostServiceImpl.*;

import javax.servlet.http.HttpSession;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String home(Model model){
        model.addAttribute("post", new Post());
        return "home";
    }

   // @PostMapping("/home")
    //public String home(Post post, Model model, HttpSession httpSession){
      //  User user = (User) httpSession.getAttribute("user");
        //user.addPost(post);
        //model.addAttribute("post", post);
        //getPostService().savePost(post);
        //return "home";
    //}
}
