package com.bfs.logindemo.service;

import com.bfs.logindemo.dao.CategoryDao;
import com.bfs.logindemo.dao.QuestionDao;
import com.bfs.logindemo.domain.Choice;
import com.bfs.logindemo.domain.Question;
import com.bfs.logindemo.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bfs.logindemo.dao.QuestionWithCategoryDTO;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {
    private final QuestionDao questionDao;
    private final CategoryDao categoryDao;

    @Autowired
    public QuestionService(QuestionDao questionDao, CategoryDao categoryDao) {
        this.questionDao = questionDao;
        this.categoryDao = categoryDao;
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
    public List<Question> getAllQuestions() {
        return questionDao.getAllQuestions();
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

    public List<QuestionWithCategoryDTO> getAllQuestionsWithCategoryName() {
        return questionDao.findAllQuestionsWithCategoryName();
    }
    public void toggleQuestionStatus(int questionId) {
        questionDao.toggleQuestionStatus(questionId);
    }
    public List<Choice> getChoicesByQuestionId(int questionId) {
        return questionDao.findChoicesByQuestionId(questionId);
    }

    public void updateQuestion(int questionId, String description, List<Integer> choiceIds, List<String> choiceDescriptions, int correctChoiceId) {
        questionDao.updateQuestionDescription(questionId, description);
        for (int i = 0; i < choiceIds.size(); i++) {
            Choice choice = new Choice();
            choice.setChoiceId(choiceIds.get(i));
            choice.setDescription(choiceDescriptions.get(i));
            choice.setCorrect(choice.getChoiceId() == correctChoiceId);
            questionDao.updateChoice(choice);
        }
    }
    public void addNewQuestion(int categoryId, String description, List<String> choiceDescriptions, int correctChoiceIndex) {
        int questionId = questionDao.addQuestion(categoryId, description);
        for (int i = 0; i < choiceDescriptions.size(); i++) {
            boolean isCorrect = i == correctChoiceIndex;
            questionDao.addChoice(questionId, choiceDescriptions.get(i), isCorrect);
        }
    }

    public List<Category> getAllCategories() {
        return categoryDao.findAll();
    }

}
