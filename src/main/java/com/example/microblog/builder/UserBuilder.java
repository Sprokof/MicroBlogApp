package com.example.microblog.builder;

import com.example.microblog.entity.User;


public abstract class UserBuilder {


    public UserBuilder email(String email){
        return null;
    }
    public UserBuilder username(String username){
        return null;
    }
    public UserBuilder password(String password){
        return null;
    }
    public User build(){
        return null;
    }
    public boolean containsNull(UserBuilder builder){
        return false;
    }

}
