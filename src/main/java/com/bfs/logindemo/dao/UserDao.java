package com.bfs.logindemo.dao;

import com.bfs.logindemo.domain.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// Repository / DAO layer
// We are using some mocked (fake) data here.
// In your project, you need to use mySQL database, configure the data source.
@Repository
public class UserDao {
    private final SessionFactory sessionFactory;

    @Autowired
    public UserDao(SessionFactory sessionFactory) {
        //this.jdbcTemplate = jdbcTemplate;
        this.sessionFactory = sessionFactory;
    }
    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }
    public User findUserByEmailHibernate(String email) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from User where email = :email", User.class)
                .setParameter("email", email)
                .uniqueResult();
    }

    public Optional<User> findByIdHibernate(int userId) {
        Session session = sessionFactory.getCurrentSession();
        return Optional.ofNullable(session.get(User.class, userId));
    }

    public void saveHibernate(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(user);
    }

    public void updateHibernate(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.update(user);
    }

    public void deleteHibernate(int id) {
        Session session = sessionFactory.getCurrentSession();
        User user = session.get(User.class, id);
        if (user != null) {
            session.delete(user);
        }
    }

    public Optional<User> findByEmailAndPasswordAndIsAdminHibernate(String email, String password, boolean isAdmin) {
        Session session = sessionFactory.getCurrentSession();
        return Optional.ofNullable(session.createQuery("from User where email = :email and password = :password and isAdmin = :isAdmin", User.class)
                .setParameter("email", email)
                .setParameter("password", password)
                .setParameter("isAdmin", isAdmin)
                .uniqueResult());
    }

    public void toggleUserStatusHibernate(int userId) {
        Session session = sessionFactory.getCurrentSession();
        User user = session.get(User.class, userId);
        if (user != null) {
            user.setActive(!user.isActive());
            session.update(user);
        }
    }

    public List<User> findAllHibernate() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from User", User.class).getResultList();
    }
    public Optional<User> findByEmailAndIsAdminHibernate(String email, boolean isAdmin) {
        Session session = sessionFactory.getCurrentSession();
        return Optional.ofNullable(session.createQuery("from User where email = :email and isAdmin = :isAdmin", User.class)
                .setParameter("email", email)
                .setParameter("isAdmin", isAdmin)
                .uniqueResult());
    }

}
