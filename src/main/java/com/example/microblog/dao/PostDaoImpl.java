package com.example.microblog.dao;

import com.example.microblog.admin.Admin;
import com.example.microblog.dto.UserRegistrationDTO;
import com.example.microblog.entity.Post;
import com.example.microblog.entity.Role;
import com.example.microblog.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class PostDaoImpl implements PostDao{

    private final SessionFactory sessionFactory =
            Db.getSessionFactory(new Class[]{User.class,
                    Post.class, Role.class, Admin.class});

    @Override
    public void savePost(Post post) {
        Session session = null;
        post.setPostDate(UserRegistrationDTO.currentDate());
    try{
        session = this.sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.save(post);
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
    public Post getPostById(int id) {
        Session session;
        Post post = null;
    try{
        session = this.sessionFactory.getCurrentSession();
        session.beginTransaction();
        post = session.get(Post.class, id);
        session.getTransaction().commit();
    }
    catch (Exception e){
        e.printStackTrace();
    }
    finally {
        this.sessionFactory.close();}
    return post;
    }


    @Override
    public void updatePost(Post post) {
        Session session;
    try{
        session = this.sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.update(post);
        session.getTransaction().commit();
    }
    catch (Exception e){e.printStackTrace();}
    finally {
        this.sessionFactory.close();
    }

    }

    @Override
    public void deletePost(Post post) {
        Session session;
    try{
        session = this.sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.delete(post);
        session.getTransaction().commit();
    }
    catch (Exception e){
        e.printStackTrace();
    }

    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Post> getAllPost(User thisUser) {
        Session session; List<Post> posts = null;
    try{
        session = this.sessionFactory.getCurrentSession();
        session.beginTransaction();
        posts = session.createSQLQuery("SELECT * FROM POSTS " +
                "WHERE USERS_ID=:thisId").
                setParameter("thisId", thisUser).addEntity(Post.class).list();
        session.getTransaction().commit();
    }
    catch (Exception e){
        e.printStackTrace();
    }
    finally {
        this.sessionFactory.close();
    }
    return posts;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Post> getUsersPostExcludeCurrentUser(User user) {
        Session session;
        List<Post> posts = null;
        try {
            session = this.sessionFactory.getCurrentSession();
            session.beginTransaction();
            posts = session.createSQLQuery("SELECT * FROM POSTS " +
                            "WHERE USERS_ID!=thisId").setParameter("thisId", user.getId()).
                    addEntity(Post.class).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            this.sessionFactory.close();
        }
        return posts;
    }


    @Override
    @SuppressWarnings("unchecked")
    public List<Post> getPostByText(String text) {
        Session session;
        List<Post> posts = null;
    try{
        session = this.sessionFactory.getCurrentSession();
        session.beginTransaction();
        posts = session.
                createSQLQuery("SELECT * FROM POSTS " +
                        "WHERE POST_TEXT like:text").
                addEntity(Post.class).setParameter("text", text).list();
    }
    catch (Exception e){e.printStackTrace();}
    finally {
        this.sessionFactory.close();
    }
    return posts;

    }
    }
