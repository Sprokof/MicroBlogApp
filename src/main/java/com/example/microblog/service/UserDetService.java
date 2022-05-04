package com.example.microblog.service;

import com.example.microblog.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserDetService extends UserDetailsService {
    User getUserById(int id);
    void saveUser(User user);
    void updateUser(User user);
    void deleteUser(User user);
    User getUserByEmail(String email);
    User getUserByUsername(String username);
    List<User> getAllUsers();
}
