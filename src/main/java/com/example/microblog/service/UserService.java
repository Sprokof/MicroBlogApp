package com.example.microblog.service;

import com.example.microblog.entity.User;

import java.util.List;

public interface UserService {
    User getUserById(int id);
    void saveUser(User user);
    void updateUser(User user);
    void deleteUser(User user);
    User getUserByEmail(String email);
    List<User> getAllUser();
    User getUserByUsername(String username);
}
