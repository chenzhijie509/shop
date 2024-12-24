package com.shop.controller;

import com.shop.model.Product;
import com.shop.model.Order;
import com.shop.model.User;
import com.shop.service.CustomerService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/customer/*")
public class CustomerServlet extends HttpServlet {
    private CustomerService customerService = new CustomerService();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getPathInfo();
        if (action == null) {
            action = "/";
        }
        
        User user = (User) request.getSession().getAttribute("user");
        if (user == null || user.getUserType() != 3) {
            response.sendRedirect(request.getContextPath() + "/user/login");
            return;
        }
        
        try {
            switch (action) {
                case "/":
                case "/products":
                    listProducts(request, response);
                    break;
                case "/orders":
                    listOrders(request, response, user.getId());
                    break;
                case "/review":
                    showReviewPage(request, response, user.getId());
                    break;
                case "/profile":
                    showProfile(request, response, user.getId());
                    break;
                default:
                    response.sendRedirect(request.getContextPath() + "/customer/products");
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getPathInfo();
        User user = (User) request.getSession().getAttribute("user");
        if (user == null || user.getUserType() != 3) {
            response.sendRedirect(request.getContextPath() + "/user/login");
            return;
        }

        try {
            switch (action) {
                case "/order/confirm":
                    confirmOrder(request, response, user.getId());
                    break;
                case "/review/submit":
                    submitReview(request, response, user.getId());
                    break;
                case "/profile/update":
                    updateProfile(request, response, user.getId());
                    break;
                default:
                    response.sendRedirect(request.getContextPath() + "/customer/orders");
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
    
    private void listProducts(HttpServletRequest request, HttpServletResponse response) 
            throws Exception {
        try {
            String keyword = request.getParameter("keyword");
            String categoryId = request.getParameter("categoryId");
            
            List<Product> products;
            if (keyword != null && !keyword.trim().isEmpty()) {
                Long categoryIdLong = null;
                if (categoryId != null && !categoryId.trim().isEmpty()) {
                    categoryIdLong = Long.parseLong(categoryId);
                }
                products = customerService.searchProducts(keyword, categoryIdLong);
            } else if (categoryId != null && !categoryId.trim().isEmpty()) {
                products = customerService.getProductsByCategory(Long.parseLong(categoryId));
            } else {
                products = customerService.searchProducts(null, null);
            }
            
            // 添加调试信息
            System.out.println("Found " + products.size() + " products");
            for (Product p : products) {
                System.out.println("Product: " + p.getName() + ", Image: " + p.getImage());
            }
            
            request.setAttribute("products", products);
            request.getRequestDispatcher("/WEB-INF/jsp/customer/products.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "获取商品列表失败：" + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/jsp/customer/products.jsp").forward(request, response);
        }
    }
    
    private void listOrders(HttpServletRequest request, HttpServletResponse response, Long userId) 
            throws Exception {
        List<Order> orders = customerService.getMyOrders(userId);
        request.setAttribute("orders", orders);
        request.getRequestDispatcher("/WEB-INF/jsp/customer/orders.jsp").forward(request, response);
    }
    
    private void showProfile(HttpServletRequest request, HttpServletResponse response, Long userId) 
            throws Exception {
        User customer = customerService.getCustomerInfo(userId);
        request.setAttribute("customer", customer);
        request.getRequestDispatcher("/WEB-INF/jsp/customer/profile.jsp").forward(request, response);
    }
    
    private void cancelOrder(HttpServletRequest request, HttpServletResponse response, Long userId) 
            throws Exception {
        Long orderId = Long.parseLong(request.getParameter("id"));
        if (customerService.cancelOrder(orderId, userId)) {
            request.setAttribute("success", "订单取消成功");
        } else {
            request.setAttribute("error", "订单取消失败");
        }
        response.sendRedirect(request.getContextPath() + "/customer/orders");
    }
    
    private void confirmOrder(HttpServletRequest request, HttpServletResponse response, Long userId) 
            throws Exception {
        Long orderId = Long.parseLong(request.getParameter("id"));
        if (customerService.confirmReceive(orderId, userId)) {
            request.setAttribute("success", "确认收货成功");
        } else {
            request.setAttribute("error", "确认收货失败");
        }
        response.sendRedirect(request.getContextPath() + "/customer/orders");
    }
    
    private void updateProfile(HttpServletRequest request, HttpServletResponse response, Long userId) 
            throws Exception {
        User customer = new User();
        customer.setId(userId);
        customer.setEmail(request.getParameter("email"));
        customer.setRealName(request.getParameter("realName"));
        customer.setPhone(request.getParameter("phone"));
        customer.setAddress(request.getParameter("address"));
        
        if (customerService.updateCustomerInfo(customer)) {
            request.setAttribute("success", "个人信息更新成功");
        } else {
            request.setAttribute("error", "个人信息更新失败");
        }
        
        showProfile(request, response, userId);
    }
    
    private void showReviewPage(HttpServletRequest request, HttpServletResponse response, Long userId) 
            throws ServletException, IOException {
        try {
            Long orderId = Long.parseLong(request.getParameter("orderId"));
            Order order = customerService.getOrderDetail(orderId, userId);
            if (order == null || order.getStatus() != 4 || order.isReview()) {
                response.sendRedirect(request.getContextPath() + "/customer/orders");
                return;
            }
            
            // 获取订单对应的商品信息
            Product product = customerService.getProduct(order.getProductId());
            
            request.setAttribute("order", order);
            request.setAttribute("product", product);  // 设置商品信息
            request.getRequestDispatcher("/WEB-INF/jsp/customer/review.jsp").forward(request, response);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
    
    private void submitReview(HttpServletRequest request, HttpServletResponse response, Long userId) 
            throws Exception {
        Long orderId = Long.parseLong(request.getParameter("orderId"));
        Long productId = Long.parseLong(request.getParameter("productId"));
        int rating = Integer.parseInt(request.getParameter("rating"));
        String content = request.getParameter("content");
        
        if (customerService.submitReview(orderId, productId, userId, rating, content)) {
            request.setAttribute("success", "评价提交成功");
        } else {
            request.setAttribute("error", "评价提交失败");
        }
        
        response.sendRedirect(request.getContextPath() + "/customer/orders");
    }
} 