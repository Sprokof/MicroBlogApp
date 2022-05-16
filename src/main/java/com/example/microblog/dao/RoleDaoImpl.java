package com.example.microblog.dao;


import com.example.microblog.entity.Post;
import com.example.microblog.entity.Role;
import com.example.microblog.entity.User;
import lombok.Getter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class RoleDaoImpl implements RoleDao {

    @Getter
    private static final SessionFactory sessionFactory =
            DB.getInstance().getSessionFactory(new Class[]{User.class, Role.class, Post.class});

    @Override
    public void saveRole(com.example.microblog.entity.Role role) {
        Session session = null;
        try{
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.save(role);
            session.getTransaction().commit();
        }
        catch (Exception e){
            if (session != null) {
                if (session.getTransaction() != null) {
                    session.getTransaction().rollback();
                }
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void updateRole(com.example.microblog.entity.Role role) {
        Session session = null;
        try{
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.update(role);
            session.getTransaction().commit();
        }
        catch (Exception e){
            if (session != null) {
                if (session.getTransaction() != null) {
                    session.getTransaction().rollback();
                }
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
