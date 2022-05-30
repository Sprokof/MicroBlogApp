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

    private String newPassword;
    private String confirmPassword;

    public PasswordDTO(String newPassword, String confirmPassword){
        this.newPassword = newPassword;
        this.confirmPassword = confirmPassword;
    }


}
