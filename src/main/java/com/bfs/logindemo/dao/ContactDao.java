package com.bfs.logindemo.dao;

import org.springframework.stereotype.Repository;
import com.bfs.logindemo.dao.ContactDao;
import com.bfs.logindemo.domain.Contact;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

@Repository
public class ContactDao {
    private final JdbcTemplate jdbcTemplate;

    public ContactDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<Contact> findAll() {
        String sql = "SELECT * FROM Contact";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Contact.class));
    }

    public Contact findById(int id) {
        String sql = "SELECT * FROM Contact WHERE contact_id = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Contact.class), id);
    }

    public void save(Contact contact) {
        String sql = "INSERT INTO Contact (subject, message, email, time) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, contact.getSubject(), contact.getMessage(), contact.getEmail(), contact.getTime());
    }

    public void update(Contact contact) {
        String sql = "UPDATE Contact SET subject = ?, message = ?, email = ?, time = ? WHERE contact_id = ?";
        jdbcTemplate.update(sql, contact.getSubject(), contact.getMessage(), contact.getEmail(), contact.getTime(), contact.getContactId());
    }

    public void delete(int id) {
        String sql = "DELETE FROM Contact WHERE contact_id = ?";
        jdbcTemplate.update(sql, id);
    }
}
