package com.bfs.logindemo.dao;
import com.bfs.logindemo.dao.ChoiceDao;
import com.bfs.logindemo.domain.Choice;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class ChoiceDao {
    private final JdbcTemplate jdbcTemplate;
    private final SessionFactory sessionFactory;

    @Autowired
    public ChoiceDao(JdbcTemplate jdbcTemplate, SessionFactory sessionFactory) {
        this.jdbcTemplate = jdbcTemplate;
        this.sessionFactory = sessionFactory;
    }

    //Hibernate method
    public List<Choice> findByQuestionIdHibernate(int questionId) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Choice where question.questionId = :questionId", Choice.class)
                .setParameter("questionId", questionId)
                .getResultList();
    }

    public void saveHibernate(Choice choice) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(choice);
    }

    public void updateHibernate(Choice choice) {
        Session session = sessionFactory.getCurrentSession();
        session.update(choice);
    }

    public void deleteHibernate(int id) {
        Session session = sessionFactory.getCurrentSession();
        Choice choice = session.get(Choice.class, id);
        if (choice != null) {
            session.delete(choice);
        }
    }
//    public List<Choice> findByQuestionIdJdbc(int questionId) {
//        String sql = "SELECT * FROM Choice WHERE question_id = ?";
//        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Choice.class), questionId);
//    }
//
//    public void saveJdbc(Choice choice) {
//        String sql = "INSERT INTO Choice (question_id, description, is_correct) VALUES (?, ?, ?)";
//        //jdbcTemplate.update(sql, choice.getQuestionId(), choice.getDescription(), choice.isCorrect());
//    }
//
//    public void updateJdbc(Choice choice) {
//        String sql = "UPDATE Choice SET question_id = ?, description = ?, is_correct = ? WHERE choice_id = ?";
//        //jdbcTemplate.update(sql, choice.getQuestionId(), choice.getDescription(), choice.isCorrect(), choice.getChoiceId());
//    }
//
//    public void deleteJdbc(int id) {
//        String sql = "DELETE FROM Choice WHERE choice_id = ?";
//        jdbcTemplate.update(sql, id);
//    }

}
