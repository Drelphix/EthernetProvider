<%@ page contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html lang="en">
<head>
    <fmt:setLocale value="${sessionScope.locale}"/>
    <fmt:setBundle basename="localization.message" var="language"/>
    <fmt:message key="menu.profile" bundle="${language}" var="menuProfile"/>
    <fmt:message key="menu.tariffs" bundle="${language}" var="menuTariffs"/>
    <fmt:message key="provider.name" bundle="${language}" var="providerName"/>
    <fmt:message key="provider.slogan" bundle="${language}" var="providerSlogan"/>
    <fmt:message key="tariffs.title" bundle="${language}" var="title"/>
    <fmt:message key="tariff.discount.currency" bundle="${language}" var="tariffCurrency"/>
    <fmt:message key="tariff.full.currency" bundle="${language}" var="tariffFullCurrency"/>
    <fmt:message key="tariff.discount" bundle="${language}" var="tariffDiscount"/>
    <fmt:message key="tariff.connect" bundle="${language}" var="tariffConnect"/>
    <fmt:message key="form.button.signUp" bundle="${language}" var="buttonSignUp"/>
    <fmt:message key="form.button.signIn" bundle="${language}" var="buttonSignIn"/>
    <fmt:message key="form.button.forgot" bundle="${language}" var="buttonForgot"/>
    <fmt:message key="form.button.logout" bundle="${language}" var="buttonLogout"/>
    <title>${providerName}</title>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
    <link href="https://use.fontawesome.com/releases/v5.15.1/css/all.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/css/sale.css" rel="stylesheet"/>


</head>
<body style="text-align: -webkit-right;">
<nav class="sidebar-navigation">
    <ul>
        <c:choose>
            <c:when test="${username == null}">
                <!--Login-->
                <form action="/controller" method="post" id="sign-in">
                    <input name="command" type="hidden" value="to_sign_in">
                </form>
                <li class="active">
                    <a href="#" onclick="document.getElementById('sign-in').submit()">
                        <i class="fas fa-sign-in-alt"></i>
                    </a>
                    <span>${buttonSignIn}</span>
                    <span class="tooltip">${buttonSignIn}</span>
                </li>
                <!--Sign up-->
                <form action="/controller" method="post" id="sign-up">
                    <input name="command" type="hidden" value="to_sign_up">
                </form>
                <li class="active">
                    <a href="#" onclick="document.getElementById('sign-up').submit()">
                        <i class="fas fa-user-plus"></i>
                    </a>
                    <span>${buttonSignUp}</span>
                    <span class="tooltip">${buttonSignUp}</span>
                </li>
            </c:when>
            <c:otherwise>
                <!--username-->
                <li>
                    <form method="post" action="/controller" id="profile">
                        <input type="hidden" name="command" value="to_profile">
                    </form>
                    <a href="#" onclick="document.getElementById('profile').submit()">
                        <i class="fas fa-user"></i>
                    </a>
                    <span>${username}</span>
                    <span class="tooltip">${menuProfile}</span>
                </li>
                <!--logout-->
                <li>
                    <form action="/controller" method="post" id="logout">
                        <input name="command" type="hidden" value="logout">
                    </form>
                    <a href="#" onclick="document.getElementById('logout').submit()">
                        <i class="fas fa-sign-out-alt"></i>
                    </a>
                    <span>${buttonLogout}</span>
                    <span class="tooltip">${buttonLogout}</span>
                </li>
            </c:otherwise>
        </c:choose>
        <li class="active">
            <a href="#" onclick="load_admin()">
                <i class="fas fa-tools"></i>
            </a>
            <span>Workplace</span>
            <span class="tooltip">Workplace</span>

        </li>
        <li>
            <a href="#" onclick="load_home()">
                <i class="fas fa-home"></i>
            </a>
            <span>Home</span>
            <span class="tooltip">Home</span>
        </li>

        <li>
            <form method="post" action="/controller" id="tariffs">
                <input type="hidden" name="command" value="to_tariffs">
            </form>
            <a href="#" onclick="document.getElementById('tariffs').submit()">
                <i class="fas fa-fire"></i>
            </a>
            <span>${menuTariffs}</span>
            <span class="tooltip">${menuTariffs}</span>

        </li>
        <li>
            <form method="post" action="/controller" id="language">
                <input type="hidden" name="command" value="switch_language">
            </form>
            <a href="#" onclick="document.getElementById('language').submit()" style="font-size:xx-large;">
                <i class="fas fa-language"></i>
            </a>
            <span>${fn:toUpperCase(locale.getLanguage())}</span>
        </li>
    </ul>
</nav>
<div align="right">
    <div align="center">
        <h1 class="provider">${providerSlogan}</h1>
        <h1 class="provider">${providerName}</h1>
        <h1 class="provider">${title}</h1>
        <div class="pricing-tables">
            <c:forEach items="${tariffs}" var="tariff">
                <div class="pricing-table">
                    <form action="/controller" method="post">
                        <h3 class="pricing-title">${tariff.name}</h3>
                        <div class="price">${tariff.discountPrice}<sup>${tariffCurrency}</sup></div>
                        <ul class="table-list">
                            <li>${tariff.description}</li>
                            <li><span class="unlimited">${tariffDiscount}: ${tariff.promotion.discount}%</span></li>
                        </ul>
                        <div class="table-buy">
                            <p>${tariff.price}<sup>${tariffFullCurrency}</sup></p>
                            <a class="pricing-action" onclick="$('form').submit();">${tariffConnect}</a>
                            <input name="id" type="hidden" value="${tariff.id}">
                            <input name="command" type="hidden" value="to_tariff">
                        </div>
                    </form>
                </div>

            </c:forEach>
        </div>
    </div>
</div>
</body>
</html>