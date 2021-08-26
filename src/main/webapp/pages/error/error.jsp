<%@ page isErrorPage="true" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Error page</title>
    <script type="text/javascript">
    function Redirect()
    {
        window.location="/controller?command=tariff_list";
    }
    document.write("You will be redirected to main page in 5 seconds");
    setTimeout('Redirect()', 5000);

    </script>
</head>
<body>
<center>
    <h1>This page try to fly into space, but something went wrong:(</h1>
    <h2>Error code: ${pageContext.errorData.statusCode}</h2>
    <h2>Launch site: ${pageContext.errorData.requestURI}</h2>
    <h2>Servlet name: ${pageContext.errorData.servletName}</h2>
    <c:if test="${not empty pageContext.errorData.getThrowable()}">
        <h2>Exception: ${pageContext.errorData.getThrowable()}</h2>
    </c:if>
    <c:if test="${not empty pageContext.exception.message}">
        <h2>Message from exception: ${pageContext.exception.message} </h2>
    </c:if>
    <a href="/controller?command=tariff_list">Redirect to main page.</a>
</center>
</body>
</html>