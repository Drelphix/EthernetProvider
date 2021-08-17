<%@ page isErrorPage="true" contentType="text/html; charset=UTF-8"
 pageEncoding="UTF-8"%>
<html><title>Error page</title>
<body>
<center>
<h1>This page try to fly into space, but something went wrong:(</h1>
<h2>Error code: ${pageContext.errorData.statusCode}<h2>
<h2>Launch site: ${pageContext.errorData.requestURI}  <h2>
<h2>Servlet name: ${pageContext.errorData.servletName} <h2>
<h2>Exception: ${pageContext.exception} <h2>
<h2>Message from exception: ${pageContext.exception.message} <h2>
</center>
</body>
</html>