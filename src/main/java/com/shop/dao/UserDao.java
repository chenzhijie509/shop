package com.shop.dao;

import com.shop.model.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao extends BaseDao {
    
    public User findByUsername(String username) throws Exception {
        String sql = "SELECT * FROM users WHERE username = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            System.out.println("Executing SQL: " + sql);
            System.out.println("Username parameter: " + username);
            
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setUserType(rs.getInt("user_type"));
                user.setStatus(rs.getBoolean("status"));
                System.out.println("Found user: " + user.getUsername());
                return user;
            }
            System.out.println("User not found");
            return null;
        } catch (Exception e) {
            System.out.println("Error in findByUsername: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
    
    public boolean save(User user) throws Exception {
        String sql = "INSERT INTO users (username, password, email, user_type) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());
            stmt.setInt(4, user.getUserType());
            
            return stmt.executeUpdate() > 0;
        }
    }
    
    public List<User> findByUserType(int userType) throws Exception {
        String sql = "SELECT * FROM users WHERE user_type = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, userType);
            ResultSet rs = stmt.executeQuery();
            List<User> users = new ArrayList<>();
            
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setUserType(rs.getInt("user_type"));
                user.setStatus(rs.getBoolean("status"));
                users.add(user);
            }
            
            return users;
        }
    }
    
    public int getUserCount() throws Exception {
        String sql = "SELECT COUNT(*) FROM users WHERE user_type = 3";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        }
    }
    
    public int getShopCount() throws Exception {
        String sql = "SELECT COUNT(*) FROM users WHERE user_type = 2";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        }
    }
    
    public void updateStatus(Long userId, boolean status) throws Exception {
        String sql = "UPDATE users SET status = ? WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setBoolean(1, status);
            stmt.setLong(2, userId);
            stmt.executeUpdate();
        }
    }
    
    public User findById(Long id) throws Exception {
        String sql = "SELECT * FROM users WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setUserType(rs.getInt("user_type"));
                user.setStatus(rs.getBoolean("status"));
                user.setRealName(rs.getString("real_name"));
                user.setPhone(rs.getString("phone"));
                user.setAddress(rs.getString("address"));
                user.setCreatedAt(rs.getTimestamp("created_at"));
                user.setUpdatedAt(rs.getTimestamp("updated_at"));
                return user;
            }
            return null;
        }
    }
    
    public boolean update(User user) throws Exception {
        String sql = "UPDATE users SET email = ?, real_name = ?, phone = ?, address = ? WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getRealName());
            stmt.setString(3, user.getPhone());
            stmt.setString(4, user.getAddress());
            stmt.setLong(5, user.getId());
            
            return stmt.executeUpdate() > 0;
        }
    }
} 