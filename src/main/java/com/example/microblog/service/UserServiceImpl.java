package com.example.microblog.service;

import com.example.microblog.dao.UserDao;
import com.example.microblog.dao.UserDaoImpl;
import com.example.microblog.entity.User;

import java.util.List;

public class UserServiceImpl implements UserService{

    private final UserDao dao;

    public UserServiceImpl(){
        this.dao = new UserDaoImpl();
    }

    @Override
    public void saveUser(User user) {
        this.dao.saveUser(user);
    }

    @Override
    public User getUserById(int id) {
        return this.dao.getUserById(id);
    }

    @Override
    public void updateUser(User user) {
        this.dao.updateUser(user);
    }

    @Override
    public void deleteUser(User user) {
        this.dao.deleteUser(user);
    }

    @Override
    public User getUserByEmail(String email) {
        return this.dao.getUserByEmail(email);
    }

    @Override
    public List<User> getAllUser() {
        return this.dao.getAllUsers();
    }

    public static UserServiceImpl getUserService(){
        return new UserServiceImpl();
    }
}
