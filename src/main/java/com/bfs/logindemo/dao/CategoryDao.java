package com.bfs.logindemo.dao;
import com.bfs.logindemo.dao.CategoryDao;
import com.bfs.logindemo.domain.Category;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class CategoryDao {
    private final JdbcTemplate jdbcTemplate;
    private final SessionFactory sessionFactory;

    @Autowired
    public CategoryDao(JdbcTemplate jdbcTemplate, SessionFactory sessionFactory) {
        this.jdbcTemplate = jdbcTemplate;
        this.sessionFactory = sessionFactory;
    }
    public List<Category> findAllHibernate() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Category", Category.class).getResultList();
    }

    public Category findByIdHibernate(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Category.class, id);
    }

    public void saveHibernate(Category category) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(category);
    }

    public void updateHibernate(Category category) {
        Session session = sessionFactory.getCurrentSession();
        session.update(category);
    }

    public void deleteHibernate(int id) {
        Session session = sessionFactory.getCurrentSession();
        Category category = session.get(Category.class, id);
        if (category != null) {
            session.delete(category);
        }
    }
//    public List<Category> findAllJdbc() {
//        String sql = "SELECT * FROM Category";
//        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Category.class));
//    }
//
//    public Category findByIdJdbc(int id) {
//        String sql = "SELECT * FROM Category WHERE category_id = ?";
//        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Category.class), id);
//    }
//
//    public void saveJdbc(Category category) {
//        String sql = "INSERT INTO Category (name) VALUES (?)";
//        jdbcTemplate.update(sql, category.getName());
//    }
//
//    public void updateJdbc(Category category) {
//        String sql = "UPDATE Category SET name = ? WHERE category_id = ?";
//        jdbcTemplate.update(sql, category.getName(), category.getCategoryId());
//    }
//
//
//    public void deleteJdbc(int id) {
//        String sql = "DELETE FROM Category WHERE category_id = ?";
//        jdbcTemplate.update(sql, id);
//    }

}
