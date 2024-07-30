package com.bfs.logindemo.service;

import com.bfs.logindemo.dao.QuizQuestionDao;
import com.bfs.logindemo.domain.QuizQuestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizQuestionService {
    private final QuizQuestionDao quizQuestionDao;

    @Autowired
    public QuizQuestionService(QuizQuestionDao quizQuestionDao) {
        this.quizQuestionDao = quizQuestionDao;
    }

    public List<QuizQuestion> getQuizQuestionsByQuizId(int quizId) {
        return quizQuestionDao.findByQuizId(quizId);
    }

    public void saveQuizQuestion(QuizQuestion quizQuestion) {
        quizQuestionDao.save(quizQuestion);
    }

    public void updateQuizQuestion(QuizQuestion quizQuestion) {
        quizQuestionDao.update(quizQuestion);
    }

    public void deleteQuizQuestion(int id) {
        quizQuestionDao.delete(id);
    }
}
