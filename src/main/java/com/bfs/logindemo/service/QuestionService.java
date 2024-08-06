package com.bfs.logindemo.service;

import com.bfs.logindemo.dao.CategoryDao;
import com.bfs.logindemo.dao.QuestionDao;
import com.bfs.logindemo.domain.Choice;
import com.bfs.logindemo.domain.Question;
import com.bfs.logindemo.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bfs.logindemo.dao.QuestionWithCategoryDTO;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional(readOnly = true)
    public Question getQuestion(int questionId) {
        return questionDao.getQuestionHibernate(questionId);
    }

    @Transactional(readOnly = true)
    public String checkAnswer(Choice selectedChoice) {
        return selectedChoice.isCorrect() ? "Correct!" : "Incorrect";
    }

    @Transactional(readOnly = true)
    public Optional<Choice> getChoiceById(Integer selectedChoiceId) {
        return questionDao.getChoiceByIdHibernate(selectedChoiceId);
    }

    @Transactional(readOnly = true)
    public List<Question> getQuestionsByCategoryId(int categoryId) {
        List<Question> questions = questionDao.findByCategoryIdHibernate(categoryId);
        // Debug statements
        for (Question question : questions) {
            System.out.println("Question: " + question.getDescription());
            for (Choice choice : question.getChoices()) {
                System.out.println("Choice: " + choice.getDescription());
            }
        }
        return questions;
    }

    @Transactional(readOnly = true)
    public List<Question> getAllQuestions() {
        return questionDao.findAllHibernate();
    }

    @Transactional(readOnly = true)
    public Question getQuestionById(int id) {
        return questionDao.findByIdHibernate(id);
    }

    @Transactional
    public void saveQuestion(Question question) {
        questionDao.saveHibernate(question);
    }

    @Transactional
    public void updateQuestion(Question question) {
        questionDao.updateHibernate(question);
    }

    @Transactional
    public void deleteQuestion(int id) {
        questionDao.deleteHibernate(id);
    }

    @Transactional(readOnly = true)
    public List<QuestionWithCategoryDTO> getAllQuestionsWithCategoryName() {
        return questionDao.findAllQuestionsWithCategoryNameHibernate();
    }

    @Transactional
    public void toggleQuestionStatus(int questionId) {
        questionDao.toggleQuestionStatusHibernate(questionId);
    }

    @Transactional(readOnly = true)
    public List<Choice> getChoicesByQuestionId(int questionId) {
        return questionDao.findChoicesByQuestionIdHibernate(questionId);
    }

    @Transactional
    public void updateQuestion(int questionId, String description, List<Integer> choiceIds, List<String> choiceDescriptions, int correctChoiceId) {
        Question question = questionDao.getQuestionHibernate(questionId);
        if(question == null){
            throw new IllegalArgumentException("Question not found for id: " + questionId);
        }
        questionDao.updateQuestionDescriptionHibernate(questionId, description);

        List<Choice> choices = question.getChoices();
        for (int i = 0; i < choices.size(); i++) {
            Choice choice = choices.get(i);
            choice.setDescription(choiceDescriptions.get(i));
            choice.setCorrect(choice.getChoiceId() == correctChoiceId);
            choice.setQuestion(question);  // Ensure the relationship is maintained
            questionDao.updateChoiceHibernate(choice);
        }
//        for (int i = 0; i < choiceIds.size(); i++) {
//            Choice choice = new Choice();
//            choice.setChoiceId(choiceIds.get(i));
//            choice.setDescription(choiceDescriptions.get(i));
//            choice.setCorrect(choice.getChoiceId() == correctChoiceId);
//            questionDao.updateChoiceHibernate(choice);
//        }
        questionDao.updateHibernate(question);
    }

    @Transactional
    public void addNewQuestion(int categoryId, String description, List<String> choiceDescriptions, int correctChoiceIndex) {
        Category category = categoryDao.findByIdHibernate(categoryId);
        if (category == null) {
            throw new IllegalArgumentException("Category not found for id: " + categoryId);
        }

        int questionId = questionDao.addQuestionHibernate(categoryId, description);
        for (int i = 0; i < choiceDescriptions.size(); i++) {
            boolean isCorrect = i == correctChoiceIndex;
            questionDao.addChoiceHibernate(questionId, choiceDescriptions.get(i), isCorrect);
        }
    }

    @Transactional(readOnly = true)
    public List<Category> getAllCategories() {
        return categoryDao.findAllHibernate();
    }

}
