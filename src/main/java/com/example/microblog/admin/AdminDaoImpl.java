package com.example.microblog.admin;

import com.example.microblog.dao.Db;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import javax.persistence.NoResultException;
import java.util.List;

@Component
public class AdminDaoImpl implements AdminDao{

    private final SessionFactory sessionFactory
            = Db.getSessionFactory(new Class<?>[]{Admin.class});


    @Override
    public Admin isAdmin(String email) {
        Admin admin = null;
        Session session;
    try{
        session = this.sessionFactory.getCurrentSession();
        session.beginTransaction();
        admin = (Admin) session.createSQLQuery("SELECT * FROM " +
                "ADMINS WHERE ADMIN_EMAL=:email").
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
