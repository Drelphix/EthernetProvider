<%@ page contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Login</title>
    <link href="https://use.fontawesome.com/releases/v5.15.1/css/all.css" rel="stylesheet">
    <link href="../css/style.css" rel="stylesheet"/>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
</head>
<body>
<c:if test="${sessionScope.username != null}">
    <script type="text/javascript">
    setTimeout(function Redirect(){
        window.location="/controller?command=tariff_list";
        }, 0);



    </script>
</c:if>
<form action="/controller" id="signup" method="post">
    <input name="command" type="hidden" value="to_sign_up">
</form>
<form action="/controller" id="back" method="post">
    <input name="command" type="hidden" value="tariff_list">
</form>
<form action="/controller" class="center" id="login" method="post">
    <div class="loginform">
        <div class="title">
            Sign in
        </div>
        <div class="input-form username">
            <input class="input" maxlength="12" name="username" placeholder="Login" type="text" type="text"
                   value="${username}">
            <i class="fas fa-user"></i>
        </div>
        <div class="input-form password">
            <input class="input" maxlength="36" name="password" placeholder="Password" type="password" type="password"
                   value="${password}">
            <i class="fas fa-key"></i>
        </div>
        <c:if test="${message != null}">
            <center><span style="color: #fff">${message}</span></center>
        </c:if>
        <div class="input-form button">
            <input name="command" type="hidden" value="sign_in">
            <center><a class="btnl" onclick="document.getElementById('login').submit()">Sign In</a>
                <a class="btnl" onclick="document.getElementById('signup').submit()">Sign Up</a>
                <a class="btnl" onclick="document.getElementById('back').submit()">Back</a></center>
        </div>
    </div>
</form>
</body>
</html>