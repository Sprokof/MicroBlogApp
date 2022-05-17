package com.example.microblog.dao;

import com.example.microblog.dto.UserRegistrationDTO;
import com.example.microblog.entity.Post;
import com.example.microblog.entity.Role;
import com.example.microblog.entity.User;

import lombok.Getter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.regex.Pattern;

public class UserDaoImpl implements UserDao {

    private static final SessionFactory sessionFactory =
            InstanceSessionFactory.getInstance();

    @Override
    public User getUserById(int id) {
        return null;
    }

    @Override
    public void saveUser(User user) {
        Session session = null;
        String currentDate = UserRegistrationDTO.currentDate();
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            user.setJoinDate((currentDate));
            session.save(user);
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
    public void updateUser(User user) {
        Session session = null;
        String currentDate = UserRegistrationDTO.currentDate();
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            user.setJoinDate((currentDate));
            session.update(user);
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
    public void deleteUser(User user) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.delete(user);
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
    public List<User> getAllUsers() {
        Session session = null;
        List<User> allUsers = null;
        try {
            session = this.sessionFactory.getCurrentSession();
            session.beginTransaction();
            allUsers = session.
                    createSQLQuery("SELECT * FROM USERS").
                    addEntity(User.class).list();
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
        return allUsers;
    }

    @Override
    public User getUserByUsername(String username) {
        Session session = null;
        User user = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            user = (User) session.createSQLQuery("SELECT * FROM " +
                            "USERS WHERE USERNAME=:username").
                    setParameter("username", username).
                    addEntity(User.class).getSingleResult();
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session != null) {
                if (session.getTransaction() != null) {
                    session.getTransaction().rollback();
                    if (e instanceof NoResultException) return null;
                }
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return user;
    }

    @Override
    public User getUserByEmail(String email) {
        Session session = null;
        User user = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            user = (User) session.createSQLQuery("SELECT * FROM " +
                            "USERS WHERE EMAIL=:email").
                    setParameter("email", email).
                    addEntity(User.class).getSingleResult();
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session != null) {
                if (session.getTransaction() != null) {
                    session.getTransaction().rollback();
                    if (e instanceof NoResultException) return null;
                }
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return user;
    }

    @Override
    public User getUserByLogin(String login) {
        Pattern email = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
                Pattern.CASE_INSENSITIVE);
        List<User> users = null;
        String column = "USERNAME";

        if (email.matcher(login).find()) column = "EMAIL";

        User user = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            user = (User) session.createSQLQuery("SELECT * FROM USERS " +
                            "WHERE " + column + "=:login").addEntity(User.class).
                    setParameter("login", login).getSingleResult();
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session != null) {
                if (session.getTransaction() != null) {
                    session.getTransaction().rollback();
                    if (e instanceof NoResultException) return null;
                }
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return user;
    }
}



