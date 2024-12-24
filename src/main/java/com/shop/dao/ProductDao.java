package com.shop.dao;

import com.shop.model.Product;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDao extends BaseDao {
    
    public List<Product> findByShopId(Long shopId) throws Exception {
        String sql = "SELECT * FROM products WHERE shop_id = ? ORDER BY created_at DESC";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, shopId);
            ResultSet rs = stmt.executeQuery();
            List<Product> products = new ArrayList<>();
            
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getLong("id"));
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getBigDecimal("price"));
                product.setStock(rs.getInt("stock"));
                product.setCategoryId(rs.getLong("category_id"));
                product.setShopId(rs.getLong("shop_id"));
                product.setImage(rs.getString("image"));
                product.setStatus(rs.getBoolean("status"));
                product.setCreatedAt(rs.getTimestamp("created_at"));
                product.setUpdatedAt(rs.getTimestamp("updated_at"));
                products.add(product);
            }
            
            return products;
        }
    }
    
    public boolean save(Product product) throws Exception {
        String sql = "INSERT INTO products (name, description, price, stock, category_id, shop_id, image, status) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, product.getName());
            stmt.setString(2, product.getDescription());
            stmt.setBigDecimal(3, product.getPrice());
            stmt.setInt(4, product.getStock());
            stmt.setLong(5, product.getCategoryId());
            stmt.setLong(6, product.getShopId());
            stmt.setString(7, product.getImage());
            stmt.setBoolean(8, product.getStatus());
            
            return stmt.executeUpdate() > 0;
        }
    }
    
    public boolean update(Product product) throws Exception {
        String sql = "UPDATE products SET name = ?, description = ?, price = ?, stock = ?, " +
                    "category_id = ?, image = ?, status = ? WHERE id = ? AND shop_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, product.getName());
            stmt.setString(2, product.getDescription());
            stmt.setBigDecimal(3, product.getPrice());
            stmt.setInt(4, product.getStock());
            stmt.setLong(5, product.getCategoryId());
            stmt.setString(6, product.getImage());
            stmt.setBoolean(7, product.getStatus());
            stmt.setLong(8, product.getId());
            stmt.setLong(9, product.getShopId());
            
            return stmt.executeUpdate() > 0;
        }
    }
    
    public boolean delete(Long id, Long shopId) throws Exception {
        String sql = "DELETE FROM products WHERE id = ? AND shop_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, id);
            stmt.setLong(2, shopId);
            
            return stmt.executeUpdate() > 0;
        }
    }
    
    public int countByShopId(Long shopId) throws Exception {
        String sql = "SELECT COUNT(*) FROM products WHERE shop_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, shopId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        }
    }
    
    public List<Product> search(String keyword, Long categoryId) throws Exception {
        StringBuilder sql = new StringBuilder("SELECT * FROM products WHERE status = true");
        List<Object> params = new ArrayList<>();
        
        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append(" AND (name LIKE ? OR description LIKE ?)");
            params.add("%" + keyword + "%");
            params.add("%" + keyword + "%");
        }
        
        if (categoryId != null) {
            sql.append(" AND category_id = ?");
            params.add(categoryId);
        }
        
        sql.append(" ORDER BY created_at DESC");
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
            
            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }
            
            ResultSet rs = stmt.executeQuery();
            List<Product> products = new ArrayList<>();
            
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getLong("id"));
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getBigDecimal("price"));
                product.setStock(rs.getInt("stock"));
                product.setCategoryId(rs.getLong("category_id"));
                product.setShopId(rs.getLong("shop_id"));
                product.setImage(rs.getString("image"));
                product.setStatus(rs.getBoolean("status"));
                product.setCreatedAt(rs.getTimestamp("created_at"));
                product.setUpdatedAt(rs.getTimestamp("updated_at"));
                products.add(product);
            }
            
            return products;
        }
    }
    
    public List<Product> findByCategory(Long categoryId) throws Exception {
        String sql = "SELECT * FROM products WHERE category_id = ? AND status = true ORDER BY created_at DESC";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, categoryId);
            ResultSet rs = stmt.executeQuery();
            List<Product> products = new ArrayList<>();
            
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getLong("id"));
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getBigDecimal("price"));
                product.setStock(rs.getInt("stock"));
                product.setCategoryId(rs.getLong("category_id"));
                product.setShopId(rs.getLong("shop_id"));
                product.setImage(rs.getString("image"));
                product.setStatus(rs.getBoolean("status"));
                product.setCreatedAt(rs.getTimestamp("created_at"));
                product.setUpdatedAt(rs.getTimestamp("updated_at"));
                products.add(product);
            }
            
            return products;
        }
    }
    
    public List<Product> findRecent(int limit) throws Exception {
        String sql = "SELECT * FROM products WHERE status = true ORDER BY created_at DESC LIMIT ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, limit);
            ResultSet rs = stmt.executeQuery();
            List<Product> products = new ArrayList<>();
            
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getLong("id"));
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getBigDecimal("price"));
                product.setStock(rs.getInt("stock"));
                product.setCategoryId(rs.getLong("category_id"));
                product.setShopId(rs.getLong("shop_id"));
                product.setImage(rs.getString("image"));
                product.setStatus(rs.getBoolean("status"));
                product.setCreatedAt(rs.getTimestamp("created_at"));
                product.setUpdatedAt(rs.getTimestamp("updated_at"));
                products.add(product);
            }
            
            return products;
        }
    }
    
    public List<Product> findAll() throws Exception {
        String sql = "SELECT * FROM products ORDER BY created_at DESC";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            ResultSet rs = stmt.executeQuery();
            List<Product> products = new ArrayList<>();
            
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getLong("id"));
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getBigDecimal("price"));
                product.setStock(rs.getInt("stock"));
                product.setCategoryId(rs.getLong("category_id"));
                product.setShopId(rs.getLong("shop_id"));
                product.setImage(rs.getString("image"));
                product.setStatus(rs.getBoolean("status"));
                product.setCreatedAt(rs.getTimestamp("created_at"));
                product.setUpdatedAt(rs.getTimestamp("updated_at"));
                products.add(product);
            }
            
            return products;
        }
    }
    
    public boolean updateStatus(Long id, boolean status) throws Exception {
        String sql = "UPDATE products SET status = ? WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setBoolean(1, status);
            stmt.setLong(2, id);
            return stmt.executeUpdate() > 0;
        }
    }
    
    public boolean delete(Long id) throws Exception {
        String sql = "DELETE FROM products WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            return stmt.executeUpdate() > 0;
        }
    }
    
    public List<Product> findByShopIdWithPaging(Long shopId, int page, int pageSize) throws Exception {
        String sql = "SELECT * FROM products WHERE shop_id = ? ORDER BY created_at DESC LIMIT ? OFFSET ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, shopId);
            stmt.setInt(2, pageSize);
            stmt.setInt(3, (page - 1) * pageSize);
            
            ResultSet rs = stmt.executeQuery();
            List<Product> products = new ArrayList<>();
            
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getLong("id"));
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getBigDecimal("price"));
                product.setStock(rs.getInt("stock"));
                product.setCategoryId(rs.getLong("category_id"));
                product.setShopId(rs.getLong("shop_id"));
                product.setImage(rs.getString("image"));
                product.setStatus(rs.getBoolean("status"));
                product.setCreatedAt(rs.getTimestamp("created_at"));
                product.setUpdatedAt(rs.getTimestamp("updated_at"));
                products.add(product);
            }
            
            return products;
        }
    }
    
    public int getTotalCount() throws Exception {
        String sql = "SELECT COUNT(*) FROM products WHERE status = true";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        }
    }
    
    public List<Product> findByPage(int page, int pageSize) throws Exception {
        String sql = "SELECT * FROM products WHERE status = true ORDER BY created_at DESC LIMIT ? OFFSET ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, pageSize);
            stmt.setInt(2, (page - 1) * pageSize);
            
            ResultSet rs = stmt.executeQuery();
            List<Product> products = new ArrayList<>();
            
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getLong("id"));
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getBigDecimal("price"));
                product.setStock(rs.getInt("stock"));
                product.setCategoryId(rs.getLong("category_id"));
                product.setShopId(rs.getLong("shop_id"));
                product.setImage(rs.getString("image"));
                product.setStatus(rs.getBoolean("status"));
                product.setCreatedAt(rs.getTimestamp("created_at"));
                product.setUpdatedAt(rs.getTimestamp("updated_at"));
                products.add(product);
            }
            
            return products;
        }
    }
    
    public Product findByIdAndShopId(Long id, Long shopId) throws Exception {
        String sql = "SELECT * FROM products WHERE id = ? AND shop_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, id);
            stmt.setLong(2, shopId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Product product = new Product();
                product.setId(rs.getLong("id"));
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getBigDecimal("price"));
                product.setStock(rs.getInt("stock"));
                product.setCategoryId(rs.getLong("category_id"));
                product.setShopId(rs.getLong("shop_id"));
                product.setImage(rs.getString("image"));
                product.setStatus(rs.getBoolean("status"));
                product.setCreatedAt(rs.getTimestamp("created_at"));
                product.setUpdatedAt(rs.getTimestamp("updated_at"));
                return product;
            }
            return null;
        }
    }
    
    public Product findById(Long id) throws Exception {
        String sql = "SELECT * FROM products WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Product product = new Product();
                product.setId(rs.getLong("id"));
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getBigDecimal("price"));
                product.setStock(rs.getInt("stock"));
                product.setCategoryId(rs.getLong("category_id"));
                product.setShopId(rs.getLong("shop_id"));
                product.setImage(rs.getString("image"));
                product.setStatus(rs.getBoolean("status"));
                product.setCreatedAt(rs.getTimestamp("created_at"));
                product.setUpdatedAt(rs.getTimestamp("updated_at"));
                return product;
            }
            return null;
        }
    }
} 