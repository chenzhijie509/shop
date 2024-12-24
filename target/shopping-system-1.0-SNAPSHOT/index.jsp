<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>购物商城</title>
    <style>
        .container { width: 1200px; margin: 0 auto; }
        .header {
            padding: 20px 0;
            background: #fff;
            border-bottom: 1px solid #ddd;
        }
        .nav {
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        .logo {
            font-size: 24px;
            font-weight: bold;
            color: #FF4400;
        }
        .search-box {
            display: flex;
            gap: 10px;
        }
        .search-input {
            width: 400px;
            padding: 8px;
            border: 2px solid #FF4400;
            border-radius: 4px;
        }
        .search-btn {
            padding: 8px 20px;
            background: #FF4400;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        .user-menu {
            display: flex;
            gap: 20px;
            align-items: center;
        }
        .user-menu a {
            color: #666;
            text-decoration: none;
        }
        .user-menu a:hover {
            color: #FF4400;
        }
        .main {
            padding: 20px 0;
        }
        .category-menu {
            margin-bottom: 20px;
            padding: 10px;
            background: #f5f5f5;
            border-radius: 5px;
        }
        .category-menu a {
            margin-right: 20px;
            color: #333;
            text-decoration: none;
        }
        .product-grid {
            display: grid;
            grid-template-columns: repeat(4, 1fr);
            gap: 20px;
            margin-top: 20px;
        }
        .product-card {
            background: white;
            padding: 15px;
            border-radius: 5px;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
            text-align: center;
        }
        .product-card img {
            width: 100%;
            height: 200px;
            object-fit: cover;
            border-radius: 5px;
            margin-bottom: 10px;
        }
        .product-card h3 {
            margin: 10px 0;
            font-size: 16px;
        }
        .product-card .price {
            color: #FF4400;
            font-size: 18px;
            font-weight: bold;
            margin: 10px 0;
        }
        .product-card .btn {
            display: block;
            padding: 8px;
            background: #FF4400;
            color: white;
            text-decoration: none;
            border-radius: 4px;
        }
        .product-image {
            width: 100%;
            height: 200px;
            object-fit: cover;
            border-radius: 5px;
            margin-bottom: 10px;
        }
        .product-name {
            margin: 10px 0;
            font-size: 16px;
        }
        .product-price {
            color: #FF4400;
            font-size: 18px;
            font-weight: bold;
            margin: 10px 0;
        }
        .pagination {
            margin: 20px 0;
            text-align: center;
        }
        .page-link {
            display: inline-block;
            padding: 8px 12px;
            margin: 0 4px;
            border: 1px solid #ddd;
            border-radius: 4px;
            text-decoration: none;
            color: #333;
        }
        .page-link:hover {
            background: #f5f5f5;
        }
        .page-link.active {
            background: #FF4400;
            color: white;
            border-color: #FF4400;
        }
        .product-link {
            text-decoration: none;
            color: inherit;
            display: block;
        }
        .product-link:hover {
            opacity: 0.8;
        }
        .product-card img {
            transition: transform 0.2s;
        }
        .product-card:hover img {
            transform: scale(1.05);
        }
    </style>
</head>
<body>
    <div class="header">
        <div class="container">
            <div class="nav">
                <div class="logo">购物商城</div>
                <div class="search-box">
                    <form action="${pageContext.request.contextPath}/customer/products" method="get">
                        <input type="text" name="keyword" class="search-input" placeholder="请输入商品名称">
                        <button type="submit" class="search-btn">搜索</button>
                    </form>
                </div>
                <div class="user-menu">
                    <c:choose>
                        <c:when test="${empty sessionScope.user}">
                            <a href="${pageContext.request.contextPath}/user/login">用户登录</a>
                            <a href="${pageContext.request.contextPath}/shop/login">店铺登录</a>
                        </c:when>
                        <c:otherwise>
                            <span>欢迎，${sessionScope.user.username}</span>
                            <c:choose>
                                <c:when test="${sessionScope.user.userType == 3}">
                                    <!-- 顾客菜单 -->
                                    <a href="${pageContext.request.contextPath}/customer/products">商品列表</a>
                                    <a href="${pageContext.request.contextPath}/customer/orders">我的订单</a>
                                    <a href="${pageContext.request.contextPath}/cart">购物车</a>
                                    <a href="${pageContext.request.contextPath}/customer/profile">个人信息</a>
                                </c:when>
                                <c:when test="${sessionScope.user.userType == 2}">
                                    <!-- 店铺菜单 -->
                                    <a href="${pageContext.request.contextPath}/shop/">店铺管理</a>
                                </c:when>
                                <c:when test="${sessionScope.user.userType == 1}">
                                    <!-- 管理员菜单 -->
                                    <a href="${pageContext.request.contextPath}/admin/">后台管理</a>
                                </c:when>
                            </c:choose>
                            <a href="${pageContext.request.contextPath}/user/logout">退出</a>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </div>

    <div class="main">
        <div class="container">
            <div class="category-menu">
                <a href="${pageContext.request.contextPath}/customer/products">全部商品</a>
                <c:forEach items="${categories}" var="category">
                    <a href="${pageContext.request.contextPath}/customer/products?categoryId=${category.id}">
                        ${category.name}
                    </a>
                </c:forEach>
            </div>
            
            <!-- 商品展示区域 -->
            <div class="product-grid">
                <c:forEach items="${products}" var="product">
                    <div class="product-card">
                        <a href="${pageContext.request.contextPath}/product/detail?id=${product.id}" class="product-link">
                            <c:choose>
                                <c:when test="${not empty product.image}">
                                    <img src="${pageContext.request.contextPath}${product.image}" 
                                         alt="${product.name}" 
                                         class="product-image">
                                </c:when>
                                <c:otherwise>
                                    <img src="${pageContext.request.contextPath}/images/default-product.jpg" 
                                         alt="默认商品图片" 
                                         class="product-image">
                                </c:otherwise>
                            </c:choose>
                            <div class="product-name">${product.name}</div>
                            <div class="product-price">￥${product.price}</div>
                        </a>
                        <a href="${pageContext.request.contextPath}/cart/add?productId=${product.id}" 
                           class="btn">加入购物车</a>
                    </div>
                </c:forEach>
            </div>

            <!-- 在商品展示区域后面添加分页导航 -->
            <div class="pagination" style="margin-top: 20px; text-align: center;">
                <c:if test="${currentPage > 1}">
                    <a href="?page=${currentPage - 1}" class="page-link">上一页</a>
                </c:if>
                
                <c:forEach begin="1" end="${totalPages}" var="i">
                    <a href="?page=${i}" 
                       class="page-link ${currentPage == i ? 'active' : ''}">${i}</a>
                </c:forEach>
                
                <c:if test="${currentPage < totalPages}">
                    <a href="?page=${currentPage + 1}" class="page-link">下一页</a>
                </c:if>
            </div>
        </div>
    </div>
</body>
</html> 