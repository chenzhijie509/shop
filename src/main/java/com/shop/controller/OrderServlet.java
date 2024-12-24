package com.shop.controller;

import com.shop.model.CartItem;
import com.shop.model.Order;
import com.shop.model.User;
import com.shop.service.CartService;
import com.shop.service.OrderService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/order/*")
public class OrderServlet extends HttpServlet {
    private CartService cartService = new CartService();
    private OrderService orderService = new OrderService();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getPathInfo();
        if (action == null) {
            action = "/";
        }
        
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/user/login");
            return;
        }
        
        try {
            switch (action) {
                case "/checkout":
                    showCheckout(request, response, user);
                    break;
                case "/pay":
                    Long orderId = Long.parseLong(request.getParameter("orderId"));
                    showPayPage(request, response, orderId);
                    break;
                default:
                    response.sendRedirect(request.getContextPath() + "/cart");
            }
        } catch (Exception e) {
            e.printStackTrace();  // 添加错误日志
            throw new ServletException(e);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // 设置请求编码为UTF-8，防止中文乱码
        request.setCharacterEncoding("UTF-8");
        
        String action = request.getPathInfo();
        if (action == null) {
            action = "/";
        }
        
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/user/login");
            return;
        }
        
        try {
            if ("/submit".equals(action)) {
                submitOrder(request, response, user);
            } else if ("/pay".equals(action)) {
                payOrder(request, response, user);
            } else {
                response.sendRedirect(request.getContextPath() + "/cart");
            }
        } catch (Exception e) {
            e.printStackTrace();  // 添加错误日志
            request.setAttribute("error", e.getMessage());
            if ("/submit".equals(action)) {
                showCheckout(request, response, user);
            } else {
                response.sendRedirect(request.getContextPath() + "/cart");
            }
        }
    }
    
    private void showCheckout(HttpServletRequest request, HttpServletResponse response, User user) 
            throws ServletException, IOException {
        try {
            List<CartItem> items = cartService.getCartItems(user.getId());
            if (items.isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/cart");
                return;
            }
            
            request.setAttribute("items", items);
            request.setAttribute("total", cartService.calculateTotal(items));
            request.getRequestDispatcher("/WEB-INF/jsp/order/checkout.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();  // 添加错误日志
            throw new ServletException("显示结算页面失败", e);
        }
    }
    
    private void submitOrder(HttpServletRequest request, HttpServletResponse response, User user) 
            throws Exception {
        try {
            String address = request.getParameter("address");
            String phone = request.getParameter("phone");
            String receiverName = request.getParameter("receiverName");
            
            if (address == null || address.trim().isEmpty() ||
                phone == null || phone.trim().isEmpty() ||
                receiverName == null || receiverName.trim().isEmpty()) {
                throw new Exception("请填写完整的收货信息");
            }
            
            Long orderId = orderService.createOrder(user.getId(), address, phone, receiverName);
            if (orderId != null) {
                // 清空购物车
                cartService.clearCart(user.getId());
                // 重定向到支付页面
                response.sendRedirect(request.getContextPath() + "/order/pay?orderId=" + orderId);
            } else {
                throw new Exception("订单创建失败");
            }
        } catch (Exception e) {
            e.printStackTrace();  // 添加错误日志
            request.setAttribute("error", e.getMessage());
            showCheckout(request, response, user);
        }
    }
    
    private void payOrder(HttpServletRequest request, HttpServletResponse response, User user) 
            throws ServletException, IOException {
        try {
            Long orderId = Long.parseLong(request.getParameter("orderId"));
            String payType = request.getParameter("payType");
            
            boolean success = orderService.payOrder(orderId, user.getId(), payType);
            if (success) {
                response.sendRedirect(request.getContextPath() + "/customer/orders");
            } else {
                request.setAttribute("error", "支付失败，请重试");
                showPayPage(request, response, orderId);
            }
        } catch (Exception e) {
            e.printStackTrace();  // 添加错误日志
            throw new ServletException("支付失败", e);
        }
    }
    
    private void showPayPage(HttpServletRequest request, HttpServletResponse response, Long orderId) 
            throws ServletException, IOException {
        try {
            Order order = orderService.getOrder(orderId);
            if (order == null || order.getStatus() != 1) { // 1表示待付款
                response.sendRedirect(request.getContextPath() + "/customer/orders");
                return;
            }
            
            request.setAttribute("order", order);
            request.getRequestDispatcher("/WEB-INF/jsp/order/pay.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();  // 添加错误日志
            throw new ServletException("显示支付页面失败", e);
        }
    }
} 