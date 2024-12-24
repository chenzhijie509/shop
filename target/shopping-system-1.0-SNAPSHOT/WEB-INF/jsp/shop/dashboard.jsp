<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>店铺管理 - 仪表盘</title>
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
        .recent-orders {
            background: white;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
        }
        table { width: 100%; border-collapse: collapse; }
        th, td { padding: 10px; text-align: left; border-bottom: 1px solid #ddd; }
        .menu {
            margin-bottom: 20px;
            padding: 10px;
            background: #f5f5f5;
            border-radius: 5px;
        }
        .menu a {
            margin-right: 20px;
            color: #333;
            text-decoration: none;
        }
        .menu a:hover { color: #FF4400; }
    </style>
</head>
<body>
    <div class="container">
        <h1>店铺管理</h1>
        
        <div class="menu">
            <a href="${pageContext.request.contextPath}/shop/dashboard">仪表盘</a>
            <a href="${pageContext.request.contextPath}/shop/products">商品管理</a>
            <a href="${pageContext.request.contextPath}/shop/orders">订单管理</a>
            <a href="${pageContext.request.contextPath}/shop/profile">店铺信息</a>
        </div>
        
        <div class="stats">
            <div class="stat-card">
                <h3>商品总数</h3>
                <p>${productCount}</p>
            </div>
            <div class="stat-card">
                <h3>订单总数</h3>
                <p>${orderCount}</p>
            </div>
        </div>
        
        <div class="recent-orders">
            <h2>最近订单</h2>
            <table>
                <tr>
                    <th>订单号</th>
                    <th>金额</th>
                    <th>状态</th>
                    <th>下单时间</th>
                </tr>
                <c:forEach items="${recentOrders}" var="order">
                    <tr>
                        <td>${order.orderNo}</td>
                        <td>￥${order.totalAmount}</td>
                        <td>
                            <c:choose>
                                <c:when test="${order.status == 1}">待付款</c:when>
                                <c:when test="${order.status == 2}">待发货</c:when>
                                <c:when test="${order.status == 3}">待收货</c:when>
                                <c:when test="${order.status == 4}">已完成</c:when>
                                <c:when test="${order.status == 5}">已取消</c:when>
                            </c:choose>
                        </td>
                        <td>${order.createdAt}</td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>
</body>
</html> 