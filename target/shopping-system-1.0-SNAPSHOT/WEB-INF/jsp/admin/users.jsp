<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>用户管理</title>
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
        <h1>用户管理</h1>
        <table>
            <tr>
                <th>ID</th>
                <th>用户名</th>
                <th>邮箱</th>
                <th>状态</th>
                <th>操作</th>
            </tr>
            <c:forEach items="${users}" var="user">
                <tr>
                    <td>${user.id}</td>
                    <td>${user.username}</td>
                    <td>${user.email}</td>
                    <td>${user.status ? '正常' : '禁用'}</td>
                    <td>
                        <form action="${pageContext.request.contextPath}/admin/user/status" method="post" style="display:inline;">
                            <input type="hidden" name="userId" value="${user.id}">
                            <input type="hidden" name="status" value="${!user.status}">
                            <button type="submit" class="btn ${user.status ? 'btn-disable' : 'btn-enable'}">
                                ${user.status ? '禁用' : '启用'}
                            </button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
</body>
</html> 