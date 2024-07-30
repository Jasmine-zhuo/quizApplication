package com.bfs.logindemo.service;

import com.bfs.logindemo.dao.QuestionDao;
import com.bfs.logindemo.domain.Choice;
import com.bfs.logindemo.domain.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {
    private final QuestionDao questionDao;

    @Autowired
    public QuestionService(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    public Question getQuestion() {
        return questionDao.getQuestion();
    }

    public String checkAnswer(Choice selectedChoice) {
//        Question question = questionDao.findById(selectedChoice.getQuestionId());
//
//        // Find the correct choice for the question
//        Optional<Choice> correctChoice = question.getChoices().stream()
//                .filter(Choice::isCorrect)
//                .findFirst();
//
//        // Compare the selected choice with the correct choice
//        if (correctChoice.isPresent() && selectedChoice.getChoiceId() == correctChoice.get().getChoiceId()) {
//            return "Correct!";
//        } else {
//            return "Incorrect";
//        }
        return selectedChoice.isCorrect() ? "Correct!" : "Incorrect";
    }

    public Optional<Choice> getChoiceById(Integer selectedChoiceId) {
        return questionDao.getChoiceById(selectedChoiceId);
    }

    public List<Question> getQuestionsByCategoryId(int categoryId) {
        List<Question> questions = questionDao.findByCategoryId(categoryId);
        // Debug statements
        for (Question question : questions) {
            System.out.println("Question: " + question.getDescription());
            for (Choice choice : question.getChoices()) {
                System.out.println("Choice: " + choice.getDescription());
            }
        }
        return questions;
    }

    public Question getQuestionById(int id) {
        return questionDao.findById(id);
    }

    public void saveQuestion(Question question) {
        questionDao.save(question);
    }

    public void updateQuestion(Question question) {
        questionDao.update(question);
    }

    public void deleteQuestion(int id) {
        questionDao.delete(id);
    }

}
