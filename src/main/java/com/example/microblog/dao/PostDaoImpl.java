package com.example.microblog.dao;


import com.example.microblog.dto.UserRegistrationDTO;
import com.example.microblog.entity.Post;
import com.example.microblog.entity.User;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


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
    String[] array = (text.split("\\s"));
        Session session = null;
        List<Post> posts = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            posts = session.
                    createSQLQuery("SELECT * FROM POSTS").
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
    return posts.stream().
            filter((p) -> {
            return isContainsText(p, text).contains(p.getId());
            }).collect(Collectors.toList());

    }

    private List<Integer> isContainsText(Post post, String text){
        String[] texts = text.split("\\s");
        String[] postTexts = post.getPostText().split("\\s");

        List<Integer> output = new ArrayList<>();

        int index = 0;

            while(index != postTexts.length) {
                for (String s : texts) {
                    if (postTexts[index].equals(s)) {
                        output.add(post.getId());
                    }
                }
                index++;
            }
        return output;

    }
}
