package com.example.microblog.dao;


import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class RoleDaoImpl implements RoleDao {

    private static final SessionFactory sessionFactory =
            InstanceSessionFactory.getInstance();

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
