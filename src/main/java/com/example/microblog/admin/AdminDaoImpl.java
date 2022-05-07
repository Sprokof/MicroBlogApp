package com.example.microblog.admin;

import com.example.microblog.dao.Db;
import com.example.microblog.entity.Post;
import com.example.microblog.entity.Role;
import com.example.microblog.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import javax.persistence.NoResultException;
import java.util.List;

@Component
public class AdminDaoImpl implements AdminDao{

    private final SessionFactory sessionFactory
            = Db.getSessionFactory(new Class<?>[]{Admin.class, Post.class,
            User.class, Role.class});


    @Override
    public Admin isAdmin(String email) {
        Admin admin = null;
        Session session;
    try{
        session = this.sessionFactory.getCurrentSession();
        session.beginTransaction();
        admin = (Admin) session.createSQLQuery("SELECT * FROM " +
                "ADMINS WHERE ADMIN_EMAIL=:email").
                addEntity(Admin.class).
                setParameter("email", email).getSingleResult();
        session.getTransaction().commit();
    }
    catch (Exception e){
        if(e instanceof NoResultException) { return null; }
            e.printStackTrace();
    }
    return admin;
    }

}
