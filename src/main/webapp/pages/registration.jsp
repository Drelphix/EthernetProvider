<%@ page contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Registration</title>
</head>
<body>
<c:if test="${sessionScope.username != null}">
    <script type="text/javascript">
        setTimeout(function Redirect(){
        window.location="/controller?command=tariff_list";
        }, 0);
    </script>
</c:if>
<center>
    <form>
        <table>
            ${message}
            <tr>
                <td><span>Login</span></td>
            </tr>
            <tr>
                <td><input id="username" name="username" type="text" value="${username}"></td>
            </tr>
            <tr>
                <td><span>Password:</span></td>
            </tr>
            <tr>
                <td> <input type="password" name="password" id="password" value="${password}"></td>
            </tr>
            <tr>
                <td><span>Repeat password:</span></td>
            </tr>
            <tr>
                <td><input type="password" name="passwordRepeat"></td>
            </tr>
            <tr>
                <td><span>Email:</span></td>
            </tr>
            <tr>
                <td><input type="email" name="email" id="email"value="${email}"></td>
            </tr>
            <tr>
                <td><input name="command" type="hidden" value="sign_up">
                    <input formaction="/controller" formmethod="post" type="submit" value="SignUp"></td>
            </tr>
        </table>
    </form>
</center>
</body>
</html>