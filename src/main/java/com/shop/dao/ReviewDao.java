package com.shop.dao;

import com.shop.model.Review;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReviewDao extends BaseDao {
    
    public boolean save(Review review) throws Exception {
        String sql = "INSERT INTO reviews (order_id, product_id, user_id, rating, content, created_at) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, review.getOrderId());
            stmt.setLong(2, review.getProductId());
            stmt.setLong(3, review.getUserId());
            stmt.setInt(4, review.getRating());
            stmt.setString(5, review.getContent());
            stmt.setTimestamp(6, new Timestamp(review.getCreatedAt().getTime()));
            
            return stmt.executeUpdate() > 0;
        }
    }
    
    public List<Review> findByProductId(Long productId) throws Exception {
        String sql = "SELECT r.*, u.username FROM reviews r " +
                    "JOIN users u ON r.user_id = u.id " +
                    "WHERE r.product_id = ? " +
                    "ORDER BY r.created_at DESC";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, productId);
            ResultSet rs = stmt.executeQuery();
            List<Review> reviews = new ArrayList<>();
            
            while (rs.next()) {
                Review review = new Review();
                review.setId(rs.getLong("id"));
                review.setOrderId(rs.getLong("order_id"));
                review.setProductId(rs.getLong("product_id"));
                review.setUserId(rs.getLong("user_id"));
                review.setRating(rs.getInt("rating"));
                review.setContent(rs.getString("content"));
                review.setCreatedAt(rs.getTimestamp("created_at"));
                review.setUsername(rs.getString("username"));
                reviews.add(review);
            }
            
            return reviews;
        }
    }
    
    public double getAverageRating(Long productId) throws Exception {
        String sql = "SELECT AVG(rating) as avg_rating FROM reviews WHERE product_id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, productId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getDouble("avg_rating");
            }
            return 0.0;
        }
    }
} 