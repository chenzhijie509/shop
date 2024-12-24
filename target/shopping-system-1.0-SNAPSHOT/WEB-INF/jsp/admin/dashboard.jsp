<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>运营商管理后台</title>
    <style>
        .container { width: 1200px; margin: 0 auto; padding: 20px; }
        .stats { display: flex; gap: 20px; margin-bottom: 20px; }
        .stat-card {
            flex: 1;
            padding: 20px;
            background: #f5f5f5;
            border-radius: 5px;
            text-align: center;
        }
        .menu { margin-bottom: 20px; }
        .menu a {
            display: inline-block;
            padding: 10px 20px;
            background: #FF4400;
            color: white;
            text-decoration: none;
            border-radius: 5px;
            margin-right: 10px;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>运营商管理后台</h1>
        
        <div class="stats">
            <div class="stat-card">
                <h3>用户总数</h3>
                <p>${userCount}</p>
            </div>
            <div class="stat-card">
                <h3>店铺总数</h3>
                <p>${shopCount}</p>
            </div>
        </div>
        
        <div class="menu">
            <a href="${pageContext.request.contextPath}/admin/users">用户管理</a>
            <a href="${pageContext.request.contextPath}/admin/shops">店铺管理</a>
            <a href="${pageContext.request.contextPath}/admin/categories">分类管理</a>
            <a href="${pageContext.request.contextPath}/admin/products">商品管理</a>
            <a href="${pageContext.request.contextPath}/admin/orders">订单管理</a>
            <a href="${pageContext.request.contextPath}/admin/statistics">数据统计</a>
        </div>
    </div>
</body>
</html> 