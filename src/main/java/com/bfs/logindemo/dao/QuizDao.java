package com.bfs.logindemo.dao;

import com.bfs.logindemo.domain.*;
import com.bfs.logindemo.util.JdbcUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import java.sql.Timestamp;

import java.util.List;

@Repository
public class QuizDao {

    private final JdbcTemplate jdbcTemplate;

    public QuizDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Category> findAllCategories() {
        String sql = "SELECT * FROM Category";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Category.class));
    }

    public List<Quiz> findByUserId(int userId) {
        String sql = "SELECT * FROM Quiz WHERE user_id = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Quiz.class), userId);
    }

    public Quiz findById(int id) {
        String sql = "SELECT * FROM Quiz WHERE quiz_id = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Quiz.class), id);
    }

    public void save(Quiz quiz) {
        String sql = "INSERT INTO Quiz (user_id, category_id, name, time_start, time_end) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, quiz.getUserId(), quiz.getCategoryId(), quiz.getName(), quiz.getTimeStart(), quiz.getTimeEnd());
    }

    public void update(Quiz quiz) {
        String sql = "UPDATE Quiz SET user_id = ?, category_id = ?, name = ?, time_start = ?, time_end = ? WHERE quiz_id = ?";
        jdbcTemplate.update(sql, quiz.getUserId(), quiz.getCategoryId(), quiz.getName(), quiz.getTimeStart(), quiz.getTimeEnd(), quiz.getQuizId());
    }

    public void delete(int id) {
        String sql = "DELETE FROM Quiz WHERE quiz_id = ?";
        jdbcTemplate.update(sql, id);
    }

    public List<Quiz> findAll() {
        String sql = "SELECT * FROM Quiz";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Quiz.class));
    }

    public List<Quiz> findRecentQuizzesByUserId(int userId) {
        String sql = "SELECT * FROM Quiz WHERE user_id = ? ORDER BY time_end DESC LIMIT 10";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Quiz.class), userId);
    }
    public int createQuiz(int userId, int categoryId, String quizName) {
        String quizSql = "INSERT INTO Quiz (user_id, category_id, name, time_start) VALUES (?, ?, ?, NOW())";
        // debug statement
        System.out.println("Executing SQL: " + quizSql);
        System.out.println("Parameters: " + userId + ", " + categoryId + ", " + quizName);
        jdbcTemplate.update(quizSql, userId, categoryId, quizName);

        String quizIdSql = "SELECT LAST_INSERT_ID()";
        System.out.println("Executing SQL to get last inserted quiz ID: " + quizIdSql);
        int quizId = jdbcTemplate.queryForObject(quizIdSql, Integer.class);
        System.out.println("Retrieved quiz ID: " + quizId);

        return quizId;
    }

    public void saveQuizResultAndSelectedChoice(int quizId, List<Question> questions, List<Integer> selectedChoiceIds, String quizResult) {
        String updateQuizSql = "UPDATE Quiz SET name = ?, time_end = NOW() WHERE quiz_id = ?";
        // debug statement
        System.out.println("Executing SQL: " + updateQuizSql);
        System.out.println("Parameters: " + quizResult + ", " + quizId);
        jdbcTemplate.update(updateQuizSql, quizResult, quizId);

        String questionSql = "INSERT INTO QuizQuestion (quiz_id, question_id, user_choice_id) VALUES (?, ?, ?)";
        for (int i = 0; i < questions.size(); i++) {
            System.out.println("Executing SQL: " + questionSql);
            System.out.println("Parameters: " + quizId + ", " + questions.get(i).getQuestionId() + ", " + selectedChoiceIds.get(i));
            jdbcTemplate.update(questionSql, quizId, questions.get(i).getQuestionId(), selectedChoiceIds.get(i));
        }
    }

    public void updateQuizEndTime(int quizId) {
        // Update the Quiz table with time_end
        String updateQuizSql = "UPDATE Quiz SET time_end = ? WHERE quiz_id = ?";
        Timestamp currentTimeStamp = new Timestamp(System.currentTimeMillis());
        jdbcTemplate.update(updateQuizSql, currentTimeStamp, quizId);
    }

//    public int saveQuizResultAndSelectedChoice(int userId, int categoryId, List<Question> questions, List<Integer> selectedChoiceIds, String quizResult) {
//        String quizSql = "INSERT INTO Quiz (user_id, category_id, name, time_start) VALUES (?, ?, ?, NOW())";
//        // debug statement
//        System.out.println("Executing SQL: " + quizSql);
//        System.out.println("Parameters: " + userId + ", " + categoryId + ", " + quizResult);
//        jdbcTemplate.update(quizSql, userId, categoryId, quizResult);
//
//        String quizIdSql = "SELECT LAST_INSERT_ID()";
//        System.out.println("Executing SQL to get last inserted quiz ID: " + quizIdSql);
//        int quizId = jdbcTemplate.queryForObject(quizIdSql, Integer.class);
//        System.out.println("Retrieved quiz ID: " + quizId);
//
//        String questionSql = "INSERT INTO QuizQuestion (quiz_id, question_id, user_choice_id) VALUES (?, ?, ?)";
//        for (int i = 0; i < questions.size(); i++) {
//            System.out.println("Executing SQL: " + questionSql);
//            System.out.println("Parameters: " + quizId + ", " + questions.get(i).getQuestionId() + ", " + selectedChoiceIds.get(i));
//            jdbcTemplate.update(questionSql, quizId, questions.get(i).getQuestionId(), selectedChoiceIds.get(i));
//        }
//        return quizId;
//    }
    public List<Question> findQuestionsByQuizId(int quizId) {
        String sql = "SELECT q.* FROM Question q INNER JOIN QuizQuestion qq ON q.question_id = qq.question_id WHERE qq.quiz_id = ?";
        List<Question> questions = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Question.class), quizId);

        // Fetch and set choices for each question
        for (Question question : questions) {
            List<Choice> choices = getChoicesByQuestionId(question.getQuestionId());
            question.setChoices(choices);
        }

        return questions;
    }
    public List<Choice> getChoicesByQuestionId(int questionId) {
        String sql = "SELECT * FROM Choice WHERE question_id = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Choice.class), questionId);
    }

    public List<Integer> findSelectedChoicesByQuizId(int quizId) {
        String sql = "SELECT user_choice_id FROM QuizQuestion WHERE quiz_id = ?";
        return jdbcTemplate.queryForList(sql, Integer.class, quizId);
    }

}
