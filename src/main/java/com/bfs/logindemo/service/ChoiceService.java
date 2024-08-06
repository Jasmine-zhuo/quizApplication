package com.bfs.logindemo.service;

import com.bfs.logindemo.dao.ChoiceDao;
import com.bfs.logindemo.domain.Choice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ChoiceService {
    private final ChoiceDao choiceDao;

    @Autowired
    public ChoiceService(ChoiceDao choiceDao) {
        this.choiceDao = choiceDao;
    }

    @Transactional(readOnly = true)
    public List<Choice> getChoicesByQuestionId(int questionId) {
        return choiceDao.findByQuestionIdHibernate(questionId);
    }

    @Transactional
    public void saveChoice(Choice choice) {
        choiceDao.saveHibernate(choice);
    }

    @Transactional
    public void updateChoice(Choice choice) {
        choiceDao.updateHibernate(choice);
    }

    @Transactional
    public void deleteChoice(int id) {
        choiceDao.deleteHibernate(id);
    }
}
