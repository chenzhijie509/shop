<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>订单结算</title>
    <style>
        .container { width: 1200px; margin: 0 auto; padding: 20px; }
        .checkout-form {
            background: white;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
            margin-bottom: 20px;
        }
        .form-group {
            margin-bottom: 15px;
        }
        .form-group label {
            display: inline-block;
            width: 100px;
        }
        .form-group input {
            width: 300px;
            padding: 8px;
            border: 1px solid #ddd;
            border-radius: 4px;
        }
        .order-items {
            margin-bottom: 20px;
        }
        table {
            width: 100%;
            border-collapse: collapse;
        }
        th, td {
            padding: 10px;
            text-align: left;
            border-bottom: 1px solid #ddd;
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
        .btn {
            display: inline-block;
            padding: 10px 20px;
            background: #FF4400;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            text-decoration: none;
        }
        .error {
            color: red;
            margin-bottom: 10px;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>订单结算</h2>
        
        <div class="order-items">
            <h3>订单商品</h3>
            <table>
                <tr>
                    <th>商品名称</th>
                    <th>单价</th>
                    <th>数量</th>
                    <th>小计</th>
                </tr>
                <c:forEach items="${items}" var="item">
                    <tr>
                        <td>${item.product.name}</td>
                        <td>￥${item.product.price}</td>
                        <td>${item.quantity}</td>
                        <td>￥${item.subtotal}</td>
                    </tr>
                </c:forEach>
            </table>
            
            <div class="total">
                总计：<span>￥${total}</span>
            </div>
        </div>
        
        <div class="checkout-form">
            <h3>收货信息</h3>
            <c:if test="${not empty error}">
                <div class="error">${error}</div>
            </c:if>
            
            <form action="${pageContext.request.contextPath}/order/submit" method="post">
                <div class="form-group">
                    <label>收货人：</label>
                    <input type="text" name="receiverName" value="${sessionScope.user.realName}" required>
                </div>
                <div class="form-group">
                    <label>联系电话：</label>
                    <input type="tel" name="phone" value="${sessionScope.user.phone}" required>
                </div>
                <div class="form-group">
                    <label>收货地址：</label>
                    <input type="text" name="address" value="${sessionScope.user.address}" required>
                </div>
                <button type="submit" class="btn">提交订单</button>
            </form>
        </div>
    </div>
</body>
</html> 