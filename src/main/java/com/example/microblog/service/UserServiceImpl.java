package com.example.microblog.service;

import com.example.microblog.dao.RoleDaoImpl;
import com.example.microblog.dao.UserDaoImpl;
import com.example.microblog.entity.Role;
import com.example.microblog.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserServiceImpl implements UserService {

    private UserDaoImpl userDao;
    private RoleDaoImpl roleDao;

    private final BCryptPasswordEncoder encoder;

    public UserServiceImpl() {
        this.encoder = new BCryptPasswordEncoder();
        this.userDao = new UserDaoImpl();
        this.roleDao = new RoleDaoImpl();
    }

    @Override
    public void saveUser(User user) {
        Role role = new Role("USER");
        user.setPassword(encoder.encode(user.getPassword()));
        user.addRole(role);
        this.userDao.saveUser(user);
    }

    @Override
    public void updateUser(User user) {
        this.userDao.updateUser(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user;
        if ((user = getUserByLogin(username)) == null) {
            throw new UsernameNotFoundException("Invalid login or password");
        }
        return new org.springframework.security
                .core.userdetails.User(user.getUsername(),
                user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().
                map((role -> new SimpleGrantedAuthority(role.getRoleName())))
                .collect(Collectors.toSet());
    }

    @Override
    public List<User> getAllUsers() {
        return this.userDao.getAllUsers();
    }

    @Override
    public User existUserByUsername(String username) {
        return this.userDao.getUserByUsername(username);
    }

    @Override
    public User existUserByEmail(String email) {
       return this.userDao.getUserByEmail(email);
    }

    @Override
    public User getUserByLogin(String login) {
        return this.userDao.getUserByLogin(login);
    }

}


