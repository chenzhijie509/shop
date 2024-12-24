package com.shop.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class BaseDao {
    protected Connection getConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/shop_db?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai",
            "root",
            "root"
        );
    }
} 