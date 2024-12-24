package com.shop.controller;

import com.shop.model.Product;
import com.shop.service.ProductService;
import com.shop.dao.ReviewDao;
import com.shop.model.Review;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/product/*")
public class ProductServlet extends HttpServlet {
    private ProductService productService = new ProductService();
    private ReviewDao reviewDao = new ReviewDao();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getPathInfo();
        
        try {
            if ("/detail".equals(action)) {
                showProductDetail(request, response);
            } else {
                response.sendRedirect(request.getContextPath() + "/");
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
    
    private void showProductDetail(HttpServletRequest request, HttpServletResponse response) 
            throws Exception {
        Long productId = Long.parseLong(request.getParameter("id"));
        
        Product product = productService.getProduct(productId);
        if (product == null) {
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }
        
        // 获取商品评价
        List<Review> reviews = reviewDao.findByProductId(productId);
        double avgRating = reviewDao.getAverageRating(productId);
        
        request.setAttribute("product", product);
        request.setAttribute("reviews", reviews);
        request.setAttribute("avgRating", avgRating);
        
        request.getRequestDispatcher("/WEB-INF/jsp/product/detail.jsp")
               .forward(request, response);
    }
} 