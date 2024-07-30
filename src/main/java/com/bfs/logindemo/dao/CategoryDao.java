package com.bfs.logindemo.dao;
import com.bfs.logindemo.dao.CategoryDao;
import com.bfs.logindemo.domain.Category;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class CategoryDao {
    private final JdbcTemplate jdbcTemplate;

    public CategoryDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Category> findAll() {
        String sql = "SELECT * FROM Category";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Category.class));
    }

    public Category findById(int id) {
        String sql = "SELECT * FROM Category WHERE category_id = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Category.class), id);
    }

    public void save(Category category) {
        String sql = "INSERT INTO Category (name) VALUES (?)";
        jdbcTemplate.update(sql, category.getName());
    }

    public void update(Category category) {
        String sql = "UPDATE Category SET name = ? WHERE category_id = ?";
        jdbcTemplate.update(sql, category.getName(), category.getCategoryId());
    }


    public void delete(int id) {
        String sql = "DELETE FROM Category WHERE category_id = ?";
        jdbcTemplate.update(sql, id);
    }
}
