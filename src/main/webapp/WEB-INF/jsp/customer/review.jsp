<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>订单评价</title>
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
        .review-form {
            background: white;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
        }
        .order-info {
            margin-bottom: 20px;
            padding: 15px;
            background: #f9f9f9;
            border-radius: 5px;
        }
        .form-group {
            margin-bottom: 15px;
        }
        .form-group label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
        }
        .rating {
            display: flex;
            flex-direction: row-reverse;
            gap: 10px;
            margin-bottom: 10px;
        }
        .rating input {
            display: none;
        }
        .rating label {
            cursor: pointer;
            font-size: 24px;
            color: #ddd;
        }
        .rating label:before {
            content: '★';
        }
        .rating input:checked ~ label {
            color: #ffb700;
        }
        .rating label:hover,
        .rating label:hover ~ label {
            color: #ffb700;
        }
        textarea {
            width: 100%;
            height: 150px;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
            resize: vertical;
        }
        .btn {
            padding: 10px 20px;
            background: #FF4400;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
        }
        .error {
            color: red;
            margin-bottom: 10px;
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
        
        <div class="review-form">
            <h2>订单评价</h2>
            
            <div class="order-info">
                <p>订单号：${order.orderNo}</p>
                <p>商品名称：${product.name}</p>
                <p>订单金额：￥${order.totalAmount}</p>
            </div>
            
            <c:if test="${not empty error}">
                <div class="error">${error}</div>
            </c:if>
            
            <form action="${pageContext.request.contextPath}/customer/review/submit" method="post">
                <input type="hidden" name="orderId" value="${order.id}">
                <input type="hidden" name="productId" value="${product.id}">
                
                <div class="form-group">
                    <label>评分：</label>
                    <div class="rating">
                        <input type="radio" id="star5" name="rating" value="5" required>
                        <label for="star5"></label>
                        <input type="radio" id="star4" name="rating" value="4">
                        <label for="star4"></label>
                        <input type="radio" id="star3" name="rating" value="3">
                        <label for="star3"></label>
                        <input type="radio" id="star2" name="rating" value="2">
                        <label for="star2"></label>
                        <input type="radio" id="star1" name="rating" value="1">
                        <label for="star1"></label>
                    </div>
                </div>
                
                <div class="form-group">
                    <label>评价内容：</label>
                    <textarea name="content" required 
                              placeholder="请分享您对商品的使用体验..."></textarea>
                </div>
                
                <button type="submit" class="btn">提交评价</button>
            </form>
        </div>
    </div>
</body>
</html> 