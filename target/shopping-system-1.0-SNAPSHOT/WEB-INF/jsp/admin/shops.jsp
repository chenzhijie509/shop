<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>店铺管理</title>
    <style>
        .container { width: 1200px; margin: 0 auto; padding: 20px; }
        table { width: 100%; border-collapse: collapse; }
        th, td { padding: 10px; border: 1px solid #ddd; }
        .btn {
            padding: 5px 10px;
            border: none;
            border-radius: 3px;
            cursor: pointer;
        }
        .btn-disable { background: #ff4444; color: white; }
        .btn-enable { background: #44aa44; color: white; }
    </style>
</head>
<body>
    <div class="container">
        <h1>店铺管理</h1>
        <table>
            <tr>
                <th>ID</th>
                <th>店铺名</th>
                <th>邮箱</th>
                <th>状态</th>
                <th>操作</th>
            </tr>
            <c:forEach items="${shops}" var="shop">
                <tr>
                    <td>${shop.id}</td>
                    <td>${shop.username}</td>
                    <td>${shop.email}</td>
                    <td>${shop.status ? '正常' : '禁用'}</td>
                    <td>
                        <form action="${pageContext.request.contextPath}/admin/shop/status" method="post" style="display:inline;">
                            <input type="hidden" name="shopId" value="${shop.id}">
                            <input type="hidden" name="status" value="${!shop.status}">
                            <button type="submit" class="btn ${shop.status ? 'btn-disable' : 'btn-enable'}">
                                ${shop.status ? '禁用' : '启用'}
                            </button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
</body>
</html> 