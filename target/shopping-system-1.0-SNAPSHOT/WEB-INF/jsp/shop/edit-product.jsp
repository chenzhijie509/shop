<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>编辑商品</title>
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
        .edit-form {
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
        .form-group input[type="text"],
        .form-group input[type="number"],
        .form-group textarea,
        .form-group select {
            width: 300px;
            padding: 8px;
            border: 1px solid #ddd;
            border-radius: 4px;
        }
        .form-group textarea {
            height: 100px;
        }
        .preview {
            margin: 10px 0 10px 110px;
        }
        .btn {
            padding: 8px 20px;
            background: #FF4400;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            margin-left: 110px;
        }
        .current-image {
            max-width: 200px;
            max-height: 200px;
            margin: 10px 0;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>编辑商品</h1>
        
        <div class="menu">
            <a href="${pageContext.request.contextPath}/shop/dashboard">仪表盘</a>
            <a href="${pageContext.request.contextPath}/shop/products">商品管理</a>
            <a href="${pageContext.request.contextPath}/shop/orders">订单管理</a>
            <a href="${pageContext.request.contextPath}/shop/profile">店铺信息</a>
        </div>

        <div class="edit-form">
            <form action="${pageContext.request.contextPath}/shop/product/update" method="post" enctype="multipart/form-data">
                <input type="hidden" name="id" value="${product.id}">
                
                <div class="form-group">
                    <label>商品名称：</label>
                    <input type="text" name="name" value="${product.name}" required>
                </div>
                
                <div class="form-group">
                    <label>商品描述：</label>
                    <textarea name="description" required>${product.description}</textarea>
                </div>
                
                <div class="form-group">
                    <label>价格：</label>
                    <input type="number" name="price" step="0.01" value="${product.price}" required>
                </div>
                
                <div class="form-group">
                    <label>库存：</label>
                    <input type="number" name="stock" value="${product.stock}" required>
                </div>
                
                <div class="form-group">
                    <label>分类：</label>
                    <select name="categoryId" required>
                        <c:forEach items="${categories}" var="category">
                            <option value="${category.id}" ${category.id == product.categoryId ? 'selected' : ''}>
                                ${category.name}
                            </option>
                        </c:forEach>
                    </select>
                </div>
                
                <div class="form-group">
                    <label>商品状态：</label>
                    <select name="status">
                        <option value="true" ${product.status ? 'selected' : ''}>上架</option>
                        <option value="false" ${!product.status ? 'selected' : ''}>下架</option>
                    </select>
                </div>
                
                <div class="form-group">
                    <label>商品图片：</label>
                    <input type="file" name="image" accept="image/*">
                    <c:if test="${not empty product.image}">
                        <div class="preview">
                            <p>当前图片：</p>
                            <img src="${pageContext.request.contextPath}${product.image}" 
                                 alt="当前商品图片" 
                                 class="current-image">
                        </div>
                    </c:if>
                    <div class="preview" style="display: none;">
                        <p>新图片预览：</p>
                        <img id="imagePreview" style="max-width: 200px; max-height: 200px;">
                    </div>
                </div>
                
                <button type="submit" class="btn">保存修改</button>
            </form>
        </div>
    </div>
    
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