<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>个人信息</title>
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
        .profile-form {
            background: white;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
            max-width: 600px;
            margin: 0 auto;
        }
        .form-group {
            margin-bottom: 15px;
        }
        .form-group label {
            display: inline-block;
            width: 100px;
            text-align: right;
            margin-right: 10px;
        }
        .form-group input {
            width: 300px;
            padding: 8px;
            border: 1px solid #ddd;
            border-radius: 4px;
        }
        .btn {
            padding: 10px 20px;
            background: #FF4400;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            margin-left: 110px;
        }
        .success {
            color: green;
            margin: 10px 0;
            text-align: center;
        }
        .error {
            color: red;
            margin: 10px 0;
            text-align: center;
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
        
        <div class="profile-form">
            <h2>个人信息</h2>
            
            <c:if test="${not empty success}">
                <div class="success">${success}</div>
            </c:if>
            <c:if test="${not empty error}">
                <div class="error">${error}</div>
            </c:if>
            
            <form action="${pageContext.request.contextPath}/customer/profile/update" method="post">
                <div class="form-group">
                    <label>用户名：</label>
                    <input type="text" value="${customer.username}" readonly>
                </div>
                <div class="form-group">
                    <label>邮箱：</label>
                    <input type="email" name="email" value="${customer.email}" required>
                </div>
                <div class="form-group">
                    <label>真实姓名：</label>
                    <input type="text" name="realName" value="${customer.realName}" required>
                </div>
                <div class="form-group">
                    <label>联系电话：</label>
                    <input type="tel" name="phone" value="${customer.phone}" required>
                </div>
                <div class="form-group">
                    <label>收货地址：</label>
                    <input type="text" name="address" value="${customer.address}" required>
                </div>
                <button type="submit" class="btn">保存修改</button>
            </form>
        </div>
    </div>
</body>
</html> 