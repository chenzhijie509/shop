<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>订单支付</title>
    <style>
        .container { width: 1200px; margin: 0 auto; padding: 20px; }
        .pay-info {
            background: white;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
            margin-bottom: 20px;
        }
        .order-info {
            margin-bottom: 20px;
            padding: 15px;
            background: #f9f9f9;
            border-radius: 5px;
        }
        .pay-methods {
            display: flex;
            gap: 20px;
            margin: 20px 0;
        }
        .pay-method {
            flex: 1;
            padding: 20px;
            border: 2px solid #ddd;
            border-radius: 5px;
            text-align: center;
            cursor: pointer;
        }
        .pay-method.selected {
            border-color: #FF4400;
        }
        .pay-method img {
            width: 120px;
            height: 40px;
            object-fit: contain;
        }
        .amount {
            font-size: 24px;
            color: #FF4400;
            font-weight: bold;
            margin: 20px 0;
        }
        .btn {
            display: inline-block;
            padding: 10px 30px;
            background: #FF4400;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
        }
        .error {
            color: red;
            margin-bottom: 10px;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>订单支付</h2>
        
        <div class="pay-info">
            <div class="order-info">
                <p>订单号：${order.orderNo}</p>
                <p>收货人：${order.receiverName}</p>
                <p>联系电话：${order.phone}</p>
                <p>收货地址：${order.address}</p>
            </div>
            
            <div class="amount">
                支付金额：￥${order.totalAmount}
            </div>
            
            <c:if test="${not empty error}">
                <div class="error">${error}</div>
            </c:if>
            
            <form action="${pageContext.request.contextPath}/order/pay" method="post" id="payForm">
                <input type="hidden" name="orderId" value="${order.id}">
                <input type="hidden" name="payType" id="payType" value="alipay">
                
                <div class="pay-methods">
                    <div class="pay-method selected" onclick="selectPayMethod('alipay', this)">
                        <img src="${pageContext.request.contextPath}/images/alipay.png" alt="支付宝">
                        <p>支付宝支付</p>
                    </div>
                    <div class="pay-method" onclick="selectPayMethod('wechat', this)">
                        <img src="${pageContext.request.contextPath}/images/wechat.png" alt="微信">
                        <p>微信支付</p>
                    </div>
                </div>
                
                <button type="submit" class="btn">确认支付</button>
            </form>
        </div>
    </div>
    
    <script>
    function selectPayMethod(type, element) {
        // 移除所有选中状态
        document.querySelectorAll('.pay-method').forEach(el => {
            el.classList.remove('selected');
        });
        // 添加选中状态
        element.classList.add('selected');
        // 设置支付方式
        document.getElementById('payType').value = type;
    }
    </script>
</body>
</html> 