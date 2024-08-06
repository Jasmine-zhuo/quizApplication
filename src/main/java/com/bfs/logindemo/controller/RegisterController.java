package com.bfs.logindemo.controller;

import ch.qos.logback.core.pattern.color.BoldCyanCompositeConverter;
import com.bfs.logindemo.domain.User;
import com.bfs.logindemo.service.UserService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegisterController {
    private final UserService userService;
//    private BCryptPasswordEncoder passwordEncoder;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }
//    public RegisterController(UserService userService, BCryptPasswordEncoder passwordEncoder) {
//        this.userService = userService;
//        this.passwordEncoder = passwordEncoder;
//    }

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
        System.out.println("Checking if user with email exists: " + email);
        if (userService.getUserByEmail(email).isPresent()) {
            model.addAttribute("error", "Email already registered!");
            return "register";
        }
        System.out.println("Saving new user...");
//        String hashedPassword = passwordEncoder.encode(password);

        // Save the new user
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setActive(true);
        user.setAdmin(false);

        userService.saveUser(user);
        System.out.println("User saved: " + user);
        return "redirect:/login";
    }
}
