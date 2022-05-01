package com.example.microblog.dao;

import com.example.microblog.entity.Post;
import com.example.microblog.entity.User;

import java.util.List;

public interface PostDao {
    void savePost(Post post);
    Post getPostById(int id);
    void updatePost(Post post);
    void deletePost(Post post);
    List<Post> getAllPost(User thisUser);
    List<Post> getUsersPostExcludeCurrentUser(User user);
    List<Post> getPostByText(String text);
}
