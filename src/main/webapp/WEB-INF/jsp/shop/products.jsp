<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>店铺管理 - 商品管理</title>
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
        .add-form {
            margin-bottom: 20px;
            padding: 20px;
            background: #f5f5f5;
            border-radius: 5px;
        }
        .form-group {
            margin-bottom: 10px;
        }
        .form-group label {
            display: inline-block;
            width: 100px;
        }
        table { width: 100%; border-collapse: collapse; }
        th, td { padding: 10px; text-align: left; border: 1px solid #ddd; }
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
        .pagination {
            margin-top: 20px;
            text-align: center;
        }
        .pagination a {
            display: inline-block;
            padding: 5px 10px;
            margin: 0 5px;
            border: 1px solid #ddd;
            border-radius: 3px;
            text-decoration: none;
            color: #333;
        }
        .pagination a.active {
            background: #FF4400;
            color: white;
            border-color: #FF4400;
        }
        .product-image {
            width: 100%;
            height: 200px;
            object-fit: cover;
            border-radius: 5px;
            margin-bottom: 10px;
        }
        .product-card {
            background: white;
            padding: 15px;
            border-radius: 5px;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
            transition: transform 0.2s;
        }
        .product-card:hover {
            transform: translateY(-5px);
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>商品管理</h1>
        
        <div class="menu">
            <a href="${pageContext.request.contextPath}/shop/dashboard">仪表盘</a>
            <a href="${pageContext.request.contextPath}/shop/products">商品管理</a>
            <a href="${pageContext.request.contextPath}/shop/orders">订单管理</a>
            <a href="${pageContext.request.contextPath}/shop/profile">店铺信息</a>
        </div>
        
        <div class="add-form">
            <h2>添加商品</h2>
            <form action="${pageContext.request.contextPath}/shop/product/add" method="post" enctype="multipart/form-data">
                <div class="form-group">
                    <label>商品名称：</label>
                    <input type="text" name="name" required>
                </div>
                <div class="form-group">
                    <label>商品描述：</label>
                    <textarea name="description" required></textarea>
                </div>
                <div class="form-group">
                    <label>价格：</label>
                    <input type="number" name="price" step="0.01" required>
                </div>
                <div class="form-group">
                    <label>库存：</label>
                    <input type="number" name="stock" required>
                </div>
                <div class="form-group">
                    <label>分类：</label>
                    <select name="categoryId" required>
                        <option value="">请选择分类</option>
                        <c:forEach items="${categories}" var="category">
                            <option value="${category.id}">${category.name}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <label>商品图片：</label>
                    <input type="file" name="image" accept="image/*" required>
                    <div class="preview" style="margin-top: 10px; display: none;">
                        <img id="imagePreview" style="max-width: 200px; max-height: 200px;">
                    </div>
                </div>
                <button type="submit" class="btn">添加商品</button>
            </form>
        </div>
        
        <table>
            <tr>
                <th>ID</th>
                <th>商品名称</th>
                <th>价格</th>
                <th>库存</th>
                <th>分类</th>
                <th>状态</th>
                <th>操作</th>
                <th>图片</th>
            </tr>
            <c:forEach items="${products}" var="product">
                <tr>
                    <td>${product.id}</td>
                    <td>${product.name}</td>
                    <td>￥${product.price}</td>
                    <td>${product.stock}</td>
                    <td>
                        <c:forEach items="${categories}" var="category">
                            <c:if test="${category.id == product.categoryId}">
                                ${category.name}
                            </c:if>
                        </c:forEach>
                    </td>
                    <td>${product.status ? '上架' : '下架'}</td>
                    <td>
                        <a href="${pageContext.request.contextPath}/shop/product/edit?id=${product.id}" 
                           class="btn">编辑</a>
                        <a href="${pageContext.request.contextPath}/shop/product/delete?id=${product.id}" 
                           class="btn btn-delete" 
                           onclick="return confirm('确定要删除吗？')">删除</a>
                    </td>
                    <td>
                        <c:if test="${not empty product.image}">
                            <img src="${pageContext.request.contextPath}${product.image}" 
                                 alt="${product.name}" 
                                 style="max-width: 50px; max-height: 50px;">
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
        </table>
        
        <!-- 分页导航 -->
        <div class="pagination">
            <c:if test="${currentPage > 1}">
                <a href="?page=${currentPage - 1}">上一页</a>
            </c:if>
            
            <c:forEach begin="1" end="${totalPages}" var="i">
                <a href="?page=${i}" class="${currentPage == i ? 'active' : ''}">${i}</a>
            </c:forEach>
            
            <c:if test="${currentPage < totalPages}">
                <a href="?page=${currentPage + 1}">下一页</a>
            </c:if>
        </div>
    </div>
    
    <!-- 添加图片预览的JavaScript -->
    <script>
    document.querySelector('input[type="file"]').addEventListener('change', function(e) {
        const file = e.target.files[0];
        if (file) {
            const reader = new FileReader();
            reader.onload = function(e) {
                const preview = document.getElementById('imagePreview');
                preview.src = e.target.result;
                preview.parentElement.style.display = 'block';
            }
            reader.readAsDataURL(file);
        }
    });
    </script>
</body>
</html> 