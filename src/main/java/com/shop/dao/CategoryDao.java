package com.shop.dao;

import com.shop.model.Category;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDao extends BaseDao {
    
    public List<Category> findAll() throws Exception {
        String sql = "SELECT * FROM categories ORDER BY sort_order";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            ResultSet rs = stmt.executeQuery();
            List<Category> categories = new ArrayList<>();
            
            while (rs.next()) {
                Category category = new Category();
                category.setId(rs.getLong("id"));
                category.setName(rs.getString("name"));
                category.setParentId(rs.getLong("parent_id"));
                category.setLevel(rs.getInt("level"));
                category.setSortOrder(rs.getInt("sort_order"));
                categories.add(category);
            }
            
            return categories;
        }
    }
    
    public boolean save(Category category) throws Exception {
        String sql = "INSERT INTO categories (name, parent_id, level, sort_order) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, category.getName());
            stmt.setLong(2, category.getParentId());
            stmt.setInt(3, category.getLevel());
            stmt.setInt(4, category.getSortOrder());
            
            return stmt.executeUpdate() > 0;
        }
    }
    
    public boolean update(Category category) throws Exception {
        String sql = "UPDATE categories SET name = ?, parent_id = ?, level = ?, sort_order = ? WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, category.getName());
            stmt.setLong(2, category.getParentId());
            stmt.setInt(3, category.getLevel());
            stmt.setInt(4, category.getSortOrder());
            stmt.setLong(5, category.getId());
            
            return stmt.executeUpdate() > 0;
        }
    }
    
    public boolean delete(Long id) throws Exception {
        String sql = "DELETE FROM categories WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, id);
            return stmt.executeUpdate() > 0;
        }
    }
    
    public Category findById(Long id) throws Exception {
        String sql = "SELECT * FROM categories WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Category category = new Category();
                category.setId(rs.getLong("id"));
                category.setName(rs.getString("name"));
                category.setParentId(rs.getLong("parent_id"));
                category.setLevel(rs.getInt("level"));
                category.setSortOrder(rs.getInt("sort_order"));
                return category;
            }
            return null;
        }
    }
    
    public List<Category> findByLevel(int level) throws Exception {
        String sql = "SELECT * FROM categories WHERE level = ? ORDER BY sort_order";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, level);
            ResultSet rs = stmt.executeQuery();
            List<Category> categories = new ArrayList<>();
            
            while (rs.next()) {
                Category category = new Category();
                category.setId(rs.getLong("id"));
                category.setName(rs.getString("name"));
                category.setParentId(rs.getLong("parent_id"));
                category.setLevel(rs.getInt("level"));
                category.setSortOrder(rs.getInt("sort_order"));
                categories.add(category);
            }
            
            return categories;
        }
    }
    
    public List<Category> findByParentId(Long parentId) throws Exception {
        String sql = "SELECT * FROM categories WHERE parent_id = ? ORDER BY sort_order";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, parentId);
            ResultSet rs = stmt.executeQuery();
            List<Category> categories = new ArrayList<>();
            
            while (rs.next()) {
                Category category = new Category();
                category.setId(rs.getLong("id"));
                category.setName(rs.getString("name"));
                category.setParentId(rs.getLong("parent_id"));
                category.setLevel(rs.getInt("level"));
                category.setSortOrder(rs.getInt("sort_order"));
                categories.add(category);
            }
            
            return categories;
        }
    }
} 