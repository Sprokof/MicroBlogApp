package com.example.microblog.dao;

import com.example.microblog.admin.Admin;
import com.example.microblog.dto.UserLoginDTO;
import com.example.microblog.dto.UserRegistrationDTO;
import com.example.microblog.entity.Post;
import com.example.microblog.entity.Role;
import com.example.microblog.entity.User;
import com.example.microblog.service.UserServiceImpl;
import lombok.Getter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.NoResultException;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Pattern;

public class UserDaoImpl implements UserDao {

    private final SessionFactory sessionFactory =
            Db.getSessionFactory(new Class[]{User.class,
                    Post.class, Role.class, Admin.class});

    public SessionFactory getSessionFactory(){
        return this.sessionFactory;
    }

    @Override
    public User getUserById(int id) {
        return null;
    }

    @Override
    public void saveUser(User user) {
        Session session;
        String currentDate = UserRegistrationDTO.currentDate();
        try {
            session = this.sessionFactory.getCurrentSession();
            session.beginTransaction();
            user.setJoinDate((currentDate));
            session.save(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.sessionFactory.close();
        }
    }

    @Override
    public void updateUser(User user) {
        Session session = null;
        try {
            session = this.sessionFactory.getCurrentSession();
            session.beginTransaction();
            session.update(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.sessionFactory.close();
        }

    }

    @Override
    public void deleteUser(User user) {
        Session session = null;
        try {
            session = this.sessionFactory.getCurrentSession();
            session.beginTransaction();
            session.delete(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.sessionFactory.close();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> getAllUsers() {
        Session session;
        List<User> allUsers = null;
        try {
            session = this.sessionFactory.getCurrentSession();
            session.beginTransaction();
            allUsers = session.
                    createSQLQuery("SELECT * FROM USERS").
                    addEntity(User.class).list();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.sessionFactory.close();
        }
        return allUsers;
    }

    @Override
    public User getUserByUsername(String username) {
        Session session;
        User user = null;
        try{
            session = this.sessionFactory.getCurrentSession();
            session.beginTransaction();
            user = (User) session.createSQLQuery("SELECT * FROM " +
                            "USERS WHERE USERNAME=:userame").
                    setParameter("username", username).
                    addEntity(User.class).getSingleResult();
            session.getTransaction().commit();
        }
        catch (Exception e){
            if(e instanceof NoResultException) return null;
            e.printStackTrace();
        }
        finally {
            this.sessionFactory.close();
        }
        return user;

    }

    @Override
    public User getUserByEmail(String email) {
        Session session;
        User user = null;
    try{
        session = this.sessionFactory.getCurrentSession();
        session.beginTransaction();
        user = (User) session.createSQLQuery("SELECT * FROM " +
                "USERS WHERE EMAIL=:email").
                setParameter("email", email).
                addEntity(User.class).getSingleResult();
        session.getTransaction().commit();
    }
    catch (Exception e){
        if(e instanceof NoResultException) return null;
        e.printStackTrace();
    }
    finally {
        this.sessionFactory.close();
    }
    return user;

    }

    @Override
    public User getUserByLogin(String login) {
        Pattern email = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
                Pattern.CASE_INSENSITIVE);

        String column = "USERNAME";

    if(email.matcher(login).find()) column = "EMAIL";

    User user = null;
        Session session;
    try{
        session = this.sessionFactory.getCurrentSession();
        session.beginTransaction();
    user = (User) session.createSQLQuery("SELECT * FROM USERS " +
                "WHERE "+column+"=:login").addEntity(User.class).
                setParameter("login", login).getSingleResult();
        System.out.println(user);
        session.getTransaction().commit();
    }
    catch (Exception e){
        if(e instanceof NoResultException){ return null; }
        e.printStackTrace();
    }
    finally {
        this.sessionFactory.close();
    }

    return user;
    }
}

