<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ page session="false" %>
<html>
<head>
	<title>Haystack</title>
</head>
<body>
<h1>Haystack</h1>
<sec:authorize access="isAuthenticated()">
	<p>You are logged in.</p>
</sec:authorize>
<p>Welcome to Haystack!</p>
<sec:authorize access="isAuthenticated()">
	<a href="<c:url value="/j_spring_security_logout" />">Logout</a>
</sec:authorize>
</body>
</html>