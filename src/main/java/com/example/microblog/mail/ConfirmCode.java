package com.example.microblog.mail;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
@Getter
@Setter
public class ConfirmCode {
    private String codeFromLetter;

    private static Map<String, ConfirmCode> usersCodes;

    static {
        usersCodes = new HashMap<>();
    }

    public static Map<String, ConfirmCode> getUsersCodes() {
        return usersCodes;
    }

    public ConfirmCode(String codeFromLetter){
        this.codeFromLetter = codeFromLetter;
    }

    public static String generateCode(){
        String[] numbers = new String[6];

        for(int i = (numbers.length - 1); i > 0; i --) {
            double d = (Math.random() * 10);
            numbers[i] = String.valueOf(((int) d));
        }

        String resultedCode = "";
        for(String n : numbers) resultedCode += n;

    return resultedCode;
    }
}
