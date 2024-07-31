package com.bfs.logindemo.service;

import com.bfs.logindemo.dao.UserDao;
import com.bfs.logindemo.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService {
    private final UserService userService;

    @Autowired
    public LoginService(UserService userService) {this.userService = userService; }

    public boolean validateLogin(String username, String password) {
        return userService.validateLogin(username, password);
    }
    public boolean validateAdminLogin(String email, String password) {
        return userService.validateAdminLogin(email, password);
    }
    public UserService userService() {
        return userService;
    }
}
