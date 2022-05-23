package com.example.microblog.httpSession;

import com.example.microblog.entity.User;
import com.example.microblog.service.UserService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;


public class Session {
    @Getter
    @Setter
    private static User user;


}
