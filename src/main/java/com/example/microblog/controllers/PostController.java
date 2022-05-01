package com.example.microblog.controllers;


import com.example.microblog.entity.Post;
import com.example.microblog.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.microblog.service.PostServiceImpl.getPostService;

@Controller
public class PostController {


    @GetMapping("/posts")
    public String posts(Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        List<Post> posts = user.getPosts();
        model.addAttribute("posts", posts);
        model.addAttribute("user", user);
        return "posts";

    }

    @GetMapping("/search")
    public String search(@RequestParam String text, Model model, HttpSession session){
    User currentUser = (User) session.getAttribute("user");
    List<Post> posts;
    if(text.isEmpty()) {
        posts = getPostService().
                getAllPost(currentUser); }
    else {
        posts = getPostService().getPostByText(text);
        posts = posts.stream().filter((p) ->
                !p.getUser().equals(currentUser)).collect(Collectors.toList());
    }
    model.addAttribute("posts", posts);
    return "searchedPosts";
    }

    @GetMapping("/newsFeed")
    public String newsFeed(Model model, HttpSession session){
        User currentUser = (User) session.getAttribute("user");
        List<Post> posts = getPostService().getAllPost(currentUser);
        model.addAttribute("posts", posts);
        return "newsFeed";
    }

}
