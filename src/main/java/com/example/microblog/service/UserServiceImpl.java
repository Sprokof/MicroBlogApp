package com.example.microblog.service;

import com.example.microblog.dao.UserDao;
import com.example.microblog.dao.UserDaoImpl;
import com.example.microblog.dto.UserLoginDTO;
import com.example.microblog.entity.Role;
import com.example.microblog.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserDao dao;

    private final BCryptPasswordEncoder encoder;

    public UserServiceImpl(){
        this.dao = new UserDaoImpl();
        this.encoder = new BCryptPasswordEncoder();
    }

    @Override
    public void saveUser(User user) {
        Role role = RoleServiceImpl.
                roleService().getRole(user.getEmail());
        user.addRole(role);
        user.setPassword(encoder.encode(user.getPassword()));
        user.setJoinDate(User.currentTime());
        this.dao.saveUser(user);
        RoleServiceImpl.roleService().saveRole(role);
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

    @Override
    public User getUserByUsername(String username) {
        return this.dao.getUserByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Pattern p = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
                Pattern.CASE_INSENSITIVE);
        User user = null;
        if(p.matcher(username).find()){
            user = this.getUserByEmail(username);
        }
        else
            user = this.getUserByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("Invalid username or password");
        }
        return new org.springframework.security
                .core.userdetails.User(user.getUsername(),
                user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
        return roles.stream().
                map((role -> new SimpleGrantedAuthority(role.getRoleName())))
                .collect(Collectors.toList());
    }


    @Override
    public boolean isExist(UserLoginDTO user) {
        return this.dao.isExistUser(user);
    }
}
