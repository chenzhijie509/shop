package com.shop.service;

import com.shop.dao.ProductDao;
import com.shop.model.Product;
import java.util.List;

public class ProductService {
    private ProductDao productDao = new ProductDao();
    
    // 获取最新的n个商品
    public List<Product> getRecentProducts(int limit) throws Exception {
        return productDao.findRecent(limit);
    }
    
    public int getTotalProductCount() throws Exception {
        return productDao.getTotalCount();
    }
    
    public List<Product> getProductsByPage(int page, int pageSize) throws Exception {
        return productDao.findByPage(page, pageSize);
    }
    
    // 添加获取单个商品的方法
    public Product getProduct(Long productId) throws Exception {
        return productDao.findById(productId);
    }
} 