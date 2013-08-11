<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<h1>Your searches</h1>
<c:if test="${not empty meetings}">
	<ul>
		<c:forEach items="${meetings}" var="meeting">
			<li><a href="<c:url value="/Searches/" />${meeting.id}">${meeting.title}</a></li>
		</c:forEach>
	</ul>
</c:if>
<c:if test="${empty meetings}">
	<p>You haven't searched for anyone yet! 
	<a href="<c:url value="/FindSomeone" />">Post your first search!</a></p>
</c:if>
