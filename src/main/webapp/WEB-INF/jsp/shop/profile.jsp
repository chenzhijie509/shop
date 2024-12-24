<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>店铺管理 - 店铺信息</title>
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
        <h1>店铺信息</h1>
        
        <div class="menu">
            <a href="${pageContext.request.contextPath}/shop/dashboard">仪表盘</a>
            <a href="${pageContext.request.contextPath}/shop/products">商品管理</a>
            <a href="${pageContext.request.contextPath}/shop/orders">订单管理</a>
            <a href="${pageContext.request.contextPath}/shop/profile">店铺信息</a>
        </div>
        
        <div class="profile-form">
            <c:if test="${not empty success}">
                <div class="success">${success}</div>
            </c:if>
            <c:if test="${not empty error}">
                <div class="error">${error}</div>
            </c:if>
            
            <form action="${pageContext.request.contextPath}/shop/profile/update" method="post">
                <div class="form-group">
                    <label>店铺名称：</label>
                    <input type="text" value="${shop.username}" readonly>
                </div>
                <div class="form-group">
                    <label>店铺邮箱：</label>
                    <input type="email" name="email" value="${shop.email}" required>
                </div>
                <div class="form-group">
                    <label>联系人：</label>
                    <input type="text" name="realName" value="${shop.realName}" required>
                </div>
                <div class="form-group">
                    <label>联系电话：</label>
                    <input type="tel" name="phone" value="${shop.phone}" required>
                </div>
                <div class="form-group">
                    <label>店铺地址：</label>
                    <input type="text" name="address" value="${shop.address}" required>
                </div>
                <button type="submit" class="btn">保存修改</button>
            </form>
        </div>
    </div>
</body>
</html> 