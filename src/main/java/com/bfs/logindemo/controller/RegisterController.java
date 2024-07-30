package com.bfs.logindemo.controller;

import com.bfs.logindemo.domain.User;
import com.bfs.logindemo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegisterController {
    private final UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String getRegisterPage(Model model) {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String email,
                               @RequestParam String password,
                               @RequestParam String firstname,
                               @RequestParam String lastname,
                               Model model) {

        // Check if the user already exists
        if (userService.getUserByEmail(email).isPresent()) {
            model.addAttribute("error", "Email already registered!");
            return "register";
        }

        // Save the new user
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setActive(true);
        user.setAdmin(false);

        userService.saveUser(user);

        return "redirect:/login";
    }
}
