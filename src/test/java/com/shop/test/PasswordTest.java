package com.shop.test;

import com.shop.util.PasswordUtil;

public class PasswordTest {
    public static void main(String[] args) throws Exception {
        String password = "admin123";
        String encrypted = PasswordUtil.encrypt(password);
        System.out.println("加密后的密码: " + encrypted);
        
        // 验证数据库中的密码
        String dbPassword = "HUBGxyJHmH+UjZMR9KZ0j4DyuR8YPKAkIeqwqHdWXAw=";
        boolean isMatch = encrypted.equals(dbPassword);
        System.out.println("密码是否匹配: " + isMatch);
    }
} 