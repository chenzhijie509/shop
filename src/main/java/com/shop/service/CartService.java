package com.shop.service;

import com.shop.dao.CartDao;
import com.shop.model.CartItem;
import java.util.List;
import java.math.BigDecimal;

public class CartService {
    private CartDao cartDao = new CartDao();
    
    public List<CartItem> getCartItems(Long userId) throws Exception {
        return cartDao.findByUserId(userId);
    }
    
    public boolean addToCart(Long userId, Long productId, Integer quantity) throws Exception {
        CartItem item = new CartItem();
        item.setUserId(userId);
        item.setProductId(productId);
        item.setQuantity(quantity);
        return cartDao.addItem(item);
    }
    
    public boolean updateQuantity(Long itemId, Integer quantity) throws Exception {
        return cartDao.updateQuantity(itemId, quantity);
    }
    
    public boolean removeFromCart(Long itemId) throws Exception {
        return cartDao.deleteItem(itemId);
    }
    
    public boolean clearCart(Long userId) throws Exception {
        return cartDao.clearCart(userId);
    }
    
    // 计算购物车总金额
    public BigDecimal calculateTotal(List<CartItem> items) {
        BigDecimal total = BigDecimal.ZERO;
        for (CartItem item : items) {
            total = total.add(item.getSubtotal());
        }
        return total;
    }
} 