package com.shop.service;

import com.shop.dao.UserDao;
import com.shop.model.User;

public class UserService {
    private UserDao userDao = new UserDao();
    
    public User login(String username, String password) throws Exception {
        User user = userDao.findByUsername(username);
        if (user != null && password.equals(user.getPassword())) {
            return user;
        }
        return null;
    }
    
    public boolean register(User user) throws Exception {
        // 检查用户名是否已存在
        if (userDao.findByUsername(user.getUsername()) != null) {
            return false;
        }
        return userDao.save(user);
    }
} 