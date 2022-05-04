package com.example.microblog.dto;


import com.example.microblog.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {
    private String username;
    private String email;
    private String confirmEmail;
    private String password;
    private String confirmPassword;

    private UserDTO(String username, String email, String confirmEmail,
                    String password, String confirmPassword){
        this.username = username;
        this.email = email;
        this.confirmEmail = confirmEmail;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    public User toUser(){
        String[] userFields = { this.getUsername(), this.getEmail(), this.getPassword() };
        return new User(userFields[0],userFields[1], userFields[2]);
    }



}
