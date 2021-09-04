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

    <form id="sign-in" action="${pageContext.request.contextPath}/controller" method="post" >
        <input type="hidden" value="to_sign_in" name="command">
    </form>

    <form id="fpass-form" action="${pageContext.request.contextPath}/controller" method="post">
        <input type="text" name="forgotten" placeholder=${formEmail}>
        <input name="command" type="hidden" value="forgot_pass">
        <a href="#" onclick="document.getElementById('fpass-form').submit()" class="login-btn" id="getpass-btn">${buttonGetPassword}</a>
        <a href="#" class="login-btn" onclick="document.getElementById('sign-in').submit()" id="back-btn2">${buttonSignIn}</a>
    </form>


</div>