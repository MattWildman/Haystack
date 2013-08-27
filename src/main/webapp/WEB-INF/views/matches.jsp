<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<h1>Your matched searches</h1>
<c:if test="${not empty meetings}">
	<ul>
		<c:forEach items="${meetings}" var="meeting">
			<li><a href="<c:url value="/Matches/" />${meeting.id}">${meeting.title}</a></li>
		</c:forEach>
	</ul>
</c:if>
<c:if test="${empty meetings}">
	<p>None of your posts have any pending matches.</p>
</c:if>
