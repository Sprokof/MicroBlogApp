package com.example.microblog;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class MicroBlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroBlogApplication.class, args);
    }

}
