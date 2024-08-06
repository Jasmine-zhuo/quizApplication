package com.bfs.logindemo.dao;

import com.bfs.logindemo.domain.*;
import com.bfs.logindemo.util.JdbcUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.hibernate.query.Query;
import java.sql.Timestamp;

import java.util.List;

@Repository
public class QuizDao {

    private final JdbcTemplate jdbcTemplate;
    private final SessionFactory sessionFactory;

    public QuizDao(JdbcTemplate jdbcTemplate, SessionFactory sessionFactory) {
        this.jdbcTemplate = jdbcTemplate;
        this.sessionFactory = sessionFactory;
    }

    // Hibernate Methods

    public int addQuizHibernate(int userId, int categoryId, String quizName) {
        Session session = sessionFactory.getCurrentSession();
        //Session session = sessionFactory.openSession();
        User user = session.get(User.class, userId);
        Category category = session.get(Category.class, categoryId);
        Quiz quiz = new Quiz();
        quiz.setUser(user);
        quiz.setCategory(category);
        quiz.setName(quizName);
        quiz.setTimeStart(new Timestamp(System.currentTimeMillis()));
        session.save(quiz);
        return quiz.getQuizId();
    }

    public void saveQuizResultAndSelectedChoiceHibernate(int quizId, List<Question> questions, List<Integer> selectedChoiceIds, String quizResult) {
        Session session = sessionFactory.getCurrentSession();
        Quiz quiz = session.get(Quiz.class, quizId);
        quiz.setName(quizResult);
        quiz.setTimeEnd(new Timestamp(System.currentTimeMillis()));
        session.update(quiz);

        for (int i = 0; i < questions.size(); i++) {
            QuizQuestion quizQuestion = new QuizQuestion();
            quizQuestion.setQuiz(quiz);
            quizQuestion.setQuestion(questions.get(i));
//            quizQuestion.setUserChoiceId(selectedChoiceIds.get(i));
            Integer selectedChoiceId = selectedChoiceIds.get(i);
            if (selectedChoiceId != null) {
                Choice selectedChoice = session.get(Choice.class, selectedChoiceId);
                quizQuestion.setUserChoice(selectedChoice); // Ensure this is set
            }
            session.save(quizQuestion);

            System.out.println("Saved QuizQuestion: " + quizQuestion);
        }
    }

    public List<Quiz> findRecentQuizzesByUserIdHibernate(int userId) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Quiz where user.userId = :userId order by timeEnd desc", Quiz.class)
                .setParameter("userId", userId)
                .setMaxResults(10)
                .getResultList();
    }

    public void updateQuizEndTimeHibernate(int quizId) {
        Session session = sessionFactory.getCurrentSession();
        Quiz quiz = session.get(Quiz.class, quizId);
        quiz.setTimeEnd(new Timestamp(System.currentTimeMillis()));
        session.update(quiz);
    }

    /**
     * find question and user choice
     * @param quizId
     * @return
     */
    public List<Question> findQuestionsByQuizIdHibernate(int quizId) {
        Session session = sessionFactory.getCurrentSession();

        List<Question> questions = session.createQuery(
                "select q from Question q join q.quizQuestions qq where qq.quiz.quizId = :quizId", Question.class)
                .setParameter("quizId", quizId)
                .getResultList();

        for (Question question : questions) {
            List<Choice> choices = session.createQuery(
                    "from Choice c where c.question.questionId = :questionId", Choice.class)
                    .setParameter("questionId", question.getQuestionId())
                    .getResultList();
            question.setChoices(choices);
            // Fetch user choices for each question if needed
            List<Integer> userChoiceIds = session.createQuery(
                            "select qq.userChoice.choiceId from QuizQuestion qq where qq.question.questionId = :questionId and qq.quiz.quizId = :quizId", Integer.class)
                    .setParameter("questionId", question.getQuestionId())
                    .setParameter("quizId", quizId)
                    .getResultList();

            // Process userChoiceIds if needed
            System.out.println("User choices for question " + question.getQuestionId() + ": " + userChoiceIds);
        }

        return questions;
    }

    public List<Integer> findSelectedChoicesByQuizIdHibernate(int quizId) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery(
                "select qq.userChoice.choiceId from QuizQuestion qq where qq.quiz.quizId = :quizId", Integer.class)
                .setParameter("quizId", quizId)
                .getResultList();
    }

    public List<QuizWithDetailsDTO> findAllWithUserDetailsHibernate() {
        Session session = sessionFactory.getCurrentSession();
        String hql = "SELECT new com.bfs.logindemo.dao.QuizWithDetailsDTO(q.quizId, q.user.userId, q.category.categoryId, q.name, q.timeStart, q.timeEnd, u.firstname, u.lastname, c.name) " +
                "FROM Quiz q JOIN q.user u JOIN q.category c ORDER BY q.timeEnd DESC";
        return session.createQuery(hql, QuizWithDetailsDTO.class).getResultList();
    }

    public List<QuizWithDetailsDTO> findAllWithFiltersHibernate(Integer categoryId, Integer userId) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "SELECT new com.bfs.logindemo.dao.QuizWithDetailsDTO(" +
                "q.quizId, q.user.userId, q.category.categoryId, q.name, q.timeStart, q.timeEnd, " +
                "u.firstname, u.lastname, c.name, " +
                "cast(count(qq.qqId) as int), " +
                "cast(sum(case when uc.isCorrect = true then 1 else 0 end) as int)) " +
                "FROM Quiz q " +
                "JOIN q.user u " +
                "JOIN q.category c " +
                "LEFT JOIN q.quizQuestions qq " +
                "LEFT JOIN qq.userChoice uc " +
                "WHERE 1=1 ";


        if (categoryId != null && categoryId > 0) {
            hql += " AND q.category.categoryId = :categoryId";
        }
        if (userId != null && userId > 0) {
            hql += " AND q.user.userId = :userId";
        }

        hql += " GROUP BY q.quizId, q.user.userId, q.category.categoryId, q.name, q.timeStart, q.timeEnd, u.firstname, u.lastname, c.name " +
                "ORDER BY q.timeEnd DESC";

        Query<QuizWithDetailsDTO> query = session.createQuery(hql, QuizWithDetailsDTO.class);
        if (categoryId != null && categoryId > 0) {
            query.setParameter("categoryId", categoryId);
        }
        if (userId != null && userId > 0) {
            query.setParameter("userId", userId);
        }

        return query.getResultList();
    }

    public List<Quiz> findAllHibernate() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Quiz", Quiz.class).getResultList();
    }

    public Quiz findQuizByIdHibernate(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Quiz.class, id);
    }

    public List<Quiz> findByUserIdHibernate(int userId) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Quiz where user.userId = :userId", Quiz.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    public void saveHibernate(Quiz quiz) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(quiz);
    }

    public void updateHibernate(Quiz quiz) {
        Session session = sessionFactory.getCurrentSession();
        session.update(quiz);
    }

    public void deleteHibernate(int id) {
        Session session = sessionFactory.getCurrentSession();
        Quiz quiz = session.get(Quiz.class, id);
        if (quiz != null) {
            session.delete(quiz);
        }
    }

    public List<Category> findAllCategoriesHibernate() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Category", Category.class).getResultList();
    }

    public List<Choice> getChoicesByQuestionIdHibernate(int questionId) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Choice where question.questionId = :questionId", Choice.class)
                .setParameter("questionId", questionId)
                .getResultList();
    }

    public Question findQuestionByIdHibernate(int id) {
        Session session = sessionFactory.getCurrentSession();
        Question question = session.get(Question.class, id);
        if (question != null) {
            question.setChoices(getChoicesByQuestionIdHibernate(question.getQuestionId()));
        }
        return question;
    }


    //Jdbc methods
//    public List<Category> findAllCategoriesJdbc() {
//        String sql = "SELECT * FROM Category";
//        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Category.class));
//    }
//
//    public List<Quiz> findByUserIdJdbc(int userId) {
//        String sql = "SELECT * FROM Quiz WHERE user_id = ?";
//        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Quiz.class), userId);
//    }
//
//    public Quiz findByIdJdbc(int id) {
//        String sql = "SELECT * FROM Quiz WHERE quiz_id = ?";
//        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Quiz.class), id);
//    }
//
//    public void saveJdbc(Quiz quiz) {
//        String sql = "INSERT INTO Quiz (user_id, category_id, name, time_start, time_end) VALUES (?, ?, ?, ?, ?)";
//        jdbcTemplate.update(sql, quiz.getUserId(), quiz.getCategoryId(), quiz.getName(), quiz.getTimeStart(), quiz.getTimeEnd());
//    }
//
//    public void updateJdbc(Quiz quiz) {
//        String sql = "UPDATE Quiz SET user_id = ?, category_id = ?, name = ?, time_start = ?, time_end = ? WHERE quiz_id = ?";
//        jdbcTemplate.update(sql, quiz.getUserId(), quiz.getCategoryId(), quiz.getName(), quiz.getTimeStart(), quiz.getTimeEnd(), quiz.getQuizId());
//    }
//
//    public void deleteJdbc(int id) {
//        String sql = "DELETE FROM Quiz WHERE quiz_id = ?";
//        jdbcTemplate.update(sql, id);
//    }
//
//    public List<Quiz> findAllJdbc() {
//        String sql = "SELECT * FROM Quiz";
//        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Quiz.class));
//    }
//
//    public List<Quiz> findRecentQuizzesByUserIdJdbc(int userId) {
//        String sql = "SELECT * FROM Quiz WHERE user_id = ? ORDER BY time_end DESC LIMIT 10";
//        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Quiz.class), userId);
//    }
//    public int createQuizJdbc(int userId, int categoryId, String quizName) {
//        String quizSql = "INSERT INTO Quiz (user_id, category_id, name, time_start) VALUES (?, ?, ?, NOW())";
//        // debug statement
//        System.out.println("Executing SQL: " + quizSql);
//        System.out.println("Parameters: " + userId + ", " + categoryId + ", " + quizName);
//        jdbcTemplate.update(quizSql, userId, categoryId, quizName);
//
//        String quizIdSql = "SELECT LAST_INSERT_ID()";
//        System.out.println("Executing SQL to get last inserted quiz ID: " + quizIdSql);
//        int quizId = jdbcTemplate.queryForObject(quizIdSql, Integer.class);
//        System.out.println("Retrieved quiz ID: " + quizId);
//
//        return quizId;
//    }
//
//    public void saveQuizResultAndSelectedChoiceJdbc(int quizId, List<Question> questions, List<Integer> selectedChoiceIds, String quizResult) {
//        String updateQuizSql = "UPDATE Quiz SET name = ?, time_end = NOW() WHERE quiz_id = ?";
//        // debug statement
//        System.out.println("Executing SQL: " + updateQuizSql);
//        System.out.println("Parameters: " + quizResult + ", " + quizId);
//        jdbcTemplate.update(updateQuizSql, quizResult, quizId);
//
//        String questionSql = "INSERT INTO QuizQuestion (quiz_id, question_id, user_choice_id) VALUES (?, ?, ?)";
//        for (int i = 0; i < questions.size(); i++) {
//            System.out.println("Executing SQL: " + questionSql);
//            System.out.println("Parameters: " + quizId + ", " + questions.get(i).getQuestionId() + ", " + selectedChoiceIds.get(i));
//            jdbcTemplate.update(questionSql, quizId, questions.get(i).getQuestionId(), selectedChoiceIds.get(i));
//        }
//    }
//
//    public void updateQuizEndTimeJdbc(int quizId) {
//        // Update the Quiz table with time_end
//        String updateQuizSql = "UPDATE Quiz SET time_end = ? WHERE quiz_id = ?";
//        Timestamp currentTimeStamp = new Timestamp(System.currentTimeMillis());
//        jdbcTemplate.update(updateQuizSql, currentTimeStamp, quizId);
//    }
//
//    public List<Question> findQuestionsByQuizIdJdbc(int quizId) {
//        String sql = "SELECT q.* FROM Question q INNER JOIN QuizQuestion qq ON q.question_id = qq.question_id WHERE qq.quiz_id = ?";
//        List<Question> questions = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Question.class), quizId);
//
//        // Fetch and set choices for each question
//        for (Question question : questions) {
//            List<Choice> choices = getChoicesByQuestionIdJdbc(question.getQuestionId());
//            question.setChoices(choices);
//        }
//
//        return questions;
//    }
//    public List<Choice> getChoicesByQuestionIdJdbc(int questionId) {
//        String sql = "SELECT * FROM Choice WHERE question_id = ?";
//        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Choice.class), questionId);
//    }
//
//    public List<Integer> findSelectedChoicesByQuizIdJdbc(int quizId) {
//        String sql = "SELECT user_choice_id FROM QuizQuestion WHERE quiz_id = ?";
//        return jdbcTemplate.queryForList(sql, Integer.class, quizId);
//    }
//    public List<QuizWithDetailsDTO> findAllWithUserDetailsJdbc() {
//        String sql = "SELECT q.*, u.firstname, u.lastname, c.name AS categoryName FROM Quiz q " +
//                "JOIN User u ON q.user_id = u.user_id " +
//                "JOIN Category c ON q.category_id = c.category_id " +
//                "ORDER BY q.time_end DESC";
//
//        return jdbcTemplate.query(sql, (rs, rowNum) -> {
//            QuizWithDetailsDTO quiz = new QuizWithDetailsDTO();
//            quiz.setQuizId(rs.getInt("quiz_id"));
//            quiz.setUserId(rs.getInt("user_id"));
//            quiz.setCategoryId(rs.getInt("category_id"));
//            quiz.setName(rs.getString("name"));
//            quiz.setTimeStart(rs.getTimestamp("time_start"));
//            quiz.setTimeEnd(rs.getTimestamp("time_end"));
//            quiz.setFirstname(rs.getString("firstname"));
//            quiz.setLastname(rs.getString("lastname"));
//            quiz.setCategoryName(rs.getString("categoryName"));
//            //quiz.setNumQuestions(calculateNumQuestions(rs.getInt("quiz_id")));
//            //quiz.setScore(calculateScore(rs.getInt("quiz_id")));
//            return quiz;
//        });
//    }
//    private int calculateNumQuestionsJdbc(int quizId) {
//        // Implement the logic to calculate the number of questions for the given quizId
//        String sql = "SELECT COUNT(*) FROM QuizQuestion WHERE quiz_id = ?";
//        return jdbcTemplate.queryForObject(sql, Integer.class, quizId);
//    }
//
//    private int calculateScoreJdbc(int quizId) {
//        // Implement the logic to calculate the score for the given quizId
//        String sql = "SELECT COUNT(*) FROM QuizQuestion qq JOIN Choice c ON qq.user_choice_id = c.choice_id WHERE qq.quiz_id = ? AND c.is_correct = TRUE";
//        return jdbcTemplate.queryForObject(sql, Integer.class, quizId);
//    }
//
//    public List<QuizWithDetailsDTO> findAllWithFiltersJdbc(Integer categoryId, Integer userId) {
//        String sql = "SELECT q.*, u.firstname, u.lastname, c.name as categoryName, " +
//                "(SELECT COUNT(*) FROM QuizQuestion qq WHERE qq.quiz_id = q.quiz_id) as numQuestions, " +
//                "(SELECT COUNT(*) FROM QuizQuestion qq JOIN Choice ch ON qq.user_choice_id = ch.choice_id WHERE qq.quiz_id = q.quiz_id AND ch.is_correct = true) as score " +
//                "FROM Quiz q " +
//                "JOIN User u ON q.user_id = u.user_id " +
//                "JOIN Category c ON q.category_id = c.category_id " +
//                "WHERE 1=1 ";
//
//
//        if (categoryId != null && categoryId > 0) {
//            sql += " AND q.category_id = " + categoryId;
//        }
//        if (userId != null && userId > 0) {
//            sql += " AND q.user_id = " + userId;
//        }
//
//        sql += " ORDER BY q.time_end DESC";
//
//        return jdbcTemplate.query(sql, (rs, rowNum) -> {
//            QuizWithDetailsDTO quiz = new QuizWithDetailsDTO();
//            quiz.setQuizId(rs.getInt("quiz_id"));
//            quiz.setUserId(rs.getInt("user_id"));
//            quiz.setCategoryId(rs.getInt("category_id"));
//            quiz.setCategoryName(rs.getString("categoryName"));
//            quiz.setFirstname(rs.getString("firstname"));
//            quiz.setLastname(rs.getString("lastname"));
//            quiz.setTimeStart(rs.getTimestamp("time_start"));
//            quiz.setTimeEnd(rs.getTimestamp("time_end"));
//            quiz.setName(rs.getString("name"));
//            quiz.setNumQuestions(rs.getInt("numQuestions"));
//            quiz.setScore(rs.getInt("score"));
//            return quiz;
//        });
//    }

}
