package com.example.microblog.dao;

import com.example.microblog.dto.UserLoginDTO;
import com.example.microblog.entity.User;
import org.hibernate.Session;

import java.util.List;

public interface UserDao {
    User getUserById(int id);
    void saveUser(User user);
    void updateUser(User user);
    void deleteUser(User user);
    User getUserByEmail(String email);
    User getUserByUsername(String username);
    List<User> getAllUsers();
    User getUserByLogin(String login);

}
