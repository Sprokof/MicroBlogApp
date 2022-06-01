package com.example.microblog.validation;

import com.example.microblog.dto.PasswordDTO;
import com.example.microblog.entity.User;
import com.example.microblog.mail.ConfirmCode;
import com.example.microblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.regex.Pattern;

import static com.example.microblog.mail.ConfirmCode.generateCode;
import static com.example.microblog.mail.ConfirmCode.getUsersCodes;

@Component
public class PasswordValidation implements Validator {

    @Autowired
    UserService userService;

    @Override
    public boolean supports(Class<?> oClass) {
        return PasswordDTO.class.equals(oClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        if(supports(o.getClass())){

            PasswordDTO passwordDTO = (PasswordDTO) o;

            ConfirmCode.getUsersCodes().put(passwordDTO.getLogin(), new ConfirmCode(generateCode()));

            User user = userService.getUserByLogin(passwordDTO.getLogin());
            if(user == null){
                errors.rejectValue("login", "Wrong.login");
            }

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
            String inputCode = passwordDTO.getConfirmCode(),
                    generatedCode = getUsersCodes().get(passwordDTO.getLogin()).getCodeFromLetter();
            if(!inputCode.equals(generatedCode)){
                errors.rejectValue("confirmCode", "Wrong.code");
            }
        }
    }
}
