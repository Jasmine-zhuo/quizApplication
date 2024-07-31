package com.bfs.logindemo.service;

import com.bfs.logindemo.dao.UserDao;
import com.bfs.logindemo.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserDao userDao;

    @Autowired
    public UserService(UserDao userDao) { this.userDao = userDao; }

    public void createNewUser(User user) {
        userDao.save(user);
    }

    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    public Optional<User> getUserByEmail(String email) {
        try{
            return Optional.ofNullable(userDao.getUserByEmail(email));
        }catch (Exception e){
            return Optional.empty();
        }
    }
    public Optional<Optional<User>> getUserById(int id) {
        return Optional.ofNullable(userDao.findById(id));
    }

    public void saveUser(User user) {
        userDao.save(user);
    }

    public void updateUser(User user) {
        userDao.update(user);
    }

    public void deleteUser(int id) {
        userDao.delete(id);
    }

    public boolean authenticateUser(String email, String password) {
        Optional<User> userOptional = Optional.ofNullable(userDao.getUserByEmail(email));
        return userOptional.isPresent() && userOptional.get().getPassword().equals(password);
    }


    public boolean validateLogin(String email, String password) {
        Optional<User> user = getUserByEmail(email);
        return user.isPresent() && user.get().getPassword().equals(password);
    }

    public boolean validateAdminLogin(String email, String password) {
        Optional<User> userOpt = userDao.findByEmailAndPasswordAndIsAdmin(email, password, true);
        return userOpt.isPresent();
    }

    public void toggleUserStatus(int userId) {
       userDao.toggleUserStatus(userId);
    }
}
