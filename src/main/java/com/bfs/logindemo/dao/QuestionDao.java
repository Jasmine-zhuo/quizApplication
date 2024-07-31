package com.bfs.logindemo.dao;

import com.bfs.logindemo.domain.Category;
import com.bfs.logindemo.domain.Choice;
import com.bfs.logindemo.domain.Question;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class QuestionDao {
    private final JdbcTemplate jdbcTemplate;

    public QuestionDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    private static final class QuestionRowMapper implements RowMapper<Question> {
        @Override
        public Question mapRow(ResultSet rs, int rowNum) throws SQLException {
            Question question = new Question();
            question.setQuestionId(rs.getInt("question_id"));
            question.setCategoryId(rs.getInt("category_id"));
            question.setDescription(rs.getString("description"));
            question.setActive(rs.getBoolean("is_active"));
            return question;
        }
    }

    private static final class ChoiceRowMapper implements RowMapper<Choice> {
        @Override
        public Choice mapRow(ResultSet rs, int rowNum) throws SQLException {
            Choice choice = new Choice();
            choice.setChoiceId(rs.getInt("choice_id"));
            choice.setQuestionId(rs.getInt("question_id"));
            choice.setDescription(rs.getString("description"));
            choice.setCorrect(rs.getBoolean("is_correct")); // Ensure this line correctly maps the is_correct field
            return choice;
        }
    }
    public List<Question> getAllQuestions() {
        String sql = "SELECT * FROM Question";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Question.class));
    }
    public List<Question> findByCategoryId(int categoryId) {
        String sql = "SELECT * FROM Question WHERE category_id = ?";
        return jdbcTemplate.query(sql, new Object[]{categoryId}, new QuestionRowMapper());
    }

    public List<Choice> getChoicesByQuestionId(int questionId) {
        String sql = "SELECT * FROM Choice WHERE question_id = ?";
        return jdbcTemplate.query(sql, new Object[]{questionId}, new ChoiceRowMapper());
    }

    public List<Question> findRandomByCategoryId(int categoryId, int limit) {
        String sql = "SELECT * FROM Question WHERE category_id = ? ORDER BY RAND() LIMIT ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Question.class), categoryId, limit);
    }

    public List<Category> findAllCategories() {
        String sql = "SELECT * FROM Category";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Category.class));
    }
    public Optional<Choice> getChoiceById(int choiceId){
        String sql = "SELECT * FROM Choice WHERE choice_id = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Choice.class), choiceId).stream().findFirst();
    }


    public Question findById(int id) {
        String sql = "SELECT * FROM Question WHERE question_id = ?";
        Question question = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Question.class), id);
        if (question != null) {
            question.setChoices(getChoicesByQuestionId(question.getQuestionId()));
        }
        return question;
    }

    public void save(Question question) {
        String sql = "INSERT INTO Question (category_id, description) VALUES (?, ?)";
        jdbcTemplate.update(sql, question.getCategoryId(), question.getDescription());
        if (question.getChoices() != null) {
            for (Choice choice : question.getChoices()) {
                saveChoice(choice);
            }
        }
    }

    public void saveChoice(Choice choice) {
        String sql = "INSERT INTO Choice (questionId, description, isCorrect) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, choice.getQuestionId(), choice.getDescription(), choice.isCorrect());
    }

    public void update(Question question) {
        String sql = "UPDATE Question SET category_id = ?, description = ?, is_active = ? WHERE question_id = ?";
        jdbcTemplate.update(sql, question.getCategoryId(), question.getDescription(), question.isActive(), question.getQuestionId());
        if (question.getChoices() != null) {
            for (Choice choice : question.getChoices()) {
                updateChoice(choice);
            }
        }
    }

    public void updateChoice(Choice choice) {
        String sql = "UPDATE Choice SET description = ?, isCorrect = ? WHERE choice_id = ?";
        jdbcTemplate.update(sql, choice.getDescription(), choice.isCorrect(), choice.getChoiceId());
    }

    public void delete(int id) {
        String sql = "DELETE FROM Question WHERE question_id = ?";
        jdbcTemplate.update(sql, id);
    }
    public void deleteChoice(int id) {
        String sql = "DELETE FROM Choice WHERE choice_id = ?";
        jdbcTemplate.update(sql, id);
    }

    public List<Question> findAll() {
        String sql = "SELECT * FROM Question";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Question.class));
    }

    public Question getQuestionById(int id) {
        String sql = "SELECT * FROM question WHERE question_id = ?";
        Question question = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Question.class), id);
        if (question != null) {
            question.setChoices(getChoicesByQuestionId(question.getQuestionId()));
        }
        return question;
    }

    public Question getQuestion() {
        String sql = "SELECT * FROM Question WHERE question_id = ?";
        Question question = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Question.class), 1); // Example: get question with ID 1

        String choiceSql = "SELECT * FROM Choice WHERE question_id = ?";
        List<Choice> choices = jdbcTemplate.query(choiceSql, new BeanPropertyRowMapper<>(Choice.class), question.getQuestionId());

        question.setChoices(choices);
        return question;
    }
    public void toggleQuestionStatus(int questionId) {
        String sql = "UPDATE Question SET active = NOT active WHERE question_id = ?";
        jdbcTemplate.update(sql, questionId);
    }
    private static class QuestionWithCategoryRowMapper implements RowMapper<QuestionWithCategoryDTO> {
        @Override
        public QuestionWithCategoryDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            Question question = new Question();
            question.setQuestionId(rs.getInt("question_id"));
            question.setCategoryId(rs.getInt("category_id"));
            question.setDescription(rs.getString("description"));
            question.setActive(rs.getBoolean("is_active"));
            // You may need to set other properties if needed

            String categoryName = rs.getString("categoryName");

            return new QuestionWithCategoryDTO(question, categoryName);
        }
    }
    public List<QuestionWithCategoryDTO> findAllQuestionsWithCategoryName() {
        String sql = "SELECT q.*, c.name as categoryName " +
                "FROM Question q " +
                "JOIN Category c ON q.category_id = c.category_id";
        return jdbcTemplate.query(sql, new QuestionWithCategoryRowMapper());
    }
}
