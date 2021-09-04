<%@ page contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="en">
<head>
    <title>EthernetProviderX</title>
</head>
<body>
<center>
    <div align="right">
        <c:choose>
            <c:when test="${sessionScope.username != null}">
                <a href="/user?=${sessionScope.username}">${sessionScope.username}</a>
                <a href="/logout">Logout</a>
            </c:when>
            <c:otherwise>
                <a href="/login">Login</a>
                <a href="/registration">Registration</a>
            </c:otherwise>
        </c:choose>
    </div>
    <div>
        <form>
            <table>
                <tr>
                    <td>Tariff:
                        <input name="tariffId" type="hidden" value="${tariffId}">
                    </td>
                    <td>${tariff.name}</td>
                </tr>
                <tr>
                    <td>Description:</td>
                    <td>${tariff.description}</td>
                </tr>
                <tr>
                    <td>Price per month:</td>
                    <td>${tariff.price}</td>
                </tr>
                <c:if test="${tariff.promotion.id != -1}">
                    <tr>
                        <td>Price with discount:</td>
                        <td>${discountPrice}</td>
                    </tr>
                    <tr>
                        <td>Active promotion:</td>
                        <td>${tariff.promotion.name}</td>
                    </tr>
                    <tr>
                        <td>Discount:</td>
                        <td>${tariff.promotion.discount}%</td>
                    </tr>
                    <tr>
                        <td>Until:</td>
                        <td>${tariff.promotion.endDate}</td>
                    </tr>
                </c:if>
                <tr>
                    <td>
                        <input formaction="/controller" formmethod="post" type="submit" value="Connect tariff"/>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</center>
</body>
</html>