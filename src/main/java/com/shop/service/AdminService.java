package com.shop.service;

import com.shop.dao.UserDao;
import com.shop.dao.CategoryDao;
import com.shop.dao.ProductDao;
import com.shop.dao.OrderDao;
import com.shop.model.User;
import com.shop.model.Category;
import com.shop.model.Product;
import com.shop.model.Order;
import com.shop.util.PasswordUtil;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class AdminService {
    private UserDao userDao = new UserDao();
    private CategoryDao categoryDao = new CategoryDao();
    private ProductDao productDao = new ProductDao();
    private OrderDao orderDao = new OrderDao();
    
    public int getUserCount() throws Exception {
        return userDao.getUserCount();
    }
    
    public int getShopCount() throws Exception {
        return userDao.getShopCount();
    }
    
    public List<User> getAllCustomers() throws Exception {
        return userDao.findByUserType(3);
    }
    
    public List<User> getAllShops() throws Exception {
        return userDao.findByUserType(2);
    }
    
    public void updateUserStatus(Long userId, boolean status) throws Exception {
        userDao.updateStatus(userId, status);
    }
    
    public void updateShopStatus(Long shopId, boolean status) throws Exception {
        userDao.updateStatus(shopId, status);
    }
    
    public List<Category> getAllCategories() throws Exception {
        return categoryDao.findAll();
    }
    
    public boolean addCategory(Category category) throws Exception {
        return categoryDao.save(category);
    }
    
    public boolean updateCategory(Category category) throws Exception {
        return categoryDao.update(category);
    }
    
    public boolean deleteCategory(Long id) throws Exception {
        return categoryDao.delete(id);
    }
    
    public User adminLogin(String username, String password) throws Exception {
        User user = userDao.findByUsername(username);
        System.out.println("Login attempt - Username: " + username);
        System.out.println("User found: " + (user != null));
        if (user != null) {
            System.out.println("User type: " + user.getUserType());
            System.out.println("Password match: " + PasswordUtil.verify(password, user.getPassword()));
        }
        
        if (user != null && user.getUserType() == 1 && password.equals(user.getPassword())) {
            return user;
        }
        return null;
    }
    
    public List<Product> getAllProducts() throws Exception {
        return productDao.findAll();
    }
    
    public List<Order> getAllOrders() throws Exception {
        return orderDao.findAll();
    }
    
    public Map<String, Object> getStatistics() throws Exception {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalUsers", userDao.getUserCount());
        stats.put("totalShops", userDao.getShopCount());
        stats.put("totalOrders", orderDao.getTotalCount());
        stats.put("totalSales", orderDao.getTotalSales());
        return stats;
    }
    
    public boolean updateProductStatus(Long productId, boolean status) throws Exception {
        return productDao.updateStatus(productId, status);
    }
    
    public boolean deleteProduct(Long productId) throws Exception {
        return productDao.delete(productId);
    }
} 