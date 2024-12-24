package com.shop.controller;

import com.shop.model.Category;
import com.shop.model.Order;
import com.shop.model.Product;
import com.shop.model.User;
import com.shop.service.AdminService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/admin/*")
public class AdminServlet extends HttpServlet {
    private AdminService adminService = new AdminService();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getPathInfo();
        
        // 检查管理员登录状态
        User user = (User) request.getSession().getAttribute("adminUser");
        if (user == null || user.getUserType() != 1) {
            response.sendRedirect(request.getContextPath() + "/admin/login");
            return;
        }
        
        try {
            switch (action) {
                case "/products":
                    listProducts(request, response);
                    break;
                case "/orders":
                    listOrders(request, response);
                    break;
                case "/statistics":
                    showStatistics(request, response);
                    break;
                case "/users":
                    listUsers(request, response);
                    break;
                case "/shops":
                    listShops(request, response);
                    break;
                case "/categories":
                    listCategories(request, response);
                    break;
                default:
                    showDashboard(request, response);
            }
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "系统错误");
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getPathInfo();
        try {
            switch (action) {
                case "/user/status":
                    updateUserStatus(request, response);
                    break;
                case "/shop/status":
                    updateShopStatus(request, response);
                    break;
                case "/category/add":
                    addCategory(request, response);
                    break;
                case "/product/status":
                    updateProductStatus(request, response);
                    break;
                case "/product/delete":
                    deleteProduct(request, response);
                    break;
                default:
                    response.sendRedirect(request.getContextPath() + "/admin");
            }
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "系统错误");
        }
    }
    
    private void showDashboard(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            request.setAttribute("userCount", adminService.getUserCount());
            request.setAttribute("shopCount", adminService.getShopCount());
            request.getRequestDispatcher("/WEB-INF/jsp/admin/dashboard.jsp").forward(request, response);
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "系统错误");
        }
    }
    
    private void listUsers(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            List<User> users = adminService.getAllCustomers();
            request.setAttribute("users", users);
            request.getRequestDispatcher("/WEB-INF/jsp/admin/users.jsp").forward(request, response);
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "系统错误");
        }
    }
    
    private void listShops(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            List<User> shops = adminService.getAllShops();
            request.setAttribute("shops", shops);
            request.getRequestDispatcher("/WEB-INF/jsp/admin/shops.jsp").forward(request, response);
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "系统错误");
        }
    }
    
    private void updateUserStatus(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            Long userId = Long.parseLong(request.getParameter("userId"));
            boolean status = Boolean.parseBoolean(request.getParameter("status"));
            adminService.updateUserStatus(userId, status);
            response.sendRedirect(request.getContextPath() + "/admin/users");
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "系统错误");
        }
    }
    
    private void updateShopStatus(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            Long shopId = Long.parseLong(request.getParameter("shopId"));
            boolean status = Boolean.parseBoolean(request.getParameter("status"));
            adminService.updateShopStatus(shopId, status);
            response.sendRedirect(request.getContextPath() + "/admin/shops");
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "系统错误");
        }
    }
    
    private void listCategories(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            List<Category> categories = adminService.getAllCategories();
            request.setAttribute("categories", categories);
            request.getRequestDispatcher("/WEB-INF/jsp/admin/categories.jsp").forward(request, response);
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "系统错误");
        }
    }
    
    private void addCategory(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String name = request.getParameter("name");
        Long parentId = Long.parseLong(request.getParameter("parentId"));
        Integer level = Integer.parseInt(request.getParameter("level"));
        Integer sortOrder = Integer.parseInt(request.getParameter("sortOrder"));
        
        Category category = new Category();
        category.setName(name);
        category.setParentId(parentId);
        category.setLevel(level);
        category.setSortOrder(sortOrder);
        
        try {
            adminService.addCategory(category);
            response.sendRedirect(request.getContextPath() + "/admin/categories");
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "系统错误");
        }
    }
    
    private void listProducts(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            List<Product> products = adminService.getAllProducts();
            request.setAttribute("products", products);
            request.getRequestDispatcher("/WEB-INF/jsp/admin/products.jsp").forward(request, response);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
    
    private void listOrders(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            List<Order> orders = adminService.getAllOrders();
            request.setAttribute("orders", orders);
            request.getRequestDispatcher("/WEB-INF/jsp/admin/orders.jsp").forward(request, response);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
    
    private void showStatistics(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            Map<String, Object> stats = adminService.getStatistics();
            request.setAttribute("stats", stats);
            request.getRequestDispatcher("/WEB-INF/jsp/admin/statistics.jsp").forward(request, response);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
    
    private void updateProductStatus(HttpServletRequest request, HttpServletResponse response) 
            throws Exception {
        Long productId = Long.parseLong(request.getParameter("id"));
        boolean status = Boolean.parseBoolean(request.getParameter("status"));
        adminService.updateProductStatus(productId, status);
        response.sendRedirect(request.getContextPath() + "/admin/products");
    }
    
    private void deleteProduct(HttpServletRequest request, HttpServletResponse response) 
            throws Exception {
        Long productId = Long.parseLong(request.getParameter("id"));
        adminService.deleteProduct(productId);
        response.sendRedirect(request.getContextPath() + "/admin/products");
    }
} 