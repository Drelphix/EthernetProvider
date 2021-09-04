<%@ page contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="localization.message" var="language"/>
<fmt:message key="form.username.placeholder" bundle="${language}" var="formUsername"/>
<fmt:message key="form.password.placeholder" bundle="${language}" var="formPassword"/>
<fmt:message key="form.email.placeholder" bundle="${language}" var="formEmail"/>
<fmt:message key="registration.title" bundle="${language}" var="title"/>
<fmt:message key="form.button.signUp" bundle="${language}" var="buttonSignUp"/>
<fmt:message key="form.button.signIn" bundle="${language}" var="buttonSignIn"/>
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

  <form id="sign-in" action="${pageContext.request.contextPath}/controller" method="post" >
    <input type="hidden" value="to_sign_in" name="command">
  </form>

  <form id="registration-form" action="${pageContext.request.contextPath}/controller" method="post">
    <input name="command" type="hidden" value="sign_up">
    <input type="text" name="username" placeholder=${formUsername} value="${username}">
    <input type="email" name="email" placeholder=${formEmail} value="${email}">
    <input type="password" name="pass" placeholder=${formPassword} value="${password}">
    <input type="password" name="repass" placeholder="Repeat password" value="${repeatPassword}">
    <a href="#" onclick="document.getElementById('registration-form').submit()" class="login-btn" id="signup-btn">${buttonSignUp}</a>
    <a href="#" onclick="document.getElementById('sign-in').submit()" class="login-btn" id="back-btn">${buttonSignIn}</a>
  </form>

</div>