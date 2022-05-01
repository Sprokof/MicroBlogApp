package com.example.microblog.dao;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Db {
    public static SessionFactory getSessionFactory(Class<?>[] annotatedClass){
        Configuration configuration = new Configuration();
        for(Class<?> c: annotatedClass) configuration.addAnnotatedClass(c);
        return configuration.configure("hibernate.cfg.xml").buildSessionFactory();

    }
}
