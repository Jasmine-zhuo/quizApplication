package com.bfs.logindemo.service;

import com.bfs.logindemo.dao.UserDao;
import com.bfs.logindemo.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserDao userDao;
//    private final PasswordEncoder passwordEncoder;
    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }
//    @Autowired
//    public UserService(UserDao userDao, PasswordEncoder passwordEncoder) {
//        this.userDao = userDao;
//        this.passwordEncoder = passwordEncoder;
//    }


    @Transactional
    public void createNewUser(User user) {
        userDao.saveHibernate(user);
    }

    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userDao.findAllHibernate();
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserByEmail(String email) {
        try{
            return Optional.ofNullable(userDao.findUserByEmailHibernate(email));
        }catch (Exception e){
            System.err.println("Error fetching user by email: " + e.getMessage());
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Transactional(readOnly = true)
    public Optional<Optional<User>> getUserById(int id) {
        return Optional.ofNullable(userDao.findByIdHibernate(id));
    }

    @Transactional
    public void saveUser(User user) {
        //System.out.println("Saving user: " + user);
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.saveHibernate(user);
        //System.out.println("User saved successfully.");
    }

    @Transactional
    public void updateUser(User user) {
        userDao.updateHibernate(user);
    }

    @Transactional
    public void deleteUser(int id) {
        userDao.deleteHibernate(id);
    }

    @Transactional
    public boolean authenticateUser(String email, String password) {
//        Optional<User> userOptional = Optional.ofNullable(userDao.findUserByEmailHibernate(email));
//        return userOptional.isPresent() && passwordEncoder.matches(password, userOptional.get().getPassword());
        Optional<User> userOptional = Optional.ofNullable(userDao.findUserByEmailHibernate(email));
        return userOptional.isPresent() && userOptional.get().getPassword().equals(password);
    }

    @Transactional(readOnly = true)
    public boolean validateLogin(String email, String password) {
//        Optional<User> userOptional = getUserByEmail(email);
//        return userOptional.isPresent() && passwordEncoder.matches(password, userOptional.get().getPassword());
        Optional<User> user = getUserByEmail(email);
        return user.isPresent() && user.get().getPassword().equals(password);
    }
    @Transactional(readOnly = true)
    public boolean validateAdminLogin(String email, String password) {
        Optional<User> userOpt = userDao.findByEmailAndPasswordAndIsAdminHibernate(email, password, true);
        return userOpt.isPresent();
//        Optional<User> userOpt = userDao.findByEmailAndIsAdminHibernate(email, true);
//        return userOpt.isPresent() && passwordEncoder.matches(password, userOpt.get().getPassword());
    }
    @Transactional
    public void toggleUserStatus(int userId) {
       userDao.toggleUserStatusHibernate(userId);
    }
}
