<%@ page contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="localization.message" var="language"/>
<fmt:message key="form.username.placeholder" bundle="${language}" var="formUsername"/>
<fmt:message key="form.password.placeholder" bundle="${language}" var="formPassword"/>
<fmt:message key="form.email.placeholder" bundle="${language}" var="formEmail"/>
<fmt:message key="login.title" bundle="${language}" var="title"/>
<fmt:message key="form.button.signUp" bundle="${language}" var="buttonSignUp"/>
<fmt:message key="form.button.signIn" bundle="${language}" var="buttonSignIn"/>
<fmt:message key="form.button.forgot" bundle="${language}" var="buttonForgot"/>
<fmt:message key="form.button.getPassword" bundle="${language}" var="buttonGetPassword"/>

<html>
<head>
    <title>${title}</title>
    <link href="${pageContext.request.contextPath}/css/auth.css" rel="stylesheet"/>
</head>
<body>
<body>
<div class="mobile-screen">
    <div class="header">
        <h1>${title}</h1>
    </div>
    <c:if test="${message != null}">
        <center><span style="color: rgba(255,255,255,.5)">${message}</span></center>
    </c:if>
    <div class="logo"></div>

    <form id="sign-up" action="${pageContext.request.contextPath}/controller" method="post" >
        <input type="hidden" value="to_sign_up" name="command">
    </form>
    <form id="forgot" action="${pageContext.request.contextPath}/controller" method="post" >
        <input type="hidden" value="to_forgot_pass" name="command">
    </form>

    <form id="login-form" action="${pageContext.request.contextPath}/controller" method="post">
        <input name="command" type="hidden" value="sign_in">
        <input type="text" name="username" placeholder=${formUsername} value="${username}">
        <input type="password" name="password" placeholder=${formPassword} value="${password}">
        <a href="#" onclick="document.getElementById('login-form').submit()" class="login-btn">${buttonSignIn}</a>

    </form>

    <div class="other-options">
        <div class="option"><a class="option-text" onclick="document.getElementById('sign-up').submit()" >${buttonSignUp}</a></div>
        <div class="option"><a class="option-text" onclick="document.getElementById('forgot').submit()"> ${buttonForgot}</a></div>
    </div>

</div>