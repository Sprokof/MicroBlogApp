package com.example.microblog.dto;


import com.example.microblog.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.AssertTrue;
import java.util.Calendar;

@Getter
@Setter
@NoArgsConstructor
public class UserRegistrationDTO {
    private String username;
    private String email;
    private String confirmEmail;
    private String password;
    private String confirmPassword;
    @AssertTrue
    private boolean terms;

    public UserRegistrationDTO(String username, String email, String confirmEmail,
                    String password, String confirmPassword){
        this.username = username;
        this.email = email;
        this.confirmEmail = confirmEmail;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    public User toUser(){
        return new User(this.getEmail(), this.getUsername(), this.getPassword());
    }


    public static String currentDate() {
        String[] tempDates = Calendar.getInstance().toString().split(",");
        String day = tempDates[17].substring(tempDates[17].indexOf("=") + 1);
        if (day.length() == 1) {
            day = "0" + day;
        }
        String month = String.valueOf(Integer.parseInt(tempDates[14].substring(tempDates[14].indexOf("=") + 1)) + 1);
        if (month.length() == 1) {
            month = "0" + month;
        }
        String year = tempDates[13].substring(tempDates[13].indexOf("=") + 1);
        return String.format("%s.%s.%s", day, month, year);
    }

}
