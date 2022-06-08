package com.example.microblog.dto;

import com.example.microblog.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class MailDTO {

    private String code;
    private boolean sent;
    private User currentUser;

    public MailDTO(String code){
        this.code = code;
    }

}
