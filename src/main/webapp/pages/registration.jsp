<%@ page contentType="text/html; charset=UTF-8"
 pageEncoding="UTF-8"%>
<html lang="en">
<head>
    <title>Registration</title>
</head>
<body>
<center>
    <form action="/registration" method="post">
        <table>
            ${message}
            <tr>
                <td><span>Login</span></td>
            </tr>
            <tr>
                <td> <input type="text" name="username" id="username" value="${username}"></td>
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
                <td><input type="submit"  value="Register"> </td>
            </tr>
        </table>
    </form>
</center>
</body>
</html>