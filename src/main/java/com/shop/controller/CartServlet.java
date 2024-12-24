package com.shop.controller;

import com.shop.model.CartItem;
import com.shop.model.User;
import com.shop.service.CartService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.math.BigDecimal;

@WebServlet("/cart/*")
public class CartServlet extends HttpServlet {
    private CartService cartService = new CartService();
    
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
                case "/":
                    showCart(request, response, user.getId());
                    break;
                case "/add":
                    addToCart(request, response, user.getId());
                    break;
                case "/update":
                    updateCart(request, response, user.getId());
                    break;
                case "/delete":
                    deleteFromCart(request, response, user.getId());
                    break;
                case "/clear":
                    clearCart(request, response, user.getId());
                    break;
                default:
                    response.sendRedirect(request.getContextPath() + "/cart");
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
    
    private void showCart(HttpServletRequest request, HttpServletResponse response, Long userId) 
            throws Exception {
        List<CartItem> items = cartService.getCartItems(userId);
        BigDecimal total = cartService.calculateTotal(items);
        request.setAttribute("items", items);
        request.setAttribute("total", total);
        request.getRequestDispatcher("/WEB-INF/jsp/cart/index.jsp").forward(request, response);
    }
    
    private void addToCart(HttpServletRequest request, HttpServletResponse response, Long userId) 
            throws Exception {
        Long productId = Long.parseLong(request.getParameter("productId"));
        Integer quantity = Integer.parseInt(request.getParameter("quantity") != null ? 
                                         request.getParameter("quantity") : "1");
        
        cartService.addToCart(userId, productId, quantity);
        response.sendRedirect(request.getContextPath() + "/cart");
    }
    
    private void updateCart(HttpServletRequest request, HttpServletResponse response, Long userId) 
            throws Exception {
        Long itemId = Long.parseLong(request.getParameter("itemId"));
        Integer quantity = Integer.parseInt(request.getParameter("quantity"));
        
        cartService.updateQuantity(itemId, quantity);
        response.sendRedirect(request.getContextPath() + "/cart");
    }
    
    private void deleteFromCart(HttpServletRequest request, HttpServletResponse response, Long userId) 
            throws Exception {
        Long itemId = Long.parseLong(request.getParameter("itemId"));
        cartService.removeFromCart(itemId);
        response.sendRedirect(request.getContextPath() + "/cart");
    }
    
    private void clearCart(HttpServletRequest request, HttpServletResponse response, Long userId) 
            throws Exception {
        cartService.clearCart(userId);
        response.sendRedirect(request.getContextPath() + "/cart");
    }
} 