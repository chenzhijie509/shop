package com.shop.service;

import com.shop.dao.ProductDao;
import com.shop.dao.OrderDao;
import com.shop.dao.UserDao;
import com.shop.dao.ReviewDao;
import com.shop.model.Product;
import com.shop.model.Order;
import com.shop.model.User;
import com.shop.model.Review;
import java.util.List;
import java.util.Date;

public class CustomerService {
    private ProductDao productDao = new ProductDao();
    private OrderDao orderDao = new OrderDao();
    private UserDao userDao = new UserDao();
    private ReviewDao reviewDao = new ReviewDao();
    
    // 商品查询相关
    public List<Product> searchProducts(String keyword, Long categoryId) throws Exception {
        System.out.println("Searching products with keyword: " + keyword + ", categoryId: " + categoryId);
        List<Product> products = productDao.search(keyword, categoryId);
        System.out.println("Found " + products.size() + " products");
        return products;
    }
    
    public List<Product> getProductsByCategory(Long categoryId) throws Exception {
        System.out.println("Getting products for category: " + categoryId);
        List<Product> products = productDao.findByCategory(categoryId);
        System.out.println("Found " + products.size() + " products");
        return products;
    }
    
    // 订单相关
    public List<Order> getMyOrders(Long userId) throws Exception {
        return orderDao.findByUserId(userId);
    }
    
    public Order getOrderDetail(Long orderId, Long userId) throws Exception {
        return orderDao.findByIdAndUserId(orderId, userId);
    }
    
    public boolean cancelOrder(Long orderId, Long userId) throws Exception {
        return orderDao.updateStatus(orderId, userId, 5); // 5表示已取消
    }
    
    public boolean confirmReceive(Long orderId, Long userId) throws Exception {
        // 验证订单是否属于该用户
        Order order = orderDao.findByIdAndUserId(orderId, userId);
        if (order == null || order.getStatus() != 3) { // 3表示待收货状态
            return false;
        }
        
        // 更新订单状态为已完成(4)
        return orderDao.updateStatusByUser(orderId, userId, 4);
    }
    
    // 个人信息相关
    public User getCustomerInfo(Long userId) throws Exception {
        return userDao.findById(userId);
    }
    
    public boolean updateCustomerInfo(User user) throws Exception {
        return userDao.update(user);
    }
    
    public boolean submitReview(Long orderId, Long productId, Long userId, int rating, String content) 
            throws Exception {
        // 先检查订单状态
        Order order = orderDao.findByIdAndUserId(orderId, userId);
        if (order == null || order.getStatus() != 4 || order.isReview()) {
            return false;
        }
        
        // 创建评价
        Review review = new Review();
        review.setOrderId(orderId);
        review.setProductId(productId);
        review.setUserId(userId);
        review.setRating(rating);
        review.setContent(content);
        review.setCreatedAt(new Date());
        
        // 保存评价并更新订单评价状态
        if (reviewDao.save(review)) {
            return orderDao.updateReviewStatus(orderId, true);
        }
        return false;
    }
    
    public Product getProduct(Long productId) throws Exception {
        return productDao.findById(productId);
    }
} 