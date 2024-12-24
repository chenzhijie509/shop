<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>分类管理</title>
    <style>
        .container { width: 1200px; margin: 0 auto; padding: 20px; }
        .add-form { margin-bottom: 20px; padding: 20px; background: #f5f5f5; }
        .form-group { margin-bottom: 10px; }
        .form-group label { display: inline-block; width: 100px; }
        table { width: 100%; border-collapse: collapse; }
        th, td { padding: 10px; border: 1px solid #ddd; }
        .btn {
            padding: 5px 10px;
            border: none;
            border-radius: 3px;
            cursor: pointer;
            background: #FF4400;
            color: white;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>分类管理</h1>
        
        <div class="add-form">
            <h2>添加分类</h2>
            <form action="${pageContext.request.contextPath}/admin/category/add" method="post">
                <div class="form-group">
                    <label>分类名称：</label>
                    <input type="text" name="name" required>
                </div>
                <div class="form-group">
                    <label>父级分类：</label>
                    <select name="parentId">
                        <option value="0">无</option>
                        <c:forEach items="${categories}" var="cat">
                            <c:if test="${cat.level == 1}">
                                <option value="${cat.id}">${cat.name}</option>
                            </c:if>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <label>层级：</label>
                    <select name="level">
                        <option value="1">一级分类</option>
                        <option value="2">二级分类</option>
                    </select>
                </div>
                <div class="form-group">
                    <label>排序：</label>
                    <input type="number" name="sortOrder" value="0">
                </div>
                <button type="submit" class="btn">添加</button>
            </form>
        </div>
        
        <table>
            <tr>
                <th>ID</th>
                <th>分类名称</th>
                <th>层级</th>
                <th>排序</th>
                <th>操作</th>
            </tr>
            <c:forEach items="${categories}" var="category">
                <tr>
                    <td>${category.id}</td>
                    <td>${category.name}</td>
                    <td>${category.level}</td>
                    <td>${category.sortOrder}</td>
                    <td>
                        <a href="${pageContext.request.contextPath}/admin/category/edit?id=${category.id}" class="btn">编辑</a>
                        <a href="${pageContext.request.contextPath}/admin/category/delete?id=${category.id}" class="btn" onclick="return confirm('确定要删除吗？')">删除</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
</body>
</html> 