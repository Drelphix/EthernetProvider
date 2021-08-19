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
    <a href="/login">Login</a>
    <a href="/registration">Registration</a>
</div>
<h1>The best Internet provider in the Universe</h1>
<h2>EthernetProviderX</h2>
<h3>Tariffs</h3>
    <form>
    <table>
        <th>Tariff</th>
        <th>Description</th>
        <th>Price</th>
        <th>Choose tariff</th>
        <c:forEach var="tariff" items="${tariffs}">
            <tr>
                <td>${tariff.name}</td>
                <td>${tariff.description}</td>
                <td>${tariff.price}</td>
                <td><input type="submit" value="Connect tariff" formmethod="post" formaction="\connectTariff?id=${tariff.id}"></td>
            </tr>
    </c:forEach>
    </table>
    </form>
</center>
</body>
</html>