<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>购物车</title>
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
        .cart-table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 20px;
        }
        .cart-table th, .cart-table td {
            padding: 10px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }
        .cart-table th {
            background: #f5f5f5;
        }
        .quantity-input {
            width: 60px;
            padding: 5px;
            text-align: center;
        }
        .btn {
            padding: 5px 10px;
            border: none;
            border-radius: 3px;
            cursor: pointer;
            color: white;
            text-decoration: none;
        }
        .btn-update { background: #4CAF50; }
        .btn-delete { background: #f44336; }
        .btn-clear { background: #9E9E9E; }
        .btn-checkout { 
            background: #FF4400;
            padding: 10px 20px;
            font-size: 16px;
        }
        .total {
            text-align: right;
            font-size: 18px;
            margin: 20px 0;
        }
        .total span {
            color: #FF4400;
            font-weight: bold;
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
        
        <h2>我的购物车</h2>
        
        <c:if test="${empty items}">
            <p>购物车是空的，去<a href="${pageContext.request.contextPath}/customer/products">选购商品</a>吧！</p>
        </c:if>
        
        <c:if test="${not empty items}">
            <table class="cart-table">
                <tr>
                    <th>商品图片</th>
                    <th>商品名称</th>
                    <th>单价</th>
                    <th>数量</th>
                    <th>小计</th>
                    <th>操作</th>
                </tr>
                <c:forEach items="${items}" var="item">
                    <tr>
                        <td>
                            <c:choose>
                                <c:when test="${not empty item.product.image}">
                                    <img src="${pageContext.request.contextPath}${item.product.image}" 
                                         alt="${item.product.name}" 
                                         width="50" height="50"
                                         onerror="this.src='${pageContext.request.contextPath}/images/default-product.jpg'">
                                </c:when>
                                <c:otherwise>
                                    <img src="${pageContext.request.contextPath}/images/default-product.jpg" 
                                         alt="默认商品图片" 
                                         width="50" height="50">
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>${item.product.name}</td>
                        <td>￥${item.product.price}</td>
                        <td>
                            <form action="${pageContext.request.contextPath}/cart/update" method="get" style="display:inline;">
                                <input type="hidden" name="itemId" value="${item.id}">
                                <input type="number" name="quantity" value="${item.quantity}" 
                                       min="1" max="${item.product.stock}" class="quantity-input">
                                <button type="submit" class="btn btn-update">更新</button>
                            </form>
                        </td>
                        <td>￥${item.subtotal}</td>
                        <td>
                            <a href="${pageContext.request.contextPath}/cart/delete?itemId=${item.id}" 
                               class="btn btn-delete"
                               onclick="return confirm('确定要删除吗？')">删除</a>
                        </td>
                    </tr>
                </c:forEach>
            </table>
            
            <div class="total">
                总计：<span>￥${total}</span>
            </div>
            
            <div style="text-align: right;">
                <a href="${pageContext.request.contextPath}/cart/clear" 
                   class="btn btn-clear"
                   onclick="return confirm('确定要清空购物车吗？')">清空购物车</a>
                <a href="${pageContext.request.contextPath}/order/checkout" 
                   class="btn btn-checkout">结算</a>
            </div>
        </c:if>
    </div>
</body>
</html> 