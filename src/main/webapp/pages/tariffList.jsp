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
                <form>
                    <input name="command" type="hidden" value="user_info">
                    <input formaction="/controller" formmethod="post" type="submit" value="${sessionScope.username}">
                </form>
                <form>
                    <input name="command" type="hidden" value="logout">
                    <input formaction="/controller" formmethod="post" type="submit" value="Logout">
                </form>
            </c:when>
            <c:otherwise>
                <form>
                    <input name="command" type="hidden" value="to_sign_in">
                    <input formaction="/controller" formmethod="post" type="submit" value="SignIn">
                </form>
                <form>
                    <input name="command" type="hidden" value="to_sign_up">
                    <input formaction="/controller" formmethod="post" type="submit" value="SignUp">
                </form>
            </c:otherwise>
        </c:choose>
    </div>
    <h1>The best Internet provider in the Universe</h1>
    <h2>EthernetProviderX</h2>
    <h3>Tariffs</h3>
    <table>
        <th>Tariff</th>
        <th>Description</th>
        <th>Price</th>
        <th>Choose tariff</th>
        <c:forEach items="${tariffs}" var="tariff">
            <form>
                <tr>
                    <td>${tariff.name}</td>
                    <td>${tariff.description}</td>
                    <td>${tariff.price}</td>
                    <td><input name="command" type="hidden" value="connect_tariff">
                        <input name="id" type="hidden" value="${tariff.id}">
                        <input formaction="/controller" formmethod="post" type="submit" value="Connect tariff"></td>
                </tr>
            </form>
        </c:forEach>
    </table>
</center>
</body>
</html>