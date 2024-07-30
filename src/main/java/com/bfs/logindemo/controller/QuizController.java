package com.bfs.logindemo.controller;

import com.bfs.logindemo.domain.Choice;
import com.bfs.logindemo.domain.Question;
import com.bfs.logindemo.domain.Quiz;
import com.bfs.logindemo.domain.User;
import com.bfs.logindemo.service.QuestionService;
import com.bfs.logindemo.service.QuizService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.*;

//import javax.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSession;
import java.util.Optional;

@Controller
public class QuizController {

    private final QuizService quizService;
    private final QuestionService questionService;

    @Autowired
    public QuizController(QuizService quizService, QuestionService questionService) {
        this.quizService = quizService;
        this.questionService = questionService;
    }

    @GetMapping("/quiz")
    public String getQuiz(@RequestParam("categoryId") int categoryId, HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        //if user not logged in, redirect to login page
        if(user == null){
            return "redirect:/login";
        }
        int quizId = quizService.startQuiz(user.getUserId(), categoryId);
        session.setAttribute("quizId", quizId);

        List<Question> questions = quizService.getRandomQuestionsByCategory(categoryId);
        session.setAttribute("questions", questions);
        model.addAttribute("questions", questions);

        //debug statement
//        System.out.println("Fetched Questions:");
//        for (Question question : questions) {
//            System.out.println("Question: " + question.getDescription());
//            for (Choice choice : question.getChoices()) {
//                System.out.println("Choice: " + choice.getDescription());
//            }
//        }
//        System.out.println("quiz page get....");
        return "quiz";
    }

    @PostMapping("/quiz")
    public String submitQuiz(HttpServletRequest request, HttpSession session, Model model) {
        List<Question> questions = (List<Question>) session.getAttribute("questions");
        User user = (User) session.getAttribute("user");
        Integer quizId = (Integer) session.getAttribute("quizId");

        if (questions == null || user == null || quizId == null) {
            return "redirect:/home";
        }

        List<Integer> selectedChoiceIds = new ArrayList<>();
        for (int i = 0; i < questions.size(); i++) {
            String paramName = "selectedChoiceIds_" + i;
            String selectedChoiceIdStr = request.getParameter(paramName);
            if (selectedChoiceIdStr != null) {
                selectedChoiceIds.add(Integer.parseInt(selectedChoiceIdStr));
            } else {
                selectedChoiceIds.add(-1); // Add a placeholder for missing selection
            }
        }

        List<String> results = new ArrayList<>();
        int correctCount = 0;

        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);
            int selectedChoiceId = selectedChoiceIds.get(i);
            Optional<Choice> selectedChoice = question.getChoices().stream()
                    .filter(choice -> choice.getChoiceId() == selectedChoiceId)
                    .findFirst();

            // Debug statement
//            System.out.println("Question: " + question.getDescription());
//            System.out.println("Selected Choice ID: " + selectedChoiceId);

            if (selectedChoice.isPresent()) {
                String result = questionService.checkAnswer(selectedChoice.get());
                results.add(result);
                // Debug statement
                System.out.println("Question: " + question.getDescription());
                System.out.println("Selected Choice: " + selectedChoice.get().getDescription());
                System.out.println("Result: " + result);
                if ("Correct!".equals(result)) {
                    correctCount++;
                }
            } else {
                results.add("No selection");
            }
//
        }
        // Determine if the user passed or failed
        String quizResult = correctCount >= 3 ? "Passed" : "Failed";
        System.out.println("Quiz Result: " + quizResult);
        // Save the quiz result to the database
        //int quizId = quizService.saveQuizResultAndSelectedChoice(user.getUserId(), questions.get(0).getCategoryId(), questions, selectedChoiceIds, quizResult);
        quizService.saveQuizResultAndSelectedChoice(quizId, questions, selectedChoiceIds, quizResult);
        quizService.updateQuizEndTime(quizId);

        Quiz quiz = quizService.getQuizById(quizId);
        model.addAttribute("results", results);
        model.addAttribute("quizResult", quizResult);
        model.addAttribute("user", user);
        model.addAttribute("questions", questions);
        model.addAttribute("quiz", quiz); // Adding the quiz object to the model
        model.addAttribute("selectedChoices", selectedChoiceIds); // Adding the selected choices to the model

        return "quiz-result";
    }


    @GetMapping("/quiz-result/{quizId}")
    public String getQuizResult(@PathVariable("quizId") int quizId, Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        Quiz quiz = quizService.getQuizById(quizId);
        if (quiz == null || quiz.getUserId() != user.getUserId()) {
            return "redirect:/home";
        }

        List<Question> questions = quizService.getQuestionsByQuizId(quizId);
        List<Integer> selectedChoiceIds = quizService.getSelectedChoicesByQuizId(quizId);
        // Ensure choices are set for each question
        for (Question question : questions) {
            List<Choice> choices = quizService.getChoicesByQuestionId(question.getQuestionId());
            question.setChoices(choices);
        }

        List<String> results = new ArrayList<>();
        int correctCount = 0;

        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);
            int selectedChoiceId = selectedChoiceIds.get(i);
            Optional<Choice> selectedChoice = question.getChoices().stream()
                    .filter(choice -> choice.getChoiceId() == selectedChoiceId)
                    .findFirst();

            if (selectedChoice.isPresent()) {
                String result = questionService.checkAnswer(selectedChoice.get());
                results.add(result);
                if ("Correct!".equals(result)) {
                    correctCount++;
                }
            } else {
                results.add("No selection");
            }
        }
        String quizResult = correctCount >= 3 ? "Passed" : "Failed";

        model.addAttribute("results", results);
        model.addAttribute("quizResult", quizResult);
        model.addAttribute("user", user);
        model.addAttribute("questions", questions);
        model.addAttribute("quiz", quiz); // Adding the quiz object to the model
        model.addAttribute("selectedChoices", selectedChoiceIds); // Adding the selected choices to the model

        return "quiz-result";
    }
}
