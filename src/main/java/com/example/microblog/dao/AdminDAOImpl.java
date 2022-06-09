package com.example.microblog.dao;

import com.example.microblog.entity.Admin;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.NoResultException;

public class AdminDAOImpl implements AdminDAO{

    private final SessionFactory sessionFactory =
            InstanceSessionFactory.getInstance();

    @Override
    public boolean isAdmin(String email) {
        Session session = null;
        Admin admin = null;
    try{
        session = this.sessionFactory.openSession();
        session.beginTransaction();
        admin = (Admin) session.createSQLQuery("SELECT * FROM " +
                        "ADMINS WHERE EMAIL=:email").
                            setParameter("email", email).
                                addEntity(Admin.class).getSingleResult();
        session.getTransaction().commit();
    } catch (Exception e) {
        if (session != null) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
                if(e instanceof NoResultException) admin = null;
            }
        }
    } finally {
        if (session != null) {
            session.close();
        }
    }
    return (admin != null);
    }

}
