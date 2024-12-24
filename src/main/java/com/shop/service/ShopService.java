package com.shop.service;

import com.shop.dao.ProductDao;
import com.shop.dao.OrderDao;
import com.shop.dao.UserDao;
import com.shop.dao.CategoryDao;
import com.shop.model.Product;
import com.shop.model.Order;
import com.shop.model.User;
import com.shop.model.Category;
import java.util.List;

public class ShopService {
    private ProductDao productDao = new ProductDao();
    private OrderDao orderDao = new OrderDao();
    private UserDao userDao = new UserDao();
    private CategoryDao categoryDao = new CategoryDao();
    
    // 商品相关
    public List<Product> getAllProducts(Long shopId) throws Exception {
        return productDao.findByShopId(shopId);
    }
    
    public boolean addProduct(Product product) throws Exception {
        return productDao.save(product);
    }
    
    public boolean updateProduct(Product product) throws Exception {
        return productDao.update(product);
    }
    
    public boolean deleteProduct(Long productId, Long shopId) throws Exception {
        return productDao.delete(productId, shopId);
    }
    
    public int getProductCount(Long shopId) throws Exception {
        return productDao.countByShopId(shopId);
    }
    
    // 订单相关
    public List<Order> getAllOrders(Long shopId) throws Exception {
        return orderDao.findByShopId(shopId);
    }
    
    public List<Order> getRecentOrders(Long shopId) throws Exception {
        return orderDao.findRecentByShopId(shopId, 5); // 获取最近5个订单
    }
    
    public boolean updateOrderStatus(Long orderId, Long shopId, Integer status) throws Exception {
        // 验证订单是否属于该店铺
        Order order = orderDao.findById(orderId);
        if (order == null || !order.getShopId().equals(shopId)) {
            return false;
        }
        
        // 验证状态转换是否合法
        if (!isValidStatusTransition(order.getStatus(), status)) {
            return false;
        }
        
        return orderDao.updateStatus(orderId, shopId, status);
    }
    
    private boolean isValidStatusTransition(int currentStatus, int newStatus) {
        // 只允许以下状态转换：
        // 2(待发货) -> 3(待收货)
        // 3(待收货) -> 4(已完成)
        // 1(待付款) -> 5(已取消)
        switch (currentStatus) {
            case 2: return newStatus == 3;
            case 3: return newStatus == 4;
            case 1: return newStatus == 5;
            default: return false;
        }
    }
    
    public int getOrderCount(Long shopId) throws Exception {
        return orderDao.countByShopId(shopId);
    }
    
    // 店铺信息相关
    public User getShopInfo(Long shopId) throws Exception {
        return userDao.findById(shopId);
    }
    
    public boolean updateShopInfo(User shop) throws Exception {
        return userDao.update(shop);
    }
    
    public User shopLogin(String username, String password) throws Exception {
        User user = userDao.findByUsername(username);
        if (user != null && user.getUserType() == 2 && password.equals(user.getPassword())) {
            return user;
        }
        return null;
    }
    
    public List<Product> getProductsByPage(Long shopId, int page, int pageSize) throws Exception {
        return productDao.findByShopIdWithPaging(shopId, page, pageSize);
    }
    
    public List<Category> getAllCategories() throws Exception {
        return categoryDao.findAll();
    }
    
    public Product getProduct(Long productId, Long shopId) throws Exception {
        return productDao.findByIdAndShopId(productId, shopId);
    }
} 