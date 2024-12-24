package com.shop.controller;

import com.shop.model.User;
import com.shop.service.ShopService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/shop/login")
public class ShopLoginServlet extends HttpServlet {
    private ShopService shopService = new ShopService();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/jsp/shop/login.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        try {
            User user = shopService.shopLogin(username, password);
            if (user != null && user.getUserType() == 2) {  // 2表示店铺账户
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                response.sendRedirect(request.getContextPath() + "/shop/");
            } else {
                request.setAttribute("error", "用户名或密码错误");
                request.getRequestDispatcher("/WEB-INF/jsp/shop/login.jsp").forward(request, response);
            }
        } catch (Exception e) {
            request.setAttribute("error", "系统错误，请稍后重试");
            request.getRequestDispatcher("/WEB-INF/jsp/shop/login.jsp").forward(request, response);
        }
    }
} 