package com.example.microblog.validation;

import com.example.microblog.dto.PasswordDTO;
import com.example.microblog.entity.User;
import com.example.microblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import java.util.regex.Pattern;


@Component
public class PasswordValidation {

    @Autowired
    UserService userService;

    public boolean supports(Class<?> oClass) {
        if(oClass.equals(Object[].class)) {
            return true;
        }
        return PasswordDTO.class.equals(oClass);
    }

    public void validateUsername(Object o, Errors errors){
        if(supports(o.getClass())){

            PasswordDTO passwordDTO = (PasswordDTO) o;

            if(userService.existUserByUsername(passwordDTO.getUsername()) == null){
                errors.rejectValue("username", "Username.not.exist");
            }
        }
    }

    public void validatePasswords(Object o, Errors errors) {
        if (supports(o.getClass())) {

            PasswordDTO passwordDTO = (PasswordDTO) o;

            Pattern password = Pattern.
                    compile("^(?=.*[0-9])(?=.*[a-z])" +
                            "(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$");

            if (!(password.matcher(passwordDTO.getNewPassword()).find())) {
                errors.rejectValue("newPassword", "Wrong.password.format");
            } else {
                String pass1 = passwordDTO.getNewPassword();
                String pass2 = passwordDTO.getConfirmPassword();

                if (!pass1.equals(pass2)) {
                    errors.rejectValue("confirmPassword", "Passwords.not.equals");
                }
            }
        }
    }

    public void validateCode(Object[] objs, Errors errors){
        if(supports(objs.getClass())){
            User current = (User) objs[0];
            PasswordDTO passwordDTO = (PasswordDTO) objs[1];
            if(!current.getAcceptedCode().
                    equals(passwordDTO.getConfirmCode())){
                errors.rejectValue("confirmCode", "Wrong.code");
            }

        }
    }

}
