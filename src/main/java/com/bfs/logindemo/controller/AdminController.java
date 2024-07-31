package com.bfs.logindemo.controller;

import com.bfs.logindemo.dao.QuestionWithCategoryDTO;
import com.bfs.logindemo.dao.QuizWithDetailsDTO;
import com.bfs.logindemo.domain.Contact;
import com.bfs.logindemo.domain.Question;
import com.bfs.logindemo.domain.Quiz;
import com.bfs.logindemo.domain.User;
import com.bfs.logindemo.service.ContactService;
import com.bfs.logindemo.service.QuestionService;
import com.bfs.logindemo.service.QuizService;
import com.bfs.logindemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final QuizService quizService;
    private final QuestionService questionService;
    private final ContactService contactService;

    @Autowired
    public AdminController(UserService userService, QuizService quizService, QuestionService questionService, ContactService contactService) {
        this.userService = userService;
        this.quizService = quizService;
        this.questionService = questionService;
        this.contactService = contactService;

    }

    @GetMapping("/home")
    public String getAdminHome() {
        System.out.println("Navigating to admin home");
        return "admin-home";
    }

    @GetMapping("/users")
    public String getUserManagementPage(Model model) {
        System.out.println("Navigating to user management page");
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "user-management";
    }

    @PostMapping("/users/toggleStatus")
    @ResponseBody
    public String toggleUserStatus(@RequestParam int userId) {
        System.out.println("Toggling user status for user ID: " + userId);
        userService.toggleUserStatus(userId);
        return "redirect:/admin/users";
    }

    @GetMapping("/quizzes")
    public String getQuizResultManagementPage(
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) Integer userId,
            Model model) {

        //List<QuizWithDetailsDTO> quizzes = quizService.getAllQuizzesWithUserDetails();
        List<QuizWithDetailsDTO> quizzes = quizService.getQuizzesWithFilters(categoryId, userId);
        model.addAttribute("quizResults", quizzes);

        model.addAttribute("categories", quizService.getAllCategories());
        model.addAttribute("users", userService.getAllUsers());
        return "quiz-result-management";
    }

    @GetMapping("/questions")
    public String getQuestionManagementPage(Model model) {
        List<QuestionWithCategoryDTO> questions = questionService.getAllQuestionsWithCategoryName();
        model.addAttribute("questions", questions);
        return "question-management";
    }

    @PostMapping("/questions/toggleStatus")
    public String toggleQuestionStatus(@RequestParam int questionId) {
        System.out.println("Toggling question status for question ID: " + questionId);
        questionService.toggleQuestionStatus(questionId);
        return "redirect:/admin/questions";
    }
    @GetMapping("/contacts")
    public String getContactUsManagementPage(Model model) {
        System.out.println("Navigating to contact us management page");
        List<Contact> contacts = contactService.getAllContacts();
        model.addAttribute("contacts", contacts);
        return "contact-management";
    }

}
