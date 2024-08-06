package com.bfs.logindemo.service;

import com.bfs.logindemo.dao.QuizQuestionDao;
import com.bfs.logindemo.domain.QuizQuestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Service
public class QuizQuestionService {
    private final QuizQuestionDao quizQuestionDao;

    @Autowired
    public QuizQuestionService(QuizQuestionDao quizQuestionDao) {
        this.quizQuestionDao = quizQuestionDao;
    }

    @Transactional(readOnly = true)
    public List<QuizQuestion> getQuizQuestionsByQuizId(int quizId) {
        return quizQuestionDao.findByQuizIdHibernate(quizId);
    }

    @Transactional
    public void saveQuizQuestion(QuizQuestion quizQuestion) {
        quizQuestionDao.saveHibernate(quizQuestion);
    }

    @Transactional
    public void updateQuizQuestion(QuizQuestion quizQuestion) {
        quizQuestionDao.updateHibernate(quizQuestion);
    }

    @Transactional
    public void deleteQuizQuestion(int id) {
        quizQuestionDao.deleteHibernate(id);
    }
}
