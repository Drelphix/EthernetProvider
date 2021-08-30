<%@ page contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="localization.message" var="language"/>
<fmt:message key="login.form.username.placeholder" bundle="${language}" var="formUsername"/>
<fmt:message key="login.form.password.placeholder" bundle="${language}" var="formPassword"/>
<fmt:message key="login.form.email.placeholder" bundle="${language}" var="formEmail"/>
<fmt:message key="login.title" bundle="${language}" var="title"/>
<fmt:message key="login.form.button.signUp" bundle="${language}" var="buttonSignUp"/>
<fmt:message key="login.form.button.signIn" bundle="${language}" var="buttonSignIn"/>
<fmt:message key="login.form.button.forgot" bundle="${language}" var="buttonForgot"/>
<fmt:message key="login.form.button.getPassword" bundle="${language}" var="buttonGetPassword"/>

<html>
<head>
    <title>${title}</title>
    <script src="https://code.jquery.com/jquery-3.4.1.js"
            integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="
            crossorigin="anonymous"></script>
    <script src="/js/auth.js" type="text/javascript"></script>
    <link href="${pageContext.request.contextPath}/css/auth.css" rel="stylesheet"/>
</head>
<body>
<body>
<div class="mobile-screen">
    <div class="header">
        <h1>${title}</h1>
    </div>

    <div class="logo"></div>
    <c:if test="${message != null}">
        <center><span style="color: #fff">${message}</span></center>
    </c:if>
    <form id="registration-form" action="${pageContext.request.contextPath}/controller" method="post">
        <input name="command" type="hidden" value="sign_up">
        <input type="text" name="username" placeholder=${formUsername} value="${username}">
        <input type="email" name="email" placeholder=${formEmail} value="${email}">
        <input type="password" name="pass" placeholder=${formPassword} value="${password}">
        <input type="password" name="repass" placeholder="Repeat password" value="${repeatPassword}">
        <a href="#" onclick="document.getElementById('registration-form').submit()" class="login-btn" id="signup-btn">${buttonSignUp}</a>
        <a href="#" class="login-btn" id="back-btn">${buttonSignIn}</a>
    </form>

    <form id="login-form" action="${pageContext.request.contextPath}/controller" method="post">
        <input name="command" type="hidden" value="sign_in">
        <input type="text" name="username" placeholder=${formUsername} value="${username}">
        <input type="password" name="password" placeholder=${formPassword} value="${password}">
        <a href="#" onclick="document.getElementById('login-form').submit()" class="login-btn">${buttonSignIn}</a>
    </form>

    <form id="fpass-form" action="${pageContext.request.contextPath}/controller" method="post">
        <input type="text" name="forgotten" placeholder=${formEmail}>
        <input name="command" type="hidden" value="forgot_pass">
        <a href="#" onclick="document.getElementById('fpass-form').submit()" class="login-btn" id="getpass-btn">${buttonGetPassword}</a>
        <a href="#" class="login-btn" id="back-btn2">${buttonSignIn}</a>
    </form>

    <div class="other-options">
        <div class="option" id="newUser"><p class="option-text">${buttonSignUp}</p></div>
        <div class="option" id="fPass"><p class="option-text"> ${buttonForgot}</p></div>
    </div>

</div>