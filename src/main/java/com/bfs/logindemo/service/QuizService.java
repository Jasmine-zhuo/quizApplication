package com.bfs.logindemo.service;

import com.bfs.logindemo.dao.QuestionDao;
import com.bfs.logindemo.dao.QuizDao;
import com.bfs.logindemo.domain.Category;
import com.bfs.logindemo.domain.Quiz;
import com.bfs.logindemo.domain.Question;
import com.bfs.logindemo.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import java.util.Optional;

import java.util.Collections;
import java.util.List;

@Service
public class QuizService {
    private final QuizDao quizDao;
    private final QuestionDao questionDao;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public QuizService(QuestionDao questionDao, QuizDao quizDao, JdbcTemplate jdbcTemplate) {
        this.questionDao = questionDao;
        this.quizDao = quizDao;
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Question> getRandomQuestionsByCategory(int categoryId) {
        List<Question> questions = questionDao.findByCategoryId(categoryId);
        Collections.shuffle(questions);
        List<Question> randomQuestions = questions.subList(0, Math.min(questions.size(), 5));

        for (Question question : randomQuestions) {
            question.setChoices(questionDao.getChoicesByQuestionId(question.getQuestionId()));
        }

        return randomQuestions;
    }

    public List<Question> getQuestionsByCategoryId(int categoryId) {
        List<Question> questions = questionDao.findByCategoryId(categoryId);
        for (Question question : questions) {
            question.setChoices(questionDao.getChoicesByQuestionId(question.getQuestionId()));
        }
        return questions;
    }

    public Optional<Choice> getChoiceById(int selectedChoiceId) {
        return questionDao.getChoiceById(selectedChoiceId);
    }

    public String checkAnswer(Choice selectedChoice) {
        return selectedChoice.isCorrect() ? "Correct!" : "Incorrect";
    }

//    public int saveQuizResult(Object user, List<Integer> selectedChoiceIds) {
//        User loggedInUser = (User) user;
//        return quizDao.saveQuizResult(loggedInUser, selectedChoiceIds);
//    }
    public int startQuiz(int userId, int categoryId) {
        return quizDao.createQuiz(userId, categoryId, "Quiz Started");
    }
    public void saveQuizResultAndSelectedChoice(int quizId, List<Question> questions, List<Integer> selectedChoiceIds, String quizResult) {
        quizDao.saveQuizResultAndSelectedChoice(quizId, questions, selectedChoiceIds, quizResult);
    }

    public List<Quiz> getQuizzesByUserId(int userId) {
        return quizDao.findByUserId(userId);
    }

    public Quiz getQuizById(int id) {
        return quizDao.findById(id);
    }

    public void saveQuiz(Quiz quiz) {
        quizDao.save(quiz);
    }

    public void updateQuiz(Quiz quiz) {
        quizDao.update(quiz);
    }

    public void deleteQuiz(int id) {
        quizDao.delete(id);
    }
    public List<Category> getAllCategories() {
        return questionDao.findAllCategories();
    }

    public List<Quiz> getRecentQuizResults(int userId) {
        return quizDao.findRecentQuizzesByUserId(userId);
    }

    public List<Quiz> getAllQuizzes() {
        return quizDao.findAll();
    }

    public void updateQuizEndTime(int quizId) {
        quizDao.updateQuizEndTime(quizId);
    }
    public List<Question> getQuestionsByQuizId(int quizId) {
        return quizDao.findQuestionsByQuizId(quizId);
    }

    public List<Integer> getSelectedChoicesByQuizId(int quizId) {
        return quizDao.findSelectedChoicesByQuizId(quizId);
    }

    public List<Choice> getChoicesByQuestionId(int questionId) {
        return questionDao.getChoicesByQuestionId(questionId);
    }
}
