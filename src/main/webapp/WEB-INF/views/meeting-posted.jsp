<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:if test="${not empty candidates}">
	<h1>We've found some matches!</h1>
	<p>One or more entries in our database matched yours! 
	<a href="<c:url value="/Matches" />">View your matches now!</a></p>
</c:if>
<c:if test="${empty candidates}">
	<h1>Meeting posted</h1>
	<p>There are no matches in our database yet, 
	but we'll let you know as soon as matching connection is posted.</p>
</c:if>
