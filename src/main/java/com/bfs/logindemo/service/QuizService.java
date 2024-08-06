package com.bfs.logindemo.service;

import com.bfs.logindemo.dao.QuestionDao;
import com.bfs.logindemo.dao.QuizDao;
import com.bfs.logindemo.dao.QuizWithDetailsDTO;
import com.bfs.logindemo.dao.UserDao;
import com.bfs.logindemo.domain.Category;
import com.bfs.logindemo.domain.Quiz;
import com.bfs.logindemo.domain.Question;
import com.bfs.logindemo.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuizService {
    private final QuizDao quizDao;
    private final QuestionDao questionDao;
    private final UserDao userDao ;
    private final QuizQuestionService quizQuestionService;

    @Autowired
    public QuizService(QuestionDao questionDao, QuizDao quizDao, UserDao userDao, QuizQuestionService quizQuestionService) {
        this.questionDao = questionDao;
        this.quizDao = quizDao;
        this.userDao = userDao;
        this.quizQuestionService = quizQuestionService;
    }

    @Transactional(readOnly = true)
    public List<Question> getRandomQuestionsByCategory(int categoryId) {
        List<Question> questions = questionDao.findByCategoryIdHibernate(categoryId);
        // Filter out inactive questions
        questions = questions.stream()
                .filter(Question::isActive)
                .collect(Collectors.toList());
        Collections.shuffle(questions);
        List<Question> randomQuestions = questions.subList(0, Math.min(questions.size(), 5));

        for (Question question : randomQuestions) {
            question.setChoices(questionDao.findChoicesByQuestionIdHibernate(question.getQuestionId()));
        }

        return randomQuestions;
    }

    @Transactional(readOnly = true)
    public List<Question> getQuestionsByCategoryId(int categoryId) {
        List<Question> questions = questionDao.findByCategoryIdHibernate(categoryId);
        for (Question question : questions) {
            question.setChoices(questionDao.findChoicesByQuestionIdHibernate(question.getQuestionId()));
        }
        return questions;
    }

    @Transactional(readOnly = true)
    public Optional<Choice> getChoiceById(int selectedChoiceId) {
        return questionDao.getChoiceByIdHibernate(selectedChoiceId);
    }

    @Transactional(readOnly = true)
    public String checkAnswer(Choice selectedChoice) {
        return selectedChoice.isCorrect() ? "Correct!" : "Incorrect";
    }

//    public int saveQuizResult(Object user, List<Integer> selectedChoiceIds) {
//        User loggedInUser = (User) user;
//        return quizDao.saveQuizResult(loggedInUser, selectedChoiceIds);
//    }

    @Transactional
    public int startQuiz(int userId, int categoryId) {
        return quizDao.addQuizHibernate(userId, categoryId, "Quiz Started");
    }

    @Transactional
    public void saveQuizResultAndSelectedChoice(int quizId, List<Question> questions, List<Integer> selectedChoiceIds, String quizResult) {
        quizDao.saveQuizResultAndSelectedChoiceHibernate(quizId, questions, selectedChoiceIds, quizResult);
    }

    @Transactional(readOnly = true)
    public List<Quiz> getQuizzesByUserId(int userId) {
        return quizDao.findByUserIdHibernate(userId);
    }

    @Transactional(readOnly = true)
    public Quiz getQuizById(int userId) {
        return quizDao.findQuizByIdHibernate(userId);
    }

    @Transactional
    public void saveQuiz(Quiz quiz) {
        quizDao.saveHibernate(quiz);
    }

    @Transactional
    public void updateQuiz(Quiz quiz) {
        quizDao.updateHibernate(quiz);
    }

    @Transactional
    public void deleteQuiz(int id) {
        quizDao.deleteHibernate(id);
    }

    @Transactional(readOnly = true)
    public List<Category> getAllCategories() {
        return questionDao.findAllCategoriesHibernate();
    }

    @Transactional(readOnly = true)
    public List<Quiz> getRecentQuizResults(int userId) {
        return quizDao.findRecentQuizzesByUserIdHibernate(userId);
    }

    @Transactional(readOnly = true)
    public List<Quiz> getAllQuizzes() {
        return quizDao.findAllHibernate();
    }

    @Transactional
    public void updateQuizEndTime(int quizId) {
        quizDao.updateQuizEndTimeHibernate(quizId);
    }

    @Transactional(readOnly = true)
    public List<Question> getQuestionsByQuizId(int quizId) {
        return quizDao.findQuestionsByQuizIdHibernate(quizId);
    }

    @Transactional(readOnly = true)
    public List<Integer> getSelectedChoicesByQuizId(int quizId) {
        return quizDao.findSelectedChoicesByQuizIdHibernate(quizId);
    }

    @Transactional(readOnly = true)
    public List<Choice> getChoicesByQuestionId(int questionId) {
        return questionDao.findChoicesByQuestionIdHibernate(questionId);
    }

    @Transactional(readOnly = true)
    public List<QuizWithDetailsDTO> getAllQuizzesWithUserDetails() {
        return quizDao.findAllWithUserDetailsHibernate();
    }

    @Transactional(readOnly = true)
    public User getUserById(int userId) {
        return userDao.findByIdHibernate(userId).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<QuizWithDetailsDTO> getQuizzesWithFilters(Integer categoryId, Integer userId) {
        return quizDao.findAllWithFiltersHibernate(categoryId, userId);
    }
    @Transactional(readOnly = true)
    public List<QuizWithDetailsDTO> getQuizzesWithDetailedScores(Integer categoryId, Integer userId) {
        List<QuizWithDetailsDTO> quizzes = quizDao.findAllWithFiltersHibernate(categoryId, userId);

        for (QuizWithDetailsDTO quiz : quizzes) {
            int score = calculateScoreForQuiz(quiz.getQuizId());
            quiz.setScore(score);
        }

        return quizzes;
    }

    private int calculateScoreForQuiz(int quizId) {
        // Get all QuizQuestions for the quiz
        List<QuizQuestion> quizQuestions = quizQuestionService.getQuizQuestionsByQuizId(quizId);

        // Calculate score based on correct answers
        int score = 0;
        for (QuizQuestion qq : quizQuestions) {
            if (qq.getUserChoice() != null && qq.getUserChoice().isCorrect()) {
                score++;
            }
        }

        return score;
    }

}
