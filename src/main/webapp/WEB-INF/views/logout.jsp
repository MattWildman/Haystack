<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Logged out</title>
</head>
<body>
<h1>Haystack</h1>
<p>You have logged out successfully!</p>
<a href="<c:url value="/login" />">Log in again.</a>
</body>
</html>