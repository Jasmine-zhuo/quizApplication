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

        // redirect to /quiz if user is already logged in
        if (session != null && session.getAttribute("user") != null) {
            return "redirect:/home";
        }

        return "login";
    }

    // validate that we are always getting a new session after login
    @PostMapping("/login")
    public String postLogin(@RequestParam String email,
                            @RequestParam String password,
                            HttpServletRequest request) {
        boolean isValidUser = loginService.validateLogin(email, password);

        if(isValidUser) {
            User user = loginService.userService().getUserByEmail(email).get();
            HttpSession oldSession = request.getSession(false);
            // invalidate old session if it exists
            if (oldSession != null) oldSession.invalidate();

            // generate new session
            HttpSession newSession = request.getSession(true);

            // store user details in session
            newSession.setAttribute("user", user);

            return "redirect:/home";
        } else { // if user details are invalid
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, Model model) {
        HttpSession oldSession = request.getSession(false);
        // invalidate old session if it exists
        if(oldSession != null) oldSession.invalidate();
        return "login";
    }

    // If there are something wrong, will be redirected to "/error"
}
