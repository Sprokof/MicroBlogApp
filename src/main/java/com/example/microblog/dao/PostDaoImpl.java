package com.example.microblog.dao;


import com.example.microblog.dto.UserRegistrationDTO;
import com.example.microblog.entity.Post;
import com.example.microblog.entity.User;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class PostDaoImpl implements PostDao {

    private static final SessionFactory sessionFactory = InstanceSessionFactory.getInstance();

    @Override
    public void savePost(Post post) {
        Session session = null;
        post.setPostDate(UserRegistrationDTO.currentDate());
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.save(post);
            session.getTransaction().commit();
        } catch (Exception e) {
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
    public Post getPostById(int id) {
        Session session = null;
        Post post = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            post = session.get(Post.class, id);
            session.getTransaction().commit();
        } catch (Exception e) {
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
        return post;
    }

    @Override
    public void updatePost(Post post) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.update(post);
            session.getTransaction().commit();
        } catch (Exception e) {
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
    public void deletePost(Post post) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.delete(post);
            session.getTransaction().commit();
        } catch (Exception e) {
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
    @SuppressWarnings("unchecked")
    public List<Post> getAllPost(User thisUser) {
        Session session = null;
        List<Post> posts = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            posts = session.createSQLQuery("SELECT * FROM POSTS " +
                            "WHERE USERS_ID=:thisId").
                    setParameter("thisId", thisUser).addEntity(Post.class).list();
            session.getTransaction().commit();
        } catch (Exception e) {
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
        return posts;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Post> getUsersPostExcludeCurrentUser(User user) {
        Session session = null;
        List<Post> posts = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            posts = session.createSQLQuery("SELECT * FROM POSTS " +
                            "WHERE USERS_ID!=thisId").setParameter("thisId", user.getId()).
                    addEntity(Post.class).list();
            session.getTransaction().commit();
        } catch (Exception e) {
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
        return posts;
    }


    @Override
    @SuppressWarnings("unchecked")
    public List<Post> getPostByText(String text) {
        Session session = null;
        List<Post> posts = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            posts = session.
                    createSQLQuery("SELECT * FROM POSTS " +
                            "WHERE POST_TEXT like:text").
                    addEntity(Post.class).setParameter("text", text).list();
            session.getTransaction().commit();
        } catch (Exception e) {
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
        return posts;

    }
}
