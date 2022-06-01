package com.example.microblog.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@NoArgsConstructor
@Component
public class PasswordDTO {

    private String login;
    private String newPassword;
    private String confirmPassword;
    private String confirmCode;

    public PasswordDTO(String login, String newPassword, String confirmPassword, String confirmCode){
        this.login = login;
        this.newPassword = newPassword;
        this.confirmPassword = confirmPassword;
        this.confirmCode = confirmCode;


    }


}
