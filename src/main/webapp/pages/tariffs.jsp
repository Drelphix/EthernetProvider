<%@ page contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="en">
<head>
    <title>EthernetProviderX</title>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
    <link href="https://use.fontawesome.com/releases/v5.15.1/css/all.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/css/sale.css" rel="stylesheet"/>

</head>
<body style="text-align: -webkit-right;">
<nav class="sidebar-navigation">
    <ul>
        <li class="active">
            <a href="#" onclick="load_admin()">
                <i class="fas fa-tools"></i>
            </a>
            <span class="tooltip">Workplace</span>
        </li>
        <li>
            <a href="#" onclick="load_home()">
                <i class="fas fa-home"></i>
            </a>
            <span class="tooltip">Home</span>
        </li>
        <li>
            <form method="post" action="/controller" id="profile">
                <input type="hidden" name="command" value="to_profile">
            </form>
            <a href="#" onclick="document.getElementById('profile').submit()">
                <i class="fas fa-user"></i>
            </a>
            <span class="tooltip">Profile</span>
        </li>
        <li>
            <form method="post" action="/controller" id="tariffs">
                <input type="hidden" name="command" value="to_tariffs">
            </form>
            <a href="#" onclick="document.getElementById('tariffs').submit()">
                <i class="fas fa-fire"></i>
            </a>
            <span class="tooltip">Tariffs</span>

        </li>
        <li>
            <a href="#" onclick="load_settings()">
                <i class="fas fa-sliders-h"></i>
            </a>
            <span class="tooltip">Settings</span>
        </li>
    </ul>
</nav>
<div align="right" style="display:inline-flex">
    <c:choose>
        <c:when test="${sessionScope.username != null}">
            <div class="option"><a class="option-text">${sessionScope.username}</a></div>
                <form action="/controller" method="post" id="logout">
                <input name="command" type="hidden" value="logout">
                    <div class="option"><a class="option-text" onclick="document.getElementById('logout').submit()" >Logout</a></div>
                </form>
            </c:when>
            <c:otherwise>
                <form action="/controller" method="post" id="sign-in">
                    <input name="command" type="hidden" value="to_sign_in">
                    <div class="option"><a class="option-text" onclick="document.getElementById('sign-in').submit()" >Sign In</a></div>
                </form>
                <form action="/controller" method="post" id="sign-up">
                    <input name="command" type="hidden" value="to_sign_up">
                    <div class="option"><a class="option-text" onclick="document.getElementById('sign-up').submit()" >Sign Up</a></div>
                </form>
            </c:otherwise>
        </c:choose>
    </div>
<div align="center">
    <h1>The best Internet provider in the Universe</h1>
    <h1>EthernetProviderX</h1>
    <h1>Tariffs</h1>
        <c:forEach items="${tariffs}" var="tariff">

                    <div class="pricing-table">
                        <form action="/controller" method="post">
                        <h3 class="pricing-title">${tariff.name}</h3>
                        <div class="price">${tariff.discountPrice}<sup>usd/month</sup></div>
                        <ul class="table-list">
                            <li>${tariff.description}</li>
                            <li><span class="unlimited">Discount: ${tariff.promotion.discount}%</span></li>
                        </ul>
                        <div class="table-buy">
                            <p>${tariff.price}<sup>руб/мес</sup></p>
                            <a class="pricing-action" onclick="$('form').submit();">Connect tariff</a>
                            <input name="id" type="hidden" value="${tariff.id}">
                            <input name="command" type="hidden" value="to_tariff">
                        </div>
                        </form>
                    </div>


        </c:forEach>
</div>
</body>
</html>