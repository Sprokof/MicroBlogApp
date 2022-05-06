package com.example.microblog.dao;

import com.example.microblog.admin.Admin;
import com.example.microblog.entity.Post;
import com.example.microblog.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.management.relation.Role;

public class RoleDaoImpl implements RoleDao {

    private final SessionFactory sessionFactory =
            Db.getSessionFactory(new Class[]{User.class,
                    Post.class, Role.class, Admin.class});

    @Override
    public void saveRole(com.example.microblog.entity.Role role) {
        Session session = null;
        try{
            session = this.sessionFactory.getCurrentSession();
            session.beginTransaction();
            session.save(role);
            session.getTransaction().commit();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            this.sessionFactory.close();
        }

    }

    @Override
    public void updateRole(com.example.microblog.entity.Role role) {
        Session session = null;
        try{
            session = this.sessionFactory.getCurrentSession();
            session.beginTransaction();
            session.update(role);
            session.getTransaction().commit();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            this.sessionFactory.close();
        }

    }
}
