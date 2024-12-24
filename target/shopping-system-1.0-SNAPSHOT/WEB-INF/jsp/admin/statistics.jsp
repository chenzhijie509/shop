<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>数据统计</title>
    <style>
        .container { width: 1200px; margin: 0 auto; padding: 20px; }
        .stats-grid {
            display: grid;
            grid-template-columns: repeat(2, 1fr);
            gap: 20px;
            margin-bottom: 20px;
        }
        .stat-card {
            background: white;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
            text-align: center;
        }
        .stat-value {
            font-size: 24px;
            font-weight: bold;
            color: #FF4400;
            margin: 10px 0;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>数据统计</h1>
        
        <div class="stats-grid">
            <div class="stat-card">
                <h3>用户总数</h3>
                <div class="stat-value">${stats.totalUsers}</div>
            </div>
            <div class="stat-card">
                <h3>店铺总数</h3>
                <div class="stat-value">${stats.totalShops}</div>
            </div>
            <div class="stat-card">
                <h3>订单总数</h3>
                <div class="stat-value">${stats.totalOrders}</div>
            </div>
            <div class="stat-card">
                <h3>销售总额</h3>
                <div class="stat-value">￥${stats.totalSales}</div>
            </div>
        </div>
    </div>
</body>
</html> 