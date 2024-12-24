<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<html>
<head>
    <title>系统错误</title>
    <style>
        .container {
            width: 600px;
            margin: 100px auto;
            text-align: center;
        }
        .error-message {
            color: #666;
            margin: 20px 0;
        }
        .back-link {
            color: #FF4400;
            text-decoration: none;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>系统错误</h1>
        <div class="error-message">
            抱歉，系统出现了一些问题。请稍后重试。
        </div>
        <a href="${pageContext.request.contextPath}/" class="back-link">返回首页</a>
    </div>
</body>
</html> 