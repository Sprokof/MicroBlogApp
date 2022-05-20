package com.example.microblog.config;

import com.example.microblog.entity.User;
import com.example.microblog.hash.MD5;
import com.example.microblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import java.util.Set;

@Component
public class AuthProvider implements AuthenticationProvider {

    @Autowired
    private UserService userService;


    @Override
    @SuppressWarnings("unchecked")
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String username = authentication.getName();

        String password = MD5.hash(authentication.getCredentials().toString());

        System.out.println(password);

        User current = userService.getUserByLogin(username);

        if(current == null){
            throw new BadCredentialsException("Unknown user");
        }
        if(!(current.getPassword().equals(password))){
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
}
