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

    private String username;
    private String newPassword;
    private String confirmPassword;
    private String confirmCode;

    private boolean changed;

    public PasswordDTO(String username, String newPassword,
                       String confirmPassword, String confirmCode){
        this.username = username;
        this.newPassword = newPassword;
        this.confirmPassword = confirmPassword;
        this.confirmCode = confirmCode;
        this.changed = false;


    }


}
