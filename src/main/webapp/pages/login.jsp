<%@ page contentType="text/html; charset=UTF-8"
 pageEncoding="UTF-8"%>
<html lang="en">
<head>
    <title>Login</title>
</head>
<body>
<center>
<form>
    <span>Login/email:</span>
    <input type="text" name="username">
    <span>Password:</span>
    <input type="password" name="password">
    <input type="submit" formaction="/login" formmethod="post" value="Login">
</form>
</center>
</body>
</html>