package com.bfs.logindemo.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.bfs.logindemo.dao.ContactDao;
import com.bfs.logindemo.domain.Contact;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

@Repository
public class ContactDao {
    private final JdbcTemplate jdbcTemplate;
    private final SessionFactory sessionFactory;

    @Autowired
    public ContactDao(JdbcTemplate jdbcTemplate, SessionFactory sessionFactory) {
        this.jdbcTemplate = jdbcTemplate;
        this.sessionFactory = sessionFactory;
    }

    // Hibernate Methods
    public List<Contact> findAllHibernate() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Contact order by time desc", Contact.class).getResultList();
    }

    public Contact findByIdHibernate(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Contact.class, id);
    }

    public void saveHibernate(Contact contact) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(contact);
    }

    public void updateHibernate(Contact contact) {
        Session session = sessionFactory.getCurrentSession();
        session.update(contact);
    }

    public void deleteHibernate(int id) {
        Session session = sessionFactory.getCurrentSession();
        Contact contact = session.get(Contact.class, id);
        if (contact != null) {
            session.delete(contact);
        }
    }

//    public List<Contact> findAllJdbc() {
//        String sql = "SELECT * FROM Contact";
//        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Contact.class));
//    }
//
//    public Contact findByIdJdbc(int id) {
//        String sql = "SELECT * FROM Contact WHERE contact_id = ?";
//        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Contact.class), id);
//    }
//
//    public void saveJdbc(Contact contact) {
//        String sql = "INSERT INTO Contact (subject, message, email, time) VALUES (?, ?, ?, ?)";
//        jdbcTemplate.update(sql, contact.getSubject(), contact.getMessage(), contact.getEmail(), contact.getTime());
//    }
//
//    public void updateJdbc(Contact contact) {
//        String sql = "UPDATE Contact SET subject = ?, message = ?, email = ?, time = ? WHERE contact_id = ?";
//        jdbcTemplate.update(sql, contact.getSubject(), contact.getMessage(), contact.getEmail(), contact.getTime(), contact.getContactId());
//    }
//
//    public void deleteJdbc(int id) {
//        String sql = "DELETE FROM Contact WHERE contact_id = ?";
//        jdbcTemplate.update(sql, id);
//    }

}
