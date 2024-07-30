package com.bfs.logindemo.service;

import com.bfs.logindemo.dao.ChoiceDao;
import com.bfs.logindemo.domain.Choice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChoiceService {
    private final ChoiceDao choiceDao;

    @Autowired
    public ChoiceService(ChoiceDao choiceDao) {
        this.choiceDao = choiceDao;
    }

    public List<Choice> getChoicesByQuestionId(int questionId) {
        return choiceDao.findByQuestionId(questionId);
    }

    public void saveChoice(Choice choice) {
        choiceDao.save(choice);
    }

    public void updateChoice(Choice choice) {
        choiceDao.update(choice);
    }

    public void deleteChoice(int id) {
        choiceDao.delete(id);
    }
}
