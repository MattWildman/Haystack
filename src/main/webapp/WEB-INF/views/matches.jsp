<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="statusFirstCap" 
	   value="${fn:toUpperCase(fn:substring(status, 0, 1))}${fn:toLowerCase(fn:substring(status, 1,fn:length(status)))}">
</c:set>
<h1>Your searches with ${status} matches</h1>
<c:if test="${not empty meetings}">
	<ul>
		<c:forEach items="${meetings}" var="meeting">
			<li>
				<a href="<c:url value="${statusFirstCap}/${meeting.id}"/>">${meeting.title}</a>
			</li>
		</c:forEach>
	</ul>
</c:if>
<c:if test="${empty meetings}">
	<p>None of your posts have any ${status} matches.</p>
</c:if>
