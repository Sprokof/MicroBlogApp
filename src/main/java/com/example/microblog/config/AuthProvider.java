package com.example.microblog.config;

import com.example.microblog.dto.UserRegistrationDTO;
import com.example.microblog.entity.User;
import com.example.microblog.hash.MD5;
import com.example.microblog.service.UserService;
import com.example.microblog.service.UserServiceImpl;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class AuthProvider implements AuthenticationProvider {

    private static User currentUser;

    public static User getUser(){
        return currentUser;
    }

    public static void setUser(User user){
        currentUser = user;
    }

    @Autowired
    private UserService userService;
    @Override
    @SuppressWarnings("unchecked")
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String username = authentication.getName();

        String password = MD5.hash(authentication.getCredentials().toString());


        User current = userService.getUserByLogin(username);

        if(current == null){
            throw new BadCredentialsException("Unknown user");
        }
        if(!(current.getPassword().equals(password))){
            AuthProvider.setUser(current);
            throw new BadCredentialsException("Bad credentials");
        }
        Set<GrantedAuthority> userAuthorities =
                (Set<GrantedAuthority>) userService.mapRolesToAuthorities(current.getRoles());


        UserDetails user =  new org.springframework.security
                .core.userdetails.User(current.getUsername(),
                current.getPassword(), userAuthorities);


        return new UsernamePasswordAuthenticationToken(user, password, userAuthorities);

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

    public static User getCurrentUser(){
        SecurityContext context = SecurityContextHolder.getContext();
        String username = ((UserDetails) context.getAuthentication().
                getPrincipal()).getUsername();

        return new UserServiceImpl().getUserByLogin(username);
    }
}
