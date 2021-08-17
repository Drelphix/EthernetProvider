<%@ page contentType="text/html; charset=UTF-8"
 pageEncoding="UTF-8"%>
<html lang="en">
<head>
    <title>Registration</title>
</head>
<body>
<center>
    <form>
        <span>Login</span>
        <input type="text" name="username">
        <span>Password:</span>
        <input type="password" name="password">
        <span>Repeat password:</span>
        <input type="password" name="passwordRepeat">
        <span>Email:</span>
        <input type="email" name="email">
        <input type="submit" formaction="/registration" formmethod="post" value="Register">
    </form>
</center>
</body>
</html>