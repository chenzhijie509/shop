<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>商品管理</title>
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
        .btn {
            padding: 5px 10px;
            border: none;
            border-radius: 3px;
            cursor: pointer;
            background: #FF4400;
            color: white;
            text-decoration: none;
            margin-right: 5px;
        }
        .btn-delete { background: #ff4444; }
    </style>
</head>
<body>
    <div class="container">
        <h1>商品管理</h1>
        
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
                <th>ID</th>
                <th>商品名称</th>
                <th>价格</th>
                <th>库存</th>
                <th>所属店铺</th>
                <th>状态</th>
                <th>操作</th>
            </tr>
            <c:forEach items="${products}" var="product">
                <tr>
                    <td>${product.id}</td>
                    <td>${product.name}</td>
                    <td>￥${product.price}</td>
                    <td>${product.stock}</td>
                    <td>${product.shopId}</td>
                    <td>${product.status ? '上架' : '下架'}</td>
                    <td>
                        <a href="${pageContext.request.contextPath}/admin/product/status?id=${product.id}&status=${!product.status}" 
                           class="btn"
                           onclick="return confirm('确定要${product.status ? '下架' : '上架'}该商品吗？')">
                            ${product.status ? '下架' : '上架'}
                        </a>
                        <a href="${pageContext.request.contextPath}/admin/product/delete?id=${product.id}" 
                           class="btn btn-delete"
                           onclick="return confirm('确定要删除该商品吗？')">删除</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
</body>
</html> 