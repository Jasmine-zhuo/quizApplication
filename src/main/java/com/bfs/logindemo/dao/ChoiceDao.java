package com.bfs.logindemo.dao;
import com.bfs.logindemo.dao.ChoiceDao;
import com.bfs.logindemo.domain.Choice;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class ChoiceDao {
    private final JdbcTemplate jdbcTemplate;

    public ChoiceDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Choice> findByQuestionId(int questionId) {
        String sql = "SELECT * FROM Choice WHERE question_id = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Choice.class), questionId);
    }

    public void save(Choice choice) {
        String sql = "INSERT INTO Choice (question_id, description, is_correct) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, choice.getQuestionId(), choice.getDescription(), choice.isCorrect());
    }

    public void update(Choice choice) {
        String sql = "UPDATE Choice SET question_id = ?, description = ?, is_correct = ? WHERE choice_id = ?";
        jdbcTemplate.update(sql, choice.getQuestionId(), choice.getDescription(), choice.isCorrect(), choice.getChoiceId());
    }

    public void delete(int id) {
        String sql = "DELETE FROM Choice WHERE choice_id = ?";
        jdbcTemplate.update(sql, id);
    }
}
