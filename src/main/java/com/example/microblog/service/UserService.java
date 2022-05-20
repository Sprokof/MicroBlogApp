package com.example.microblog.service;

import com.example.microblog.dto.UserLoginDTO;
import com.example.microblog.entity.Role;
import com.example.microblog.entity.User;
import org.hibernate.Session;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Collection;
import java.util.List;

public interface UserService extends UserDetailsService {
    void saveUser(User user);
    void updateUser(User user);
    List<User> getAllUsers();
    User existUserByUsername(String username);
    User existUserByEmail(String email);
    User getUserByLogin(String login);
    Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles);
}
