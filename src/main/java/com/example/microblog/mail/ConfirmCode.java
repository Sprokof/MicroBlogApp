package com.example.microblog.mail;

import lombok.NoArgsConstructor;

import java.util.*;

@NoArgsConstructor
public class ConfirmCode {


    private static final Map<String, String> codes;

    static {
        codes = new HashMap<String, String>();
    }

    public static void sendCodeToEmail(String email){
        codes.put(email, getCode());
        ///ConfirmCode(getCode());

    }

    private static String getCode() {
        String resultedCode = "";
        int index = 0;
        while (index != 6) {
            double d = (Math.random() * 10);
            resultedCode += (int) d;
            index++;
        }
        return resultedCode;
    }

    public static boolean compareCodes(String email, String inputCode) {
        String sentCode = codes.get(email);
        return sentCode.equals(inputCode);
    }
}
