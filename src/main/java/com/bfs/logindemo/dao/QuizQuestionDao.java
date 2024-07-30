package com.bfs.logindemo.dao;

import com.bfs.logindemo.domain.QuizQuestion;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class QuizQuestionDao {
    private final JdbcTemplate jdbcTemplate;

    public QuizQuestionDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<QuizQuestion> findByQuizId(int quizId) {
        String sql = "SELECT * FROM QuizQuestion WHERE quiz_id = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(QuizQuestion.class), quizId);
    }

    public void save(QuizQuestion quizQuestion) {
        String sql = "INSERT INTO QuizQuestion (quiz_id, question_id, user_choice_id) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, quizQuestion.getQuizId(), quizQuestion.getQuestionId(), quizQuestion.getUserChoiceId());
    }

    public void update(QuizQuestion quizQuestion) {
        String sql = "UPDATE QuizQuestion SET quiz_id = ?, question_id = ?, user_choice_id = ? WHERE qq_id = ?";
        jdbcTemplate.update(sql, quizQuestion.getQuizId(), quizQuestion.getQuestionId(), quizQuestion.getUserChoiceId(), quizQuestion.getQqId());
    }

    public void delete(int id) {
        String sql = "DELETE FROM QuizQuestion WHERE qq_id = ?";
        jdbcTemplate.update(sql, id);
    }
}
