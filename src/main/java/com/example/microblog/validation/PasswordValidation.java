package com.example.microblog.validation;

import com.example.microblog.dto.PasswordDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.regex.Pattern;

@Component
public class PasswordValidation implements Validator {

    @Override
    public boolean supports(Class<?> oClass) {
        return PasswordDTO.class.equals(oClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        if(supports(o.getClass())){

            PasswordDTO passwordDTO = (PasswordDTO) o;

            Pattern password = Pattern.
                    compile("^(?=.*[0-9])(?=.*[a-z])" +
                            "(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$");

            if(!(password.matcher(passwordDTO.getNewPassword()).find())){
                errors.rejectValue("newPassword", "Wrong.password.format");
            }
            else {
                String pass1 = passwordDTO.getNewPassword();
                String pass2 = passwordDTO.getConfirmPassword();

                if(!pass1.equals(pass2)){
                    errors.rejectValue("confirmPassword", "Passwords.not.equals");
                }
            }
        }
    }
}
