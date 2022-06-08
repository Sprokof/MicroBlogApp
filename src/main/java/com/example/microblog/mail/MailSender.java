package com.example.microblog.mail;

import com.example.microblog.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

@Component
public class MailSender  {

    @Value("${spring.mail.username}")
    private String username;

    @Autowired
    private JavaMailSender mailSender;

    public void send(String emailTo, String subject, String message){
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom(username);
        mailMessage.setTo(emailTo);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        mailSender.send(mailMessage);
    }

    public String activateMessage(User user){
        user.setAcceptedCode(generateConfirmCode());
        return String.format("Hello, %s,  it's your activate code %s",
                user.getUsername(), user.getAcceptedCode());
    }

    public String changePasswordMessage(User user){
        user.setAcceptedCode(generateConfirmCode());
        return String.format("Code to change password %s", user.getAcceptedCode());
    }

    private String generateConfirmCode() {
        String[] temp = new String[7];
        String result = "";

        for (int i = 0; i < temp.length; i++) {
            double d = (Math.random() * 10);
            temp[i] = String.valueOf((int) d);
        }
        int index = 0;
        while (index != temp.length) {
            result += temp[index];

            index ++ ;
        }

        return result;

    }
}
