package com.example.microblog.builder;

import com.example.microblog.entity.User;

public interface UserBuilder {
    UserBuilder email(String email);
    UserBuilder username(String username);
    UserBuilder password(String password);
    User build();
}
