package com.example.microblog.validation;

import com.example.microblog.dto.UserDTO;
import com.example.microblog.entity.User;
import com.example.microblog.service.UserServiceImpl;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.regex.Pattern;

@Component
public class UserDTOValidation implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {

        return UserDTO.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        if(supports(o.getClass())) {
            UserDTO userDTO = (UserDTO) o;


            String usernamePattern = "^[a-zA-Z0-9](_(?!(\\.|_))|\\.(?!(_|\\.))|" +
                    "[a-zA-Z0-9]){6,18}[a-zA-Z0-9]$";

            if (!userDTO.getUsername().matches(usernamePattern)) {
                errors.rejectValue("username", "Wrong.username.format");
            }

            Pattern p = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
                    Pattern.CASE_INSENSITIVE);


            if (!p.matcher(userDTO.getEmail()).find()) {
                errors.rejectValue("email", "Wrong.email.format");
            }

            p = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$");

            if (!p.matcher(userDTO.getPassword()).find()) {

                System.out.println(userDTO.getPassword() + " " + p.matcher(userDTO.getPassword()).find());
                errors.rejectValue("password", "Wrong.password.format");
            }
            if(!userDTO.getEmail().equals(userDTO.getConfirmEmail())){
                errors.rejectValue("confirmEmail", "Emails.not.equals");
            }

            if(!userDTO.getPassword().equals(userDTO.getConfirmPassword())){
                errors.rejectValue("confirmPassword", "Passwords.not.equals");
            }
        }
    }
}
