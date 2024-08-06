package com.bfs.logindemo.dao;

import com.bfs.logindemo.domain.QuizQuestion;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class QuizQuestionDao {
    private final JdbcTemplate jdbcTemplate;
    private final SessionFactory sessionFactory;

    public QuizQuestionDao(JdbcTemplate jdbcTemplate, SessionFactory sessionFactory) {
        this.jdbcTemplate = jdbcTemplate;
        this.sessionFactory = sessionFactory;
    }

    // Hibernate Methods
    public List<QuizQuestion> findByQuizIdHibernate(int quizId) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from QuizQuestion where quiz.quizId = :quizId", QuizQuestion.class)
                .setParameter("quizId", quizId)
                .getResultList();
    }

    public void saveHibernate(QuizQuestion quizQuestion) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(quizQuestion);
    }

    public void updateHibernate(QuizQuestion quizQuestion) {
        Session session = sessionFactory.getCurrentSession();
        session.update(quizQuestion);
    }

    public void deleteHibernate(int id) {
        Session session = sessionFactory.getCurrentSession();
        QuizQuestion quizQuestion = session.get(QuizQuestion.class, id);
        if (quizQuestion != null) {
            session.delete(quizQuestion);
        }
    }


//    public List<QuizQuestion> findByQuizIdJdbc(int quizId) {
//        String sql = "SELECT * FROM QuizQuestion WHERE quiz_id = ?";
//        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(QuizQuestion.class), quizId);
//    }
//
//    public void saveJdbc(QuizQuestion quizQuestion) {
//        String sql = "INSERT INTO QuizQuestion (quiz_id, question_id, user_choice_id) VALUES (?, ?, ?)";
//        jdbcTemplate.update(sql, quizQuestion.getQuizId(), quizQuestion.getQuestionId(), quizQuestion.getUserChoiceId());
//    }
//
//    public void updateJdbc(QuizQuestion quizQuestion) {
//        String sql = "UPDATE QuizQuestion SET quiz_id = ?, question_id = ?, user_choice_id = ? WHERE qq_id = ?";
//        jdbcTemplate.update(sql, quizQuestion.getQuizId(), quizQuestion.getQuestionId(), quizQuestion.getUserChoiceId(), quizQuestion.getQqId());
//    }
//
//    public void deleteJdbc(int id) {
//        String sql = "DELETE FROM QuizQuestion WHERE qq_id = ?";
//        jdbcTemplate.update(sql, id);
//    }
}
