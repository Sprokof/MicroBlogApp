package com.example.microblog.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserLoginDTO {

    private String login;
    private String password;

    public UserLoginDTO(String login, String password){
        this.login = login;
        this.password = password;
    }
}
