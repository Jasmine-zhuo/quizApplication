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
        String sql = "select * from User";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class));
    }

    public Optional<User> findById(int userId) {
        String sql = "select * from User where user_id = ?";
        return jdbcTemplate.query(sql, new Object[]{userId}, new BeanPropertyRowMapper<>(User.class))
                .stream()
                .findFirst();
    }

    public User getUserByEmail(String email) {
        String sql = "select * from user where email = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), email);
    }

    public void save(User user) {
        String sql = "insert into User(email,password, firstname, lastname, is_active, is_admin) values(?,?,?,?,?,?)";
        jdbcTemplate.update(sql, user.getEmail(),
                user.getPassword(), user.getFirstname(),
                user.getLastname(),user.getActive(), user.isAdmin());
    }

    public void update(User user) {
        String sql = "UPDATE User SET email = ?, password = ?, firstname = ?, lastname = ?, is_active = ?, is_admin = ? WHERE user_id = ?";
        jdbcTemplate.update(sql, user.getEmail(), user.getPassword(), user.getFirstname(), user.getLastname(), user.getActive(), user.isAdmin(), user.getUserId());
    }

    public void delete(int id) {
        String sql = "DELETE FROM User WHERE user_id = ?";
        jdbcTemplate.update(sql, id);
    }


    public Optional<User> findByEmailAndPasswordAndIsAdmin(String email, String password, boolean isAdmin) {
        String sql = "SELECT * FROM User WHERE email = ? AND password = ? AND is_admin = ?";
        try {
            User user = jdbcTemplate.queryForObject(sql, new Object[]{email, password, isAdmin},
                    new BeanPropertyRowMapper<>(User.class));
            return Optional.ofNullable(user);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
    public void toggleUserStatus(int userId) {
        String sql = "UPDATE User SET is_active = NOT is_active WHERE user_id = ?";
        jdbcTemplate.update(sql, userId);
    }

}
