<%@ page contentType="text/html; charset=UTF-8"
 pageEncoding="UTF-8"%>
<html lang="en">
<head>
    <title>Login</title>
</head>
<body>
<center>
<form>
    ${message}
    <table>
        <tr><td><span>Login/email:</span></td></tr>
        <tr><td><input type="text" name="username" value="${username}"></td></tr>
        <tr><td><span>Password:</span></td></tr>
        <tr><td><input type="password" name="password" value="${password}"></td></tr>
        <tr><td><input type="submit" formaction="/login" formmethod="post" value="Login"></td></tr>

    </table>
</form>
</center>
</body>
</html>