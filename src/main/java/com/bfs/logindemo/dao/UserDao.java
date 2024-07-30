package com.bfs.logindemo.dao;

import com.bfs.logindemo.domain.User;
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
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<User> findAll() {
        String sql = "select * from user";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class));
    }

    public Optional<User> findById(String id) {
        String sql = "select * from user where id = ?";
        try {
            User user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), id);
            return Optional.ofNullable(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public User getUserByEmail(String email) {
        String sql = "select * from user where email = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), email);
    }

    public void save(User user) {
        String sql = "insert into User(email,password, firstname, lastname, is_active, is_admin) values(?,?,?,?,?,?)";
        jdbcTemplate.update(sql, user.getEmail(),
                user.getPassword(), user.getFirstname(),
                user.getLastname(),user.isActive(), user.isAdmin());
    }

    public void update(User user) {
        String sql = "UPDATE User SET email = ?, password = ?, firstname = ?, lastname = ?, is_active = ?, is_admin = ? WHERE user_id = ?";
        jdbcTemplate.update(sql, user.getEmail(), user.getPassword(), user.getFirstname(), user.getLastname(), user.isActive(), user.isAdmin(), user.getUserId());
    }

    public void delete(int id) {
        String sql = "DELETE FROM User WHERE user_id = ?";
        jdbcTemplate.update(sql, id);
    }



}
