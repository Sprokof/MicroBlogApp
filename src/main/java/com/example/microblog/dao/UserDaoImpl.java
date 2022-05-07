package com.example.microblog.dao;

import com.example.microblog.admin.Admin;
import com.example.microblog.entity.Post;
import com.example.microblog.entity.Role;
import com.example.microblog.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.NoResultException;
import java.util.Calendar;
import java.util.List;

public class UserDaoImpl implements UserDao{

    private final SessionFactory sessionFactory =
            Db.getSessionFactory(new Class[]{User.class,
                    Post.class, Role.class, Admin.class});

    @Override
    public User getUserById(int id) {
        return null;
    }

    @Override
    public void saveUser(User user) {
        Session session;
        String currentDate = Calendar.getInstance().getTime().toString();
    try{
        session = this.sessionFactory.getCurrentSession();
        session.beginTransaction();
        user.setJoinDate((currentDate));
        session.save(user);
        session.getTransaction().commit();
    }
    catch (Exception e){e.printStackTrace();}
    finally {
        this.sessionFactory.close();
    }
    }

    @Override
    public void updateUser(User user) {
        Session session = null;
        try{
            session = this.sessionFactory.getCurrentSession();
            session.beginTransaction();
            session.update(user);
            session.getTransaction().commit();
        }
        catch (Exception e){e.printStackTrace();}
        finally {
            this.sessionFactory.close();
        }

    }

    @Override
    public void deleteUser(User user) {
        Session session = null;
        try{
            session = this.sessionFactory.getCurrentSession();
            session.beginTransaction();
            session.delete(user);
            session.getTransaction().commit();
        }
        catch (Exception e){e.printStackTrace();}
        finally {
            this.sessionFactory.close();
        }
    }

    @Override
    public User getUserByEmail(String email) {
        Session session; User user = null;
        try{
            session = this.sessionFactory.getCurrentSession();
            session.beginTransaction();
            user = (User) session.
                    createSQLQuery("SELECT * FROM USERS " +
                            "WHERE EMAIL=:email").setParameter("email", email).
                    addEntity(User.class).getSingleResult();
            session.getTransaction().commit();}

        catch (Exception e){
            System.out.println();
            if(e instanceof NoResultException){ return null; }
        else e.printStackTrace();
        }
        finally {
            this.sessionFactory.close();
        }
        return user;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> getAllUsers() {
        Session session;
        List<User> allUsers = null;
    try{
        session = this.sessionFactory.getCurrentSession();
        session.beginTransaction();
        allUsers = session.
                createSQLQuery("SELECT * FROM USERS").
                addEntity(User.class).list();
    }
    catch (Exception e){e.printStackTrace();}
    finally {
        this.sessionFactory.close();
    }
    return allUsers;
    }

    @Override
    public User getUserByUsername(String username) {
        Session session; User user = null;
        try{
            session = this.sessionFactory.getCurrentSession();
            session.beginTransaction();
            user = (User) session.
                    createSQLQuery("SELECT * FROM USERS " +
                            "WHERE EMAIL=:username").setParameter("username", username).
                    addEntity(User.class).getSingleResult();
            session.getTransaction().commit();}

        catch (Exception e){
            if(e instanceof NoResultException){ return null;}
            else e.printStackTrace();
        }
        finally {
            this.sessionFactory.close();
        }
        return user;
    }
}
