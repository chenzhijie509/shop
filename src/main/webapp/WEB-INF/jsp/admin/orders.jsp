<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>订单管理</title>
    <style>
        .container { width: 1200px; margin: 0 auto; padding: 20px; }
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
        table { width: 100%; border-collapse: collapse; }
        th, td { padding: 10px; border: 1px solid #ddd; }
        .status-badge {
            padding: 3px 8px;
            border-radius: 3px;
            color: white;
            font-size: 12px;
        }
        .status-1 { background: #ff9800; } /* 待付款 */
        .status-2 { background: #2196F3; } /* 待发货 */
        .status-3 { background: #4CAF50; } /* 待收货 */
        .status-4 { background: #9E9E9E; } /* 已完成 */
        .status-5 { background: #f44336; } /* 已取消 */
    </style>
</head>
<body>
    <div class="container">
        <h1>订单管理</h1>
        
        <div class="menu">
            <a href="${pageContext.request.contextPath}/admin/dashboard">仪表盘</a>
            <a href="${pageContext.request.contextPath}/admin/users">用户管理</a>
            <a href="${pageContext.request.contextPath}/admin/shops">店铺管理</a>
            <a href="${pageContext.request.contextPath}/admin/categories">分类管理</a>
            <a href="${pageContext.request.contextPath}/admin/products">商品管理</a>
            <a href="${pageContext.request.contextPath}/admin/orders">订单管理</a>
            <a href="${pageContext.request.contextPath}/admin/statistics">数据统计</a>
        </div>

        <table>
            <tr>
                <th>订单号</th>
                <th>用户ID</th>
                <th>店铺ID</th>
                <th>金额</th>
                <th>状态</th>
                <th>收货人</th>
                <th>联系电话</th>
                <th>下单时间</th>
            </tr>
            <c:forEach items="${orders}" var="order">
                <tr>
                    <td>${order.orderNo}</td>
                    <td>${order.userId}</td>
                    <td>${order.shopId}</td>
                    <td>￥${order.totalAmount}</td>
                    <td>
                        <span class="status-badge status-${order.status}">
                            <c:choose>
                                <c:when test="${order.status == 1}">待付款</c:when>
                                <c:when test="${order.status == 2}">待发货</c:when>
                                <c:when test="${order.status == 3}">待收货</c:when>
                                <c:when test="${order.status == 4}">已完成</c:when>
                                <c:when test="${order.status == 5}">已取消</c:when>
                            </c:choose>
                        </span>
                    </td>
                    <td>${order.receiverName}</td>
                    <td>${order.phone}</td>
                    <td>
                        <fmt:formatDate value="${order.createdAt}" pattern="yyyy-MM-dd HH:mm:ss"/>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
</body>
</html> 