package com.shop.controller;

import com.shop.model.Product;
import com.shop.model.Order;
import com.shop.model.User;
import com.shop.model.Category;
import com.shop.service.ShopService;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import javax.servlet.http.Part;

@WebServlet("/shop/*")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024, // 1MB
    maxFileSize = 1024 * 1024 * 10,  // 10MB
    maxRequestSize = 1024 * 1024 * 15 // 15MB
)
public class ShopServlet extends HttpServlet {
    private ShopService shopService = new ShopService();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getPathInfo();
        if (action == null) {
            action = "/";
        }
        
        User user = (User) request.getSession().getAttribute("user");
        if (user == null || user.getUserType() != 2) {
            response.sendRedirect(request.getContextPath() + "/user/login");
            return;
        }
        
        try {
            switch (action) {
                case "/":
                case "/dashboard":
                    showDashboard(request, response, user.getId());
                    break;
                case "/products":
                    listProducts(request, response, user.getId());
                    break;
                case "/orders":
                    listOrders(request, response, user.getId());
                    break;
                case "/profile":
                    showProfile(request, response, user.getId());
                    break;
                case "/product/edit":
                    showEditProduct(request, response, user.getId());
                    break;
                default:
                    response.sendRedirect(request.getContextPath() + "/shop/dashboard");
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
        if (user == null || user.getUserType() != 2) {
            response.sendRedirect(request.getContextPath() + "/user/login");
            return;
        }

        try {
            switch (action) {
                case "/product/add":
                    addProduct(request, response, user.getId());
                    break;
                case "/product/update":
                    updateProduct(request, response, user.getId());
                    break;
                case "/product/delete":
                    deleteProduct(request, response, user.getId());
                    break;
                case "/order/status":
                    updateOrderStatus(request, response, user.getId());
                    break;
                case "/profile/update":
                    updateProfile(request, response, user.getId());
                    break;
                default:
                    response.sendRedirect(request.getContextPath() + "/shop/dashboard");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "系统错误：" + e.getMessage());
            try {
                if (action.startsWith("/product")) {
                    listProducts(request, response, user.getId());
                } else if (action.startsWith("/order")) {
                    listOrders(request, response, user.getId());
                } else {
                    response.sendRedirect(request.getContextPath() + "/shop/dashboard");
                }
            } catch (Exception ex) {
                throw new ServletException(ex);
            }
        }
    }
    
    private void showDashboard(HttpServletRequest request, HttpServletResponse response, Long shopId) 
            throws Exception {
        request.setAttribute("orderCount", shopService.getOrderCount(shopId));
        request.setAttribute("productCount", shopService.getProductCount(shopId));
        request.setAttribute("recentOrders", shopService.getRecentOrders(shopId));
        request.getRequestDispatcher("/WEB-INF/jsp/shop/dashboard.jsp").forward(request, response);
    }
    
    private void listProducts(HttpServletRequest request, HttpServletResponse response, Long shopId) 
            throws Exception {
        int page = 1;
        int pageSize = 10; // 每页显示10条记录
        
        String pageStr = request.getParameter("page");
        if (pageStr != null && !pageStr.trim().isEmpty()) {
            page = Integer.parseInt(pageStr);
        }
        
        int totalProducts = shopService.getProductCount(shopId);
        int totalPages = (int) Math.ceil((double) totalProducts / pageSize);
        
        List<Product> products = shopService.getProductsByPage(shopId, page, pageSize);
        List<Category> categories = shopService.getAllCategories(); // 获取所有分类
        
        request.setAttribute("products", products);
        request.setAttribute("categories", categories); // 添加分类数据
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.getRequestDispatcher("/WEB-INF/jsp/shop/products.jsp").forward(request, response);
    }
    
    private void listOrders(HttpServletRequest request, HttpServletResponse response, Long shopId) 
            throws Exception {
        List<Order> orders = shopService.getAllOrders(shopId);
        request.setAttribute("orders", orders);
        request.getRequestDispatcher("/WEB-INF/jsp/shop/orders.jsp").forward(request, response);
    }
    
    private void showProfile(HttpServletRequest request, HttpServletResponse response, Long shopId) 
            throws Exception {
        User shop = shopService.getShopInfo(shopId);
        request.setAttribute("shop", shop);
        request.getRequestDispatcher("/WEB-INF/jsp/shop/profile.jsp").forward(request, response);
    }
    
    private void addProduct(HttpServletRequest request, HttpServletResponse response, Long shopId) 
            throws Exception {
        // 获取上传的文件
        Part filePart = request.getPart("image");
        String fileName = null;
        
        if (filePart != null && filePart.getSize() > 0) {
            // 生成唯一的文件名
            String originalFileName = getFileName(filePart);
            String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
            fileName = "product_" + System.currentTimeMillis() + extension;
            
            // 确保上传目录存在
            String uploadPath = getServletContext().getRealPath("/uploads/products/");
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }
            
            // 保存文件
            filePart.write(uploadPath + File.separator + fileName);
        }
        
        // 创建商品对象
        Product product = new Product();
        product.setName(request.getParameter("name"));
        product.setDescription(request.getParameter("description"));
        product.setPrice(new BigDecimal(request.getParameter("price")));
        product.setStock(Integer.parseInt(request.getParameter("stock")));
        product.setCategoryId(Long.parseLong(request.getParameter("categoryId")));
        product.setShopId(shopId);
        product.setStatus(true);
        product.setImage("/uploads/products/" + fileName); // 设置图片路径
        
        // 保存商品信息
        shopService.addProduct(product);
        response.sendRedirect(request.getContextPath() + "/shop/products");
    }
    
    private String getFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] tokens = contentDisp.split(";");
        for (String token : tokens) {
            if (token.trim().startsWith("filename")) {
                return token.substring(token.indexOf("=") + 2, token.length() - 1);
            }
        }
        return "";
    }
    
    private void updateProduct(HttpServletRequest request, HttpServletResponse response, Long shopId) 
            throws Exception {
        Long productId = Long.parseLong(request.getParameter("id"));
        Product product = new Product();
        product.setId(productId);
        product.setName(request.getParameter("name"));
        product.setDescription(request.getParameter("description"));
        product.setPrice(new BigDecimal(request.getParameter("price")));
        product.setStock(Integer.parseInt(request.getParameter("stock")));
        product.setCategoryId(Long.parseLong(request.getParameter("categoryId")));
        product.setShopId(shopId);
        product.setStatus(Boolean.parseBoolean(request.getParameter("status")));
        
        // 处理图片上传
        Part filePart = request.getPart("image");
        if (filePart != null && filePart.getSize() > 0) {
            String fileName = "product_" + System.currentTimeMillis() + 
                             getFileName(filePart).substring(getFileName(filePart).lastIndexOf("."));
            String uploadPath = getServletContext().getRealPath("/uploads/products/");
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }
            filePart.write(uploadPath + File.separator + fileName);
            product.setImage("/uploads/products/" + fileName);
        } else {
            // 如果没有上传新图片，保留原来的图片
            Product oldProduct = shopService.getProduct(productId, shopId);
            product.setImage(oldProduct.getImage());
        }
        
        shopService.updateProduct(product);
        response.sendRedirect(request.getContextPath() + "/shop/products");
    }
    
    private void deleteProduct(HttpServletRequest request, HttpServletResponse response, Long shopId) 
            throws Exception {
        Long productId = Long.parseLong(request.getParameter("id"));
        shopService.deleteProduct(productId, shopId);
        response.sendRedirect(request.getContextPath() + "/shop/products");
    }
    
    private void updateOrderStatus(HttpServletRequest request, HttpServletResponse response, Long shopId) 
            throws Exception {
        Long orderId = Long.parseLong(request.getParameter("id"));
        Integer status = Integer.parseInt(request.getParameter("status"));
        shopService.updateOrderStatus(orderId, shopId, status);
        response.sendRedirect(request.getContextPath() + "/shop/orders");
    }
    
    private void updateProfile(HttpServletRequest request, HttpServletResponse response, Long shopId) 
            throws Exception {
        User shop = new User();
        shop.setId(shopId);
        shop.setEmail(request.getParameter("email"));
        shop.setRealName(request.getParameter("realName"));
        shop.setPhone(request.getParameter("phone"));
        shop.setAddress(request.getParameter("address"));
        
        if (shopService.updateShopInfo(shop)) {
            request.setAttribute("success", "店铺信息更新成功");
        } else {
            request.setAttribute("error", "店铺信息更新失败");
        }
        
        // 重新获取最新的店铺信息
        shop = shopService.getShopInfo(shopId);
        request.setAttribute("shop", shop);
        request.getRequestDispatcher("/WEB-INF/jsp/shop/profile.jsp").forward(request, response);
    }
    
    private void showEditProduct(HttpServletRequest request, HttpServletResponse response, Long shopId) 
            throws Exception {
        Long productId = Long.parseLong(request.getParameter("id"));
        Product product = shopService.getProduct(productId, shopId);
        if (product == null) {
            response.sendRedirect(request.getContextPath() + "/shop/products");
            return;
        }
        
        List<Category> categories = shopService.getAllCategories();
        request.setAttribute("product", product);
        request.setAttribute("categories", categories);
        request.getRequestDispatcher("/WEB-INF/jsp/shop/edit-product.jsp").forward(request, response);
    }
} 