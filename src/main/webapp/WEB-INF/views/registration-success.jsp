<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<h1>Your registration was successful!</h1>
<p>You have successfully registered on Haystack as 
<strong><c:out value="${registration.username}" /></strong>.</p>
<p><a href="Login">Log in to start searching!</a></p>