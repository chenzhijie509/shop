<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>我的订单</title>
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
        .orders {
            background: white;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
        }
        table {
            width: 100%;
            border-collapse: collapse;
        }
        th, td {
            padding: 12px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }
        th {
            background: #f5f5f5;
            font-weight: bold;
        }
        .btn {
            padding: 5px 10px;
            border: none;
            border-radius: 3px;
            cursor: pointer;
            color: white;
            text-decoration: none;
            margin-right: 5px;
        }
        .btn-cancel { background: #f44336; }
        .btn-confirm { background: #4CAF50; }
        .status-badge {
            padding: 3px 8px;
            border-radius: 3px;
            color: white;
            font-size: 12px;
        }
        .status-1 { background: #FF9800; } /* 待付款 */
        .status-2 { background: #2196F3; } /* 待发货 */
        .status-3 { background: #9C27B0; } /* 待收货 */
        .status-4 { background: #4CAF50; } /* 已完成 */
        .status-5 { background: #9E9E9E; } /* 已取消 */
        .btn-review { 
            background: #FF8C00; 
            padding: 5px 10px;
            border: none;
            border-radius: 3px;
            cursor: pointer;
            color: white;
            text-decoration: none;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="menu">
            <a href="${pageContext.request.contextPath}/customer/products">商品列表</a>
            <a href="${pageContext.request.contextPath}/customer/orders">我的订单</a>
            <a href="${pageContext.request.contextPath}/customer/profile">个人信息</a>
            <a href="${pageContext.request.contextPath}/cart">购物车</a>
        </div>
        
        <div class="orders">
            <h2>我的订单</h2>
            
            <c:if test="${not empty success}">
                <div class="success">${success}</div>
            </c:if>
            <c:if test="${not empty error}">
                <div class="error">${error}</div>
            </c:if>
            
            <table>
                <tr>
                    <th>订单号</th>
                    <th>下单时间</th>
                    <th>订单金额</th>
                    <th>订单状态</th>
                    <th>操作</th>
                    <th>评价</th>
                </tr>
                <c:forEach items="${orders}" var="order">
                    <tr>
                        <td>${order.orderNo}</td>
                        <td><fmt:formatDate value="${order.createdAt}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
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
                        <td>
                            <c:if test="${order.status == 1}">
                                <a href="${pageContext.request.contextPath}/customer/order/cancel?id=${order.id}" 
                                   class="btn btn-cancel"
                                   onclick="return confirm('确认取消订单？')">取消订单</a>
                            </c:if>
                            <c:if test="${order.status == 3}">
                                <form action="${pageContext.request.contextPath}/customer/order/confirm" method="post" style="display:inline;">
                                    <input type="hidden" name="id" value="${order.id}">
                                    <button type="submit" class="btn btn-confirm"
                                            onclick="return confirm('确认收到商品？')">确认收货</button>
                                </form>
                            </c:if>
                            
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${order.status == 4 && !order.review}">
                                    <a href="${pageContext.request.contextPath}/customer/review?orderId=${order.id}" 
                                       class="btn btn-review">评价</a>
                                </c:when>
                                <c:when test="${order.status == 4 && order.review}">
                                    <span style="color: #999;">已评价</span>
                                </c:when>
                                <c:otherwise>
                                    <span style="color: #999;">-</span>
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>
</body>
</html> 