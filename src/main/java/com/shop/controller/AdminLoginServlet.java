package com.shop.controller;

import com.shop.model.User;
import com.shop.service.AdminService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/admin/login")
public class AdminLoginServlet extends HttpServlet {
    private AdminService adminService = new AdminService();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/jsp/admin/login.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        try {
            User user = adminService.adminLogin(username, password);
            if (user != null && user.getUserType() == 1) {
                HttpSession session = request.getSession();
                session.setAttribute("adminUser", user);
                response.sendRedirect(request.getContextPath() + "/admin/");
            } else {
                request.setAttribute("error", "用户名或密码错误");
                request.getRequestDispatcher("/WEB-INF/jsp/admin/login.jsp").forward(request, response);
            }
        } catch (Exception e) {
            request.setAttribute("error", "系统错误，请稍后重试");
            request.getRequestDispatcher("/WEB-INF/jsp/admin/login.jsp").forward(request, response);
        }
    }
} 