package com.example.microblog.service;

import com.example.microblog.dto.UserLoginDTO;
import com.example.microblog.entity.User;
import org.hibernate.Session;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    User getUserById(int id);
    void saveUser(User user);
    void updateUser(User user);
    void deleteUser(User user);
    User getUserByEmail(String email);
    User getUserByUsername(String username);
    List<User> getAllUser();
    User getUserByLogin(String login);
}
