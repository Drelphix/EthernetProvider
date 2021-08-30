<%@ page contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="en">
<head>
    <title>EthernetProviderX</title>
    <link href="https://use.fontawesome.com/releases/v5.15.1/css/all.css" rel="stylesheet">
    <link href="../css/style.css" rel="stylesheet"/>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>

</head>
<body>
<script>
	function load_admin() {
		document.getElementById("content").innerHTML='<object type="text/html" data="./admin.html" height="100%" width="96%"></object>';
	}
	function load_home() {
		document.getElementById("content").innerHTML='<object type="text/html" data="./home.html" height="100%" width="96%"></object>';
	}
	function load_profile() {
		document.getElementById("content").innerHTML='<object type="text/html" data="./profile.html" height="100%" width="96%"></object>';
	}
	function load_sale() {
		document.getElementById("content").innerHTML='<object type="text/html" data="./sale.html" height="100%" width="96%"></object>';
	}
	function load_settings() {
		document.getElementById("content").innerHTML='<object type="text/html" data="./settings.html" height="100%" width="96%"></object>';
	}


</script>
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
            <a href="#" onclick="load_profile()">
                <i class="fas fa-user"></i>
            </a>
            <span class="tooltip">Profile</span>
        </li>
        <li>
            <a href="#" onclick="load_sale()">
                <i class="fas fa-fire"></i>
            </a>
            <span class="tooltip">Sale</span>
        </li>
        <li>
            <a href="#" onclick="load_settings()">
                <i class="fas fa-sliders-h"></i>
            </a>
            <span class="tooltip">Settings</span>
        </li>
    </ul>
</nav>
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
<div align="center">
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
</div>
</body>
</html>