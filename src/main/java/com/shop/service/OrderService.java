package com.shop.service;

import com.shop.dao.OrderDao;
import com.shop.dao.CartDao;
import com.shop.model.Order;
import com.shop.model.CartItem;
import java.util.List;
import java.math.BigDecimal;
import java.util.Date;

public class OrderService {
    private OrderDao orderDao = new OrderDao();
    private CartDao cartDao = new CartDao();
    
    public Long createOrder(Long userId, String address, String phone, String receiverName) throws Exception {
        // 获取购物车商品
        List<CartItem> cartItems = cartDao.findByUserId(userId);
        if (cartItems.isEmpty()) {
            return null;
        }
        
        // 计算总金额
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (CartItem item : cartItems) {
            totalAmount = totalAmount.add(item.getSubtotal());
        }
        
        // 创建订单
        Order order = new Order();
        order.setUserId(userId);
        order.setTotalAmount(totalAmount);
        order.setStatus(1); // 1:待付款
        order.setAddress(address);
        order.setPhone(phone);
        order.setReceiverName(receiverName);
        
        return orderDao.save(order, cartItems);
    }

    public boolean payOrder(Long orderId, Long userId, String payType) throws Exception {
        // 这里应该调用实际的支付接口
        // 简化处理，直接更新订单状态
        Order order = orderDao.findById(orderId);
        if (order == null || !order.getUserId().equals(userId) || order.getStatus() != 1) {
            return false;
        }
        
        order.setStatus(2); // 更新为待发货状态
        order.setPayTime(new Date());
        order.setPayType(payType);
        order.setPayNo(generatePayNo()); // 生成支付单号
        
        return orderDao.updatePayInfo(order);
    }

    private String generatePayNo() {
        // 生成支付单号，格式：PAY + 时间戳 + 4位随机数
        return "PAY" + System.currentTimeMillis() + 
               String.format("%04d", (int)(Math.random() * 10000));
    }

    public Order getOrder(Long orderId) throws Exception {
        return orderDao.findById(orderId);
    }
} 