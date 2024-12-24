package com.shop.service;

import com.shop.dao.CategoryDao;
import com.shop.model.Category;
import java.util.List;

public class CategoryService {
    private CategoryDao categoryDao = new CategoryDao();
    
    public List<Category> getAllCategories() throws Exception {
        return categoryDao.findAll();
    }
    
    public Category getCategory(Long id) throws Exception {
        return categoryDao.findById(id);
    }
    
    public boolean addCategory(Category category) throws Exception {
        return categoryDao.save(category);
    }
    
    public boolean updateCategory(Category category) throws Exception {
        return categoryDao.update(category);
    }
    
    public boolean deleteCategory(Long id) throws Exception {
        return categoryDao.delete(id);
    }
    
    public List<Category> getParentCategories() throws Exception {
        return categoryDao.findByLevel(1);  // 获取一级分类
    }
    
    public List<Category> getSubCategories(Long parentId) throws Exception {
        return categoryDao.findByParentId(parentId);  // 获取子分类
    }
} 