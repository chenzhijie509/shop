package com.shop.dao;

import com.shop.model.Order;
import com.shop.model.OrderItem;
import com.shop.model.CartItem;
import com.shop.model.Product;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

public class OrderDao extends BaseDao {
    
    public List<Order> findByShopId(Long shopId) throws Exception {
        String sql = "SELECT * FROM orders WHERE shop_id = ? ORDER BY created_at DESC";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, shopId);
            ResultSet rs = stmt.executeQuery();
            List<Order> orders = new ArrayList<>();
            
            while (rs.next()) {
                Order order = new Order();
                order.setId(rs.getLong("id"));
                order.setOrderNo(rs.getString("order_no"));
                order.setUserId(rs.getLong("user_id"));
                order.setShopId(rs.getLong("shop_id"));
                order.setTotalAmount(rs.getBigDecimal("total_amount"));
                order.setStatus(rs.getInt("status"));
                order.setAddress(rs.getString("address"));
                order.setPhone(rs.getString("phone"));
                order.setReceiverName(rs.getString("receiver_name"));
                order.setCreatedAt(rs.getTimestamp("created_at"));
                order.setUpdatedAt(rs.getTimestamp("updated_at"));
                orders.add(order);
            }
            
            return orders;
        }
    }
    
    public boolean updateStatus(Long id, Long shopId, Integer status) throws Exception {
        String sql = "UPDATE orders SET status = ?, updated_at = NOW() " +
                    "WHERE id = ? AND shop_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, status);
            stmt.setLong(2, id);
            stmt.setLong(3, shopId);
            
            return stmt.executeUpdate() > 0;
        }
    }
    
    public int countByShopId(Long shopId) throws Exception {
        String sql = "SELECT COUNT(*) FROM orders WHERE shop_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, shopId);
            ResultSet rs = stmt.executeQuery();
            
            return rs.next() ? rs.getInt(1) : 0;
        }
    }
    
    public List<Order> findRecentByShopId(Long shopId, int limit) throws Exception {
        String sql = "SELECT * FROM orders WHERE shop_id = ? ORDER BY created_at DESC LIMIT ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, shopId);
            stmt.setInt(2, limit);
            ResultSet rs = stmt.executeQuery();
            List<Order> orders = new ArrayList<>();
            
            while (rs.next()) {
                Order order = new Order();
                order.setId(rs.getLong("id"));
                order.setOrderNo(rs.getString("order_no"));
                order.setUserId(rs.getLong("user_id"));
                order.setShopId(rs.getLong("shop_id"));
                order.setTotalAmount(rs.getBigDecimal("total_amount"));
                order.setStatus(rs.getInt("status"));
                order.setAddress(rs.getString("address"));
                order.setPhone(rs.getString("phone"));
                order.setReceiverName(rs.getString("receiver_name"));
                order.setCreatedAt(rs.getTimestamp("created_at"));
                order.setUpdatedAt(rs.getTimestamp("updated_at"));
                orders.add(order);
            }
            
            return orders;
        }
    }
    
    public List<Order> findByUserId(Long userId) throws Exception {
        String sql = "SELECT o.*, oi.product_id FROM orders o " +
                    "LEFT JOIN order_items oi ON o.id = oi.order_id " +
                    "WHERE o.user_id = ? " +
                    "ORDER BY o.created_at DESC";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, userId);
            ResultSet rs = stmt.executeQuery();
            List<Order> orders = new ArrayList<>();
            
            while (rs.next()) {
                Order order = new Order();
                order.setId(rs.getLong("id"));
                order.setOrderNo(rs.getString("order_no"));
                order.setUserId(rs.getLong("user_id"));
                order.setShopId(rs.getLong("shop_id"));
                order.setTotalAmount(rs.getBigDecimal("total_amount"));
                order.setStatus(rs.getInt("status"));
                order.setAddress(rs.getString("address"));
                order.setPhone(rs.getString("phone"));
                order.setReceiverName(rs.getString("receiver_name"));
                order.setCreatedAt(rs.getTimestamp("created_at"));
                order.setUpdatedAt(rs.getTimestamp("updated_at"));
                order.setReview(rs.getBoolean("review"));
                order.setProductId(rs.getLong("product_id"));
                orders.add(order);
            }
            
            return orders;
        }
    }
    
    public Order findByIdAndUserId(Long orderId, Long userId) throws Exception {
        String sql = "SELECT o.*, oi.product_id FROM orders o " +
                    "LEFT JOIN order_items oi ON o.id = oi.order_id " +
                    "WHERE o.id = ? AND o.user_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, orderId);
            stmt.setLong(2, userId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Order order = new Order();
                order.setId(rs.getLong("id"));
                order.setOrderNo(rs.getString("order_no"));
                order.setUserId(rs.getLong("user_id"));
                order.setShopId(rs.getLong("shop_id"));
                order.setTotalAmount(rs.getBigDecimal("total_amount"));
                order.setStatus(rs.getInt("status"));
                order.setAddress(rs.getString("address"));
                order.setPhone(rs.getString("phone"));
                order.setReceiverName(rs.getString("receiver_name"));
                order.setCreatedAt(rs.getTimestamp("created_at"));
                order.setUpdatedAt(rs.getTimestamp("updated_at"));
                order.setReview(rs.getBoolean("review"));
                order.setProductId(rs.getLong("product_id"));
                return order;
            }
            return null;
        }
    }
    
    public Long save(Order order, List<CartItem> cartItems) throws Exception {
        if (cartItems == null || cartItems.isEmpty()) {
            throw new Exception("购物车为空");
        }
        
        // 检查商品信息是否完整
        for (CartItem item : cartItems) {
            if (item.getProduct() == null) {
                throw new Exception("商品信息不完整");
            }
        }
        
        Connection conn = null;
        try {
            conn = getConnection();
            conn.setAutoCommit(false);  // 开启事务
            
            // 生成订单号
            order.setOrderNo(generateOrderNo());
            
            // 保存订单
            String sql = "INSERT INTO orders (order_no, user_id, shop_id, total_amount, status, " +
                        "address, phone, receiver_name, created_at, updated_at) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, NOW(), NOW())";
            
            Long orderId;
            try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, order.getOrderNo());
                stmt.setLong(2, order.getUserId());
                stmt.setLong(3, cartItems.get(0).getProduct().getShopId());
                stmt.setBigDecimal(4, order.getTotalAmount());
                stmt.setInt(5, order.getStatus());
                stmt.setString(6, order.getAddress());
                stmt.setString(7, order.getPhone());
                stmt.setString(8, order.getReceiverName());
                
                if (stmt.executeUpdate() > 0) {
                    ResultSet rs = stmt.getGeneratedKeys();
                    if (rs.next()) {
                        orderId = rs.getLong(1);
                    } else {
                        throw new Exception("获取订单ID失败");
                    }
                } else {
                    throw new Exception("创建订单失败");
                }
            }
            
            // 保存订单项
            sql = "INSERT INTO order_items (order_id, product_id, product_name, price, quantity, subtotal) " +
                  "VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                for (CartItem cartItem : cartItems) {
                    Product product = cartItem.getProduct();
                    stmt.setLong(1, orderId);
                    stmt.setLong(2, product.getId());
                    stmt.setString(3, product.getName());
                    stmt.setBigDecimal(4, product.getPrice());
                    stmt.setInt(5, cartItem.getQuantity());
                    stmt.setBigDecimal(6, cartItem.getSubtotal());
                    stmt.addBatch();
                }
                stmt.executeBatch();
            }
            
            // 更新商品库存
            sql = "UPDATE products SET stock = stock - ?, updated_at = NOW() " +
                  "WHERE id = ? AND stock >= ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                for (CartItem cartItem : cartItems) {
                    stmt.setInt(1, cartItem.getQuantity());
                    stmt.setLong(2, cartItem.getProductId());
                    stmt.setInt(3, cartItem.getQuantity());
                    if (stmt.executeUpdate() != 1) {
                        throw new Exception("商品库存不足");
                    }
                }
            }
            
            conn.commit();
            return orderId;
            
        } catch (Exception e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            throw e;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    private String generateOrderNo() {
        // 生成订单号：年月日时分秒 + 4位随机数
        return String.format("%tY%<tm%<td%<tH%<tM%<tS%04d",
                new java.util.Date(), (int)(Math.random() * 10000));
    }
    
    public boolean updatePayInfo(Order order) throws Exception {
        String sql = "UPDATE orders SET status = ?, pay_time = ?, pay_type = ?, pay_no = ? WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, order.getStatus());
            stmt.setTimestamp(2, new Timestamp(order.getPayTime().getTime()));
            stmt.setString(3, order.getPayType());
            stmt.setString(4, order.getPayNo());
            stmt.setLong(5, order.getId());
            
            return stmt.executeUpdate() > 0;
        }
    }
    
    public Order findById(Long id) throws Exception {
        String sql = "SELECT * FROM orders WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Order order = new Order();
                order.setId(rs.getLong("id"));
                order.setOrderNo(rs.getString("order_no"));
                order.setUserId(rs.getLong("user_id"));
                order.setShopId(rs.getLong("shop_id"));
                order.setTotalAmount(rs.getBigDecimal("total_amount"));
                order.setStatus(rs.getInt("status"));
                order.setAddress(rs.getString("address"));
                order.setPhone(rs.getString("phone"));
                order.setReceiverName(rs.getString("receiver_name"));
                order.setPayTime(rs.getTimestamp("pay_time"));
                order.setPayType(rs.getString("pay_type"));
                order.setPayNo(rs.getString("pay_no"));
                order.setCreatedAt(rs.getTimestamp("created_at"));
                order.setUpdatedAt(rs.getTimestamp("updated_at"));
                return order;
            }
            return null;
        }
    }
    
    public List<Order> findAll() throws Exception {
        String sql = "SELECT * FROM orders ORDER BY created_at DESC";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            ResultSet rs = stmt.executeQuery();
            List<Order> orders = new ArrayList<>();
            
            while (rs.next()) {
                Order order = new Order();
                // 设置订单属性
                order.setId(rs.getLong("id"));
                order.setOrderNo(rs.getString("order_no"));
                order.setUserId(rs.getLong("user_id"));
                order.setTotalAmount(rs.getBigDecimal("total_amount"));
                order.setStatus(rs.getInt("status"));
                order.setCreatedAt(rs.getTimestamp("created_at"));
                orders.add(order);
            }
            
            return orders;
        }
    }
    
    public int getTotalCount() throws Exception {
        String sql = "SELECT COUNT(*) FROM orders";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        }
    }
    
    public BigDecimal getTotalSales() throws Exception {
        String sql = "SELECT SUM(total_amount) FROM orders WHERE status = 4"; // 4表示已完成
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            return rs.next() ? rs.getBigDecimal(1) : BigDecimal.ZERO;
        }
    }
    
    public boolean updateStatusByUser(Long id, Long userId, Integer status) throws Exception {
        String sql = "UPDATE orders SET status = ?, updated_at = NOW() " +
                    "WHERE id = ? AND user_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, status);
            stmt.setLong(2, id);
            stmt.setLong(3, userId);
            
            return stmt.executeUpdate() > 0;
        }
    }
    
    public boolean updateReviewStatus(Long orderId, boolean reviewed) throws Exception {
        String sql = "UPDATE orders SET review = ? WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setBoolean(1, reviewed);
            stmt.setLong(2, orderId);
            
            return stmt.executeUpdate() > 0;
        }
    }
} 