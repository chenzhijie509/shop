<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>商品列表</title>
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
        .search-box {
            margin-bottom: 20px;
            padding: 20px;
            background: #f5f5f5;
            border-radius: 5px;
        }
        .search-box input[type="text"] {
            width: 300px;
            padding: 8px;
            border: 1px solid #ddd;
            border-radius: 4px;
        }
        .search-box select {
            padding: 8px;
            border: 1px solid #ddd;
            border-radius: 4px;
        }
        .search-box button {
            padding: 8px 20px;
            background: #FF4400;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        .product-grid {
            display: grid;
            grid-template-columns: repeat(4, 1fr);
            gap: 20px;
        }
        .product-card {
            background: white;
            padding: 15px;
            border-radius: 5px;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
        }
        .product-card img {
            width: 100%;
            height: 200px;
            object-fit: cover;
            border-radius: 5px;
        }
        .product-card h3 {
            margin: 10px 0;
            font-size: 16px;
        }
        .price {
            color: #FF4400;
            font-size: 18px;
            font-weight: bold;
        }
        .btn {
            display: block;
            width: 100%;
            padding: 8px;
            background: #FF4400;
            color: white;
            text-align: center;
            text-decoration: none;
            border-radius: 4px;
            margin-top: 10px;
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
        
        <div class="search-box">
            <form action="${pageContext.request.contextPath}/customer/products" method="get">
                <input type="text" name="keyword" placeholder="输入商品名称" value="${param.keyword}">
                <select name="categoryId">
                    <option value="">所有分类</option>
                    <c:forEach items="${categories}" var="category">
                        <option value="${category.id}" ${param.categoryId == category.id ? 'selected' : ''}>
                            ${category.name}
                        </option>
                    </c:forEach>
                </select>
                <button type="submit">搜索</button>
            </form>
        </div>
        
        <div class="product-grid">
            <c:forEach items="${products}" var="product">
                <div class="product-card">
                    <c:choose>
                        <c:when test="${not empty product.image}">
                            <img src="${pageContext.request.contextPath}${product.image}" 
                                 alt="${product.name}" 
                                 class="product-image"
                                 onerror="this.src='${pageContext.request.contextPath}/images/default-product.jpg'">
                        </c:when>
                        <c:otherwise>
                            <img src="${pageContext.request.contextPath}/images/default-product.jpg" 
                                 alt="默认商品图片" 
                                 class="product-image">
                        </c:otherwise>
                    </c:choose>
                    <div class="product-name">${product.name}</div>
                    <div class="product-price">￥${product.price}</div>
                    <a href="${pageContext.request.contextPath}/cart/add?productId=${product.id}" 
                       class="btn">加入购物车</a>
                </div>
            </c:forEach>
            
            <c:if test="${empty products}">
                <div class="no-products">
                    <p>暂无商品</p>
                </div>
            </c:if>
        </div>
        
        <c:if test="${not empty error}">
            <div class="error-message" style="color: red; margin: 10px 0;">
                ${error}
            </div>
        </c:if>
    </div>
</body>
</html> 