package com.bfs.logindemo.controller;

import com.bfs.logindemo.domain.Category;
import com.bfs.logindemo.domain.Quiz;
import com.bfs.logindemo.domain.User;
import com.bfs.logindemo.service.QuizService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    private final QuizService quizService;

    @Autowired
    public HomeController(QuizService quizService) {
        this.quizService = quizService;
    }
    @GetMapping("/home")
    public String home(HttpSession session, Model model){
        User user = (User)session.getAttribute("user");

        // If user is not logged in, redirect to login page
        if (user == null) {
            return "redirect:/login";
        }
        // Fetch quiz categories
        List<Category> categories = quizService.getAllCategories();

        // Fetch recent quiz results
        List<Quiz> recentQuizzes = quizService.getRecentQuizResults(user.getUserId());

        // Add data to the model
        model.addAttribute("user", user);
        model.addAttribute("categories", categories);
        model.addAttribute("recentQuizzes", recentQuizzes);

        return "home";
    }
}
