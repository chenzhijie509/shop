package com.shop.dao;

import com.shop.model.CartItem;
import com.shop.model.Product;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

public class CartDao extends BaseDao {
    
    public List<CartItem> findByUserId(Long userId) throws Exception {
        String sql = "SELECT c.*, p.* FROM cart_items c " +
                    "JOIN products p ON c.product_id = p.id " +
                    "WHERE c.user_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, userId);
            ResultSet rs = stmt.executeQuery();
            List<CartItem> items = new ArrayList<>();
            
            while (rs.next()) {
                CartItem item = new CartItem();
                item.setId(rs.getLong("c.id"));
                item.setUserId(rs.getLong("c.user_id"));
                item.setProductId(rs.getLong("c.product_id"));
                item.setQuantity(rs.getInt("c.quantity"));
                
                Product product = new Product();
                product.setId(rs.getLong("p.id"));
                product.setName(rs.getString("p.name"));
                product.setPrice(rs.getBigDecimal("p.price"));
                product.setShopId(rs.getLong("p.shop_id"));
                product.setImage(rs.getString("p.image"));
                item.setProduct(product);
                
                item.setSubtotal(product.getPrice().multiply(new BigDecimal(item.getQuantity())));
                
                items.add(item);
            }
            
            return items;
        }
    }
    
    public boolean addItem(CartItem item) throws Exception {
        String sql = "INSERT INTO cart_items (user_id, product_id, quantity) VALUES (?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, item.getUserId());
            stmt.setLong(2, item.getProductId());
            stmt.setInt(3, item.getQuantity());
            
            return stmt.executeUpdate() > 0;
        }
    }
    
    public boolean updateQuantity(Long id, Integer quantity) throws Exception {
        String sql = "UPDATE cart_items SET quantity = ? WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, quantity);
            stmt.setLong(2, id);
            
            return stmt.executeUpdate() > 0;
        }
    }
    
    public boolean deleteItem(Long id) throws Exception {
        String sql = "DELETE FROM cart_items WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, id);
            return stmt.executeUpdate() > 0;
        }
    }
    
    public boolean clearCart(Long userId) throws Exception {
        String sql = "DELETE FROM cart_items WHERE user_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, userId);
            return stmt.executeUpdate() > 0;
        }
    }
} 