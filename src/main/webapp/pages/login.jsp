<%@ page contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Login</title>

</head>
<body>
<center>
    <c:if test="${sessionScope.username != null}">
        <script type="text/javascript">
    setTimeout(function Redirect(){
        window.location="/controller?command=tariff_list";
        }, 0);

        </script>
    </c:if>
    <form>
        ${message}
        <table>
            <tr>
                <td><span>Login/email:</span></td>
            </tr>
            <tr>
                <td><input name="username" type="text" value="${username}"></td>
            </tr>
            <tr>
                <td><span>Password:</span></td>
            </tr>
            <tr>
                <td><input name="password" type="password" value="${password}"></td>
            </tr>
            <tr>
                <td><input name="command" type="hidden" value="sign_in">
                    <input formaction="/controller" formmethod="post" type="submit" value="SignIn"></td>
            </tr>

        </table>
</form>
</center>
</body>
</html>