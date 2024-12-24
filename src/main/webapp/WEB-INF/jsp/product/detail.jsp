<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>${product.name} - 商品详情</title>
    <style>
        .container { width: 1200px; margin: 0 auto; padding: 20px; }
        .product-detail {
            display: flex;
            gap: 40px;
            margin-top: 20px;
        }
        .product-image {
            flex: 0 0 400px;
        }
        .product-image img {
            width: 100%;
            height: auto;
            border-radius: 8px;
        }
        .product-info {
            flex: 1;
        }
        .product-name {
            font-size: 24px;
            margin-bottom: 20px;
        }
        .product-price {
            font-size: 28px;
            color: #FF4400;
            margin-bottom: 20px;
        }
        .product-description {
            color: #666;
            line-height: 1.6;
            margin-bottom: 20px;
        }
        .quantity {
            margin-bottom: 20px;
        }
        .quantity input {
            width: 60px;
            padding: 8px;
            text-align: center;
            margin: 0 10px;
        }
        .btn {
            display: inline-block;
            padding: 12px 30px;
            background: #FF4400;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            text-decoration: none;
            font-size: 16px;
        }
        .btn:hover {
            background: #ff5500;
        }
        .reviews-section {
            margin-top: 40px;
            padding-top: 20px;
            border-top: 1px solid #eee;
        }
        .review-stats {
            display: flex;
            align-items: center;
            margin-bottom: 20px;
        }
        .avg-rating {
            font-size: 32px;
            color: #FF4400;
            margin-right: 20px;
        }
        .rating-stars {
            color: #FFD700;
            font-size: 20px;
        }
        .review-item {
            padding: 15px 0;
            border-bottom: 1px solid #eee;
        }
        .review-header {
            display: flex;
            justify-content: space-between;
            margin-bottom: 10px;
        }
        .reviewer-name {
            color: #666;
        }
        .review-date {
            color: #999;
            font-size: 14px;
        }
        .review-rating {
            color: #FFD700;
            margin-bottom: 5px;
        }
        .review-content {
            color: #333;
            line-height: 1.6;
        }
        .no-reviews {
            text-align: center;
            color: #999;
            padding: 20px 0;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="product-detail">
            <div class="product-image">
                <c:choose>
                    <c:when test="${not empty product.image}">
                        <img src="${pageContext.request.contextPath}${product.image}" 
                             alt="${product.name}">
                    </c:when>
                    <c:otherwise>
                        <img src="${pageContext.request.contextPath}/images/default-product.jpg" 
                             alt="默认商品图片">
                    </c:otherwise>
                </c:choose>
            </div>
            
            <div class="product-info">
                <h1 class="product-name">${product.name}</h1>
                <div class="product-price">￥${product.price}</div>
                <div class="product-description">${product.description}</div>
                
                <form action="${pageContext.request.contextPath}/cart/add" method="get">
                    <input type="hidden" name="productId" value="${product.id}">
                    <div class="quantity">
                        <label>数量：</label>
                        <input type="number" name="quantity" value="1" min="1" max="${product.stock}">
                        <span>库存：${product.stock}</span>
                    </div>
                    <button type="submit" class="btn">加入购物车</button>
                </form>
            </div>
        </div>

        <div class="reviews-section">
            <h2>商品评价</h2>
            
            <div class="review-stats">
                <div class="avg-rating">
                    <fmt:formatNumber value="${avgRating}" pattern="#.0"/>
                </div>
                <div class="rating-stars">
                    <c:forEach begin="1" end="5" var="i">
                        <c:choose>
                            <c:when test="${i <= avgRating}">★</c:when>
                            <c:otherwise>☆</c:otherwise>
                        </c:choose>
                    </c:forEach>
                </div>
            </div>
            
            <div class="review-list">
                <c:forEach items="${reviews}" var="review">
                    <div class="review-item">
                        <div class="review-header">
                            <span class="reviewer-name">${review.username}</span>
                            <span class="review-date">
                                <fmt:formatDate value="${review.createdAt}" pattern="yyyy-MM-dd HH:mm"/>
                            </span>
                        </div>
                        <div class="review-rating">
                            <c:forEach begin="1" end="5" var="i">
                                <c:choose>
                                    <c:when test="${i <= review.rating}">★</c:when>
                                    <c:otherwise>☆</c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </div>
                        <div class="review-content">
                            ${review.content}
                        </div>
                    </div>
                </c:forEach>
                
                <c:if test="${empty reviews}">
                    <div class="no-reviews">
                        暂无评价
                    </div>
                </c:if>
            </div>
        </div>
    </div>
</body>
</html> 