package com.example.microblog.dao;

import com.example.microblog.entity.Post;
import com.example.microblog.entity.Role;
import com.example.microblog.entity.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class InstanceSessionFactory {
    private static SessionFactory instance;

    private InstanceSessionFactory(){}

    public static synchronized SessionFactory getInstance(){
        if(instance == null){
            instance = getSessionFactory(new Class[]{
                User.class, Role.class, Post.class});
        }
    return instance;
    }

    private static SessionFactory getSessionFactory(Class<?>[] annotatedClass){
        Configuration configuration = new Configuration();
        for(Class<?> c: annotatedClass){ configuration.addAnnotatedClass(c); }
        return configuration.configure("hibernate.cfg.xml").buildSessionFactory();

    }
}
