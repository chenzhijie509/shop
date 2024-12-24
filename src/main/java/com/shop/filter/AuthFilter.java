package com.shop.filter;

import com.shop.model.User;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/*")
public class AuthFilter implements Filter {
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String path = req.getRequestURI().substring(req.getContextPath().length());
        
        // 不需要验证的路径
        if (isPublicPath(path)) {
            chain.doFilter(request, response);
            return;
        }
        
        // 后台路径检查
        if (path.startsWith("/admin") && !path.equals("/admin/login")) {
            User adminUser = (User) req.getSession().getAttribute("adminUser");
            if (adminUser == null || adminUser.getUserType() != 1) {
                resp.sendRedirect(req.getContextPath() + "/admin/login");
                return;
            }
            chain.doFilter(request, response);
            return;
        }
        
        // 店铺路径检查
        if (path.startsWith("/shop") && !path.equals("/shop/login")) {
            User shopUser = (User) req.getSession().getAttribute("user");
            if (shopUser == null || shopUser.getUserType() != 2) {
                resp.sendRedirect(req.getContextPath() + "/shop/login");
                return;
            }
            chain.doFilter(request, response);
            return;
        }
        
        // 普通用户路径检查
        User user = (User) req.getSession().getAttribute("user");
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/user/login");
            return;
        }
        
        // 检查权限
        if (!hasPermission(user, path)) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "没有访问权限");
            return;
        }
        
        chain.doFilter(request, response);
    }
    
    private boolean isPublicPath(String path) {
        return path.startsWith("/user/login") ||
               path.startsWith("/user/register") ||
               path.equals("/admin/login") ||
               path.equals("/shop/login") ||
               path.startsWith("/customer/products") ||
               path.startsWith("/css/") ||
               path.startsWith("/js/") ||
               path.startsWith("/images/") ||
               path.startsWith("/uploads/") ||
               path.equals("/") ||
               path.equals("/index.jsp");
    }
    
    private boolean hasPermission(User user, String path) {
        // 顾客权限
        if (path.startsWith("/customer/") || path.startsWith("/cart/")) {
            return user.getUserType() == 3;
        }
        
        // 店铺权限
        if (path.startsWith("/shop/") && !path.equals("/shop/login")) {
            return user.getUserType() == 2;
        }
        
        // 管理员权限
        if (path.startsWith("/admin/") && !path.equals("/admin/login")) {
            return user.getUserType() == 1;
        }
        
        return true;
    }
} 