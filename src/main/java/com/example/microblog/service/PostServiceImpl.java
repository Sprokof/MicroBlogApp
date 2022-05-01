package com.example.microblog.service;

import com.example.microblog.dao.PostDao;
import com.example.microblog.dao.PostDaoImpl;
import com.example.microblog.entity.Post;
import com.example.microblog.entity.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class PostServiceImpl implements PostService{

    public PostDao dao;

    public PostServiceImpl(){
        this.dao = new PostDaoImpl();
    }

    @Override
    public void savePost(Post post) {
        this.dao.savePost(post);

    }

    @Override
    public Post getPostById(int id) {
        return this.dao.getPostById(id);
    }

    @Override
    public void updatePost(Post post) {
        this.dao.updatePost(post);
    }


    @Override
    public void deletePost(Post post) {
        this.dao.deletePost(post);
    }

    @Override
    public List<Post> getAllPost(User thisUser) {
        return this.dao.getUsersPostExcludeCurrentUser(thisUser);
    }

    @Override
    public List<Post> getUsersPost(User user) {
        return this.dao.getAllPost(user);
    }

    @Override
    public List<Post> getPostByText(String text) {
        return this.dao.getPostByText(text);
    }

    public static PostServiceImpl getPostService(){
        return new PostServiceImpl();
    }
}
