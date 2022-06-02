package com.example.microblog.service;

import com.example.microblog.builder.UserBuilder;
import com.example.microblog.config.AuthProvider;
import com.example.microblog.dao.RoleDaoImpl;
import com.example.microblog.dao.UserDaoImpl;
import com.example.microblog.entity.Role;
import com.example.microblog.entity.User;
import com.example.microblog.hash.MD5;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserServiceImpl implements UserService {


    private static User currentNotLoginUser;

    public static User getNotLoginUser(){
        return currentNotLoginUser;
    }

    public static void setNotLoginUser(User user){
        currentNotLoginUser = user;
    }

    private final UserDaoImpl userDao;
    private final RoleDaoImpl roleDao;

    private final BCryptPasswordEncoder encoder;

    public UserServiceImpl() {
        this.encoder = new BCryptPasswordEncoder();
        this.userDao = new UserDaoImpl();
        this.roleDao = new RoleDaoImpl();
    }

    @Override
    public void saveUser(User user) {
        Role role = new Role("USER");
        String hashPassword = MD5.hash(user.getPassword());
        user.setPassword(hashPassword);
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

    @Override
    public Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
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
    @Cacheable(value="user", key="#login")
    public User getUserByLogin(String login) {
        return this.userDao.getUserByLogin(login);
    }

    public User getCurrentUser(){
        SecurityContext context = SecurityContextHolder.getContext();
        User currentUser;
    try {
        String username = ((UserDetails) context.getAuthentication().
                getPrincipal()).getUsername();
        currentUser = this.getUserByLogin(username);
    }
    catch (ClassCastException e){
        currentUser = getNotLoginUser();
    }
        return currentUser;
    }

}


