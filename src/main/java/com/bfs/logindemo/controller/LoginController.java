package com.bfs.logindemo.controller;

import com.bfs.logindemo.domain.Question;
import com.bfs.logindemo.domain.User;
import com.bfs.logindemo.service.LoginService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpSession;//incompatibility error
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

@Controller
public class LoginController {
    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping("/login")
    public String getLogin(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);

        // redirect to /home if user is already logged in
        //comment for testing
        if (session != null && session.getAttribute("user") != null) {
            return "redirect:/home";
        }

        return "login";
    }
    @GetMapping("/admin/login")
    public String getAdminLogin(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);

        // redirect to /admin/home if admin is already logged in
        //comment for testing
        if (session != null && session.getAttribute("user") != null) {
            return "redirect:/admin/home";
        }

        return "admin-login"; // Return the admin login view
    }


    @PostMapping("/login")
    public String postLogin(@RequestParam String email,
                            @RequestParam String password,
                            HttpServletRequest request, Model model) {
        User user = loginService.userService().getUserByEmail(email).orElse(null);


        if(user != null) {
            if (!user.isActive()) {
                model.addAttribute("error", "Your account is blocked. Please contact support.");
                return "login";
            }
            boolean isValidPassword = loginService.validateLogin(email, password);
            if(isValidPassword) {
                HttpSession oldSession = request.getSession(false);
                // invalidate old session if it exists
                if (oldSession != null) oldSession.invalidate();

                // generate new session
                HttpSession newSession = request.getSession(true);

                // store user details in session
                newSession.setAttribute("user", user);

                return "redirect:/home";
            }
        }
        // If user details are invalid or user is not found
        model.addAttribute("error", "Invalid email or password");
        return "login";
    }

    @PostMapping("/admin/login")
    public String postAdminLogin(@RequestParam String email,
                                 @RequestParam String password,
                                 HttpServletRequest request, Model model) {
        User user = loginService.userService().getUserByEmail(email).orElse(null);
        if (user != null) {
            // Check if the user is active
            if (!user.isActive()) {
                model.addAttribute("error", "Your account is blocked. Please contact support.");
                return "admin-login";
            }

            // Validate the password and check if the user is an admin
            boolean isValidAdmin = loginService.validateAdminLogin(email, password);

            if (isValidAdmin) {
                HttpSession oldSession = request.getSession(false);
                if (oldSession != null) oldSession.invalidate();
                HttpSession newSession = request.getSession(true);

                // Store user details in session
                newSession.setAttribute("user", user);
                newSession.setAttribute("isAdmin", true);

                return "redirect:/admin/home";
            }
        }

        model.addAttribute("error", "Invalid admin credentials");
        return "admin-login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, Model model) {
        HttpSession oldSession = request.getSession(false);
        // invalidate old session if it exists
        if(oldSession != null) oldSession.invalidate();
        return "redirect:/login";
    }


}
