package com.bfs.logindemo.dao;

import com.bfs.logindemo.domain.Category;
import com.bfs.logindemo.domain.Choice;
import com.bfs.logindemo.domain.Question;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.hibernate.query.Query;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Repository
public class QuestionDao {
    private final JdbcTemplate jdbcTemplate;
    private final SessionFactory sessionFactory;

    @Autowired
    public QuestionDao(JdbcTemplate jdbcTemplate, SessionFactory sessionFactory) {
        this.jdbcTemplate = jdbcTemplate;
        this.sessionFactory = sessionFactory;
    }
    public Question getQuestionHibernate(int questionId) {
        Session session = sessionFactory.getCurrentSession();
        Question question = session.get(Question.class, questionId);
        if (question != null) {
            question.setChoices(findChoicesByQuestionIdHibernate(question.getQuestionId()));
        }
        return question;
    }

    public Optional<Choice> getChoiceByIdHibernate(int choiceId) {
        Session session = sessionFactory.getCurrentSession();
        Choice choice = session.get(Choice.class, choiceId);
        return Optional.ofNullable(choice);
    }

    public List<Question> findByCategoryIdHibernate(int categoryId) {
        Session session = sessionFactory.getCurrentSession();
        Query<Question> query = session.createQuery("from Question where category.categoryId = :categoryId", Question.class);
        query.setParameter("categoryId", categoryId);
        return query.getResultList();
    }

    // Hibernate Methods
    public List<Question> findAllHibernate() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Question", Question.class).getResultList();
    }

    public Question findByIdHibernate(int id) {
        Session session = sessionFactory.getCurrentSession();
        Question question = session.get(Question.class, id);
        if (question != null) {
            question.setChoices(findChoicesByQuestionIdHibernate(question.getQuestionId()));
        }
        return question;
    }

    public List<Choice> findChoicesByQuestionIdHibernate(int questionId) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Choice where question.questionId = :questionId", Choice.class)
                .setParameter("questionId", questionId)
                .getResultList();
    }

    public void saveHibernate(Question question) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(question);
        if (question.getChoices() != null) {
            for (Choice choice : question.getChoices()) {
                saveChoiceHibernate(choice);
            }
        }
    }

    public void saveChoiceHibernate(Choice choice) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(choice);
    }

    public void updateHibernate(Question question) {
        Session session = sessionFactory.getCurrentSession();
        session.update(question);
        if (question.getChoices() != null) {
            for (Choice choice : question.getChoices()) {
                updateChoiceHibernate(choice);
            }
        }
    }

    public void updateChoiceHibernate(Choice choice) {
        Session session = sessionFactory.getCurrentSession();
        session.update(choice);
    }

    public void deleteHibernate(int id) {
        Session session = sessionFactory.getCurrentSession();
        Question question = session.get(Question.class, id);
        if (question != null) {
            session.delete(question);
        }
    }

    public void deleteChoiceHibernate(int id) {
        Session session = sessionFactory.getCurrentSession();
        Choice choice = session.get(Choice.class, id);
        if (choice != null) {
            session.delete(choice);
        }
    }
    public void toggleQuestionStatusHibernate(int questionId) {
        Session session = sessionFactory.getCurrentSession();
        Question question = session.get(Question.class, questionId);
        if (question != null) {
            question.setActive(!question.isActive());
            session.update(question);
        }
    }

    public List<QuestionWithCategoryDTO> findAllQuestionsWithCategoryNameHibernate() {
        Session session = sessionFactory.getCurrentSession();
        String hql = "SELECT new com.bfs.logindemo.dao.QuestionWithCategoryDTO(q, c.name) " +
                "FROM Question q JOIN q.category c";
        Query<QuestionWithCategoryDTO> query = session.createQuery(hql, QuestionWithCategoryDTO.class);
        return query.getResultList();
    }

    public void updateQuestionDescriptionHibernate(int questionId, String description) {
        Session session = sessionFactory.getCurrentSession();
        Question question = session.get(Question.class, questionId);
        if (question != null) {
            question.setDescription(description);
            session.update(question);
        }
    }

    public int addQuestionHibernate(int categoryId, String description) {
        Session session = sessionFactory.getCurrentSession();
        Category category = session.get(Category.class, categoryId);
        Question question = new Question();
        question.setCategory(category);
        question.setDescription(description);
        question.setActive(true); // Assuming default active state
        session.save(question);
        return question.getQuestionId();
    }

    public void addChoiceHibernate(int questionId, String description, boolean isCorrect) {
        Session session = sessionFactory.getCurrentSession();
        Question question = session.get(Question.class, questionId);
        Choice choice = new Choice();
        choice.setQuestion(question);
        choice.setDescription(description);
        choice.setCorrect(isCorrect);
        session.save(choice);
    }

    public List<Category> findAllCategoriesHibernate() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Category", Category.class).getResultList();
    }

//
//    //Jdbc methods
//    private static final class QuestionRowMapper implements RowMapper<Question> {
//        @Override
//        public Question mapRow(ResultSet rs, int rowNum) throws SQLException {
//            Question question = new Question();
//            question.setQuestionId(rs.getInt("question_id"));
//            //question.setCategoryId(rs.getInt("category_id"));
//            question.setDescription(rs.getString("description"));
//            question.setActive(rs.getBoolean("is_active"));
//            return question;
//        }
//    }
//
//    private static final class ChoiceRowMapper implements RowMapper<Choice> {
//        @Override
//        public Choice mapRow(ResultSet rs, int rowNum) throws SQLException {
//            Choice choice = new Choice();
//            choice.setChoiceId(rs.getInt("choice_id"));
//            //choice.setQuestionId(rs.getInt("question_id"));
//            choice.setDescription(rs.getString("description"));
//            choice.setCorrect(rs.getBoolean("is_correct")); // Ensure this line correctly maps the is_correct field
//            return choice;
//        }
//    }
//    public List<Question> getAllQuestionsJdbc() {
//        String sql = "SELECT * FROM Question";
//        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Question.class));
//    }
//    public List<Question> findByCategoryIdJdbc(int categoryId) {
//        String sql = "SELECT * FROM Question WHERE category_id = ?";
//        return jdbcTemplate.query(sql, new Object[]{categoryId}, new QuestionRowMapper());
//    }
//
//    public List<Choice> getChoicesByQuestionIdJdbc(int questionId) {
//        String sql = "SELECT * FROM Choice WHERE question_id = ?";
//        return jdbcTemplate.query(sql, new Object[]{questionId}, new ChoiceRowMapper());
//    }
//
//    public List<Question> findRandomByCategoryIdJdbc(int categoryId, int limit) {
//        String sql = "SELECT * FROM Question WHERE category_id = ? ORDER BY RAND() LIMIT ?";
//        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Question.class), categoryId, limit);
//    }
//
//    public List<Category> findAllCategoriesJdbc() {
//        String sql = "SELECT * FROM Category";
//        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Category.class));
//    }
//    public Optional<Choice> getChoiceByIdJdbc(int choiceId){
//        String sql = "SELECT * FROM Choice WHERE choice_id = ?";
//        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Choice.class), choiceId).stream().findFirst();
//    }
//
//
//    public Question findByIdJdbc(int id) {
//        String sql = "SELECT * FROM Question WHERE question_id = ?";
//        Question question = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Question.class), id);
//        if (question != null) {
//            question.setChoices(getChoicesByQuestionIdJdbc(question.getQuestionId()));
//        }
//        return question;
//    }
//
//    public void saveJdbc(Question question) {
//        String sql = "INSERT INTO Question (category_id, description) VALUES (?, ?)";
//        //jdbcTemplate.update(sql, question.getCategoryId(), question.getDescription());
//        if (question.getChoices() != null) {
//            for (Choice choice : question.getChoices()) {
//                saveChoiceJdbc(choice);
//            }
//        }
//    }
//
//    public void saveChoiceJdbc(Choice choice) {
//        String sql = "INSERT INTO Choice (questionId, description, isCorrect) VALUES (?, ?, ?)";
//        //jdbcTemplate.update(sql, choice.getQuestionId(), choice.getDescription(), choice.isCorrect());
//    }
//
//    public void updateJdbc(Question question) {
//        String sql = "UPDATE Question SET category_id = ?, description = ?, is_active = ? WHERE question_id = ?";
//        //jdbcTemplate.update(sql, question.getCategoryId(), question.getDescription(), question.isActive(), question.getQuestionId());
//        if (question.getChoices() != null) {
//            for (Choice choice : question.getChoices()) {
//                updateChoiceJdbc(choice);
//            }
//        }
//    }
//
//
//    public void deleteJdbc(int id) {
//        String sql = "DELETE FROM Question WHERE question_id = ?";
//        jdbcTemplate.update(sql, id);
//    }
//    public void deleteChoice(int id) {
//        String sql = "DELETE FROM Choice WHERE choice_id = ?";
//        jdbcTemplate.update(sql, id);
//    }
//
//    public List<Question> findAll() {
//        String sql = "SELECT * FROM Question";
//        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Question.class));
//    }
//
//    public Question getQuestionById(int id) {
//        String sql = "SELECT * FROM question WHERE question_id = ?";
//        Question question = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Question.class), id);
//        if (question != null) {
//            question.setChoices(getChoicesByQuestionIdJdbc(question.getQuestionId()));
//        }
//        return question;
//    }
//
//    public Question getQuestionJdbc() {
//        String sql = "SELECT * FROM Question WHERE question_id = ?";
//        Question question = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Question.class), 1); // Example: get question with ID 1
//
//        String choiceSql = "SELECT * FROM Choice WHERE question_id = ?";
//        List<Choice> choices = jdbcTemplate.query(choiceSql, new BeanPropertyRowMapper<>(Choice.class), question.getQuestionId());
//
//        question.setChoices(choices);
//        return question;
//    }
//    public void toggleQuestionStatusJdbc(int questionId) {
//        String sql = "UPDATE Question SET is_active = NOT is_active WHERE question_id = ?";
//        jdbcTemplate.update(sql, questionId);
//    }
//    private static class QuestionWithCategoryRowMapper implements RowMapper<QuestionWithCategoryDTO> {
//        @Override
//        public QuestionWithCategoryDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
//            Question question = new Question();
//            question.setQuestionId(rs.getInt("question_id"));
//            //question.setCategoryId(rs.getInt("category_id"));
//            question.setDescription(rs.getString("description"));
//            question.setActive(rs.getBoolean("is_active"));
//            // You may need to set other properties if needed
//
//            String categoryName = rs.getString("categoryName");
//
//            return new QuestionWithCategoryDTO(question, categoryName);
//        }
//    }
//    public List<QuestionWithCategoryDTO> findAllQuestionsWithCategoryNameJdbc() {
//        String sql = "SELECT q.*, c.name as categoryName " +
//                "FROM Question q " +
//                "JOIN Category c ON q.category_id = c.category_id";
//        return jdbcTemplate.query(sql, new QuestionWithCategoryRowMapper());
//    }
//    public List<Choice> findChoicesByQuestionIdJdbc(int questionId) {
//        String sql = "SELECT * FROM Choice WHERE question_id = ?";
//        return jdbcTemplate.query(sql, new Object[]{questionId}, new BeanPropertyRowMapper<>(Choice.class));
//    }
//
//    public void updateQuestionDescriptionJdbc(int questionId, String description) {
//        String sql = "UPDATE Question SET description = ? WHERE question_id = ?";
//        jdbcTemplate.update(sql, description, questionId);
//    }
//
//    public void updateChoiceJdbc(Choice choice) {
//        String sql = "UPDATE Choice SET description = ?, is_correct = ? WHERE choice_id = ?";
//        jdbcTemplate.update(sql, choice.getDescription(), choice.isCorrect(), choice.getChoiceId());
//    }
//    public int addQuestionJdbc(int categoryId, String description) {
//        String sql = "INSERT INTO Question (category_id, description, is_active) VALUES (?, ?, TRUE)";
//        KeyHolder keyHolder = new GeneratedKeyHolder();
//        jdbcTemplate.update(connection -> {
//            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"question_id"});
//            ps.setInt(1, categoryId);
//            ps.setString(2, description);
//            return ps;
//        }, keyHolder);
//        return keyHolder.getKey().intValue();
//    }
//
//    public void addChoiceJdbc(int questionId, String description, boolean isCorrect) {
//        String sql = "INSERT INTO Choice (question_id, description, is_correct) VALUES (?, ?, ?)";
//        jdbcTemplate.update(sql, questionId, description, isCorrect);
//    }

}
