package com.shop.controller;

import com.shop.model.Product;
import com.shop.model.Category;
import com.shop.service.ProductService;
import com.shop.service.CategoryService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("")  // 映射到根路径
public class IndexServlet extends HttpServlet {
    private ProductService productService = new ProductService();
    private CategoryService categoryService = new CategoryService();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            // 获取分页参数
            int page = 1;
            int pageSize = 12; // 每页显示12个商品
            
            String pageStr = request.getParameter("page");
            if (pageStr != null && !pageStr.trim().isEmpty()) {
                page = Integer.parseInt(pageStr);
            }
            
            // 获取所有分类
            List<Category> categories = categoryService.getAllCategories();
            request.setAttribute("categories", categories);
            
            // 获取商品总数和计算总页数
            int totalProducts = productService.getTotalProductCount();
            int totalPages = (int) Math.ceil((double) totalProducts / pageSize);
            
            // 获取当前页的商品
            List<Product> products = productService.getProductsByPage(page, pageSize);
            request.setAttribute("products", products);
            request.setAttribute("currentPage", page);
            request.setAttribute("totalPages", totalPages);
            
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
} 