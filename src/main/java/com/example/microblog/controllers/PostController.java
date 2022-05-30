package com.example.microblog.controllers;


import com.example.microblog.config.AuthProvider;
import com.example.microblog.entity.Post;
import com.example.microblog.entity.User;
import com.example.microblog.service.PostService;
import com.example.microblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static com.example.microblog.service.PostServiceImpl.getPostService;

@Controller
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;


    @ModelAttribute("currentUser")
    public User getCurrentUser() {
        return userService.getCurrentUser();
    }


    @ModelAttribute("posts")
    public List<Post> ListOfPosts(){
       List<Post> posts = AuthProvider.
               getCurrentUser().getPosts();

       posts.sort(new Comparator<Post>() {

           @Override
           public int compare(Post p1, Post p2) {
              return p2.getId() - p1.getId();
           }
       });

    return posts;

    }

    @PostMapping("/post/delete")
    public String deletePost(@RequestParam ("postid") String id, Model model){
        Post postToDelete = postService.getPostById((Integer.parseInt(id)));
        if(postToDelete != null) {
            User user = (User) model.getAttribute("currentUser");
            assert user != null;
            user.removePost(postToDelete);
            postService.deletePost(postToDelete);
            userService.updateUser(user);
        }

    return "posts";
    }

    @GetMapping("/posts")
    public String posts(){
      return "posts";
    }

    @GetMapping("/search")
    public String search(@RequestParam String text, Model model){
    User currentUser = (User) model.getAttribute("currentUser");
    List<Post> posts;
    if(text.isEmpty()) {
        posts = postService.getAllPost(currentUser); }

    else {
        posts = postService.getPostByText(text);
        posts = posts.stream().filter((p) ->
                !p.getUser().equals(currentUser)).collect(Collectors.toList());
    }
    model.addAttribute("posts", posts);
    return "/search";
    }

    @GetMapping("/newsFeed")
    public String newsFeed(Model model){
        User currentUser = (User) model.getAttribute("currentUser");
        List<Post> posts = getPostService().getAllPost(currentUser);
        model.addAttribute("posts", posts);
        return "newsFeed";
    }

}
