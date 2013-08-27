<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<h1>Pending matches for '${meeting.title}'</h1>
<c:if test="${not empty candidates}">
	<ul>
		<c:forEach items="${candidates}" var="candidate">
			<li><a href="<c:url value="/Matches/" />${meeting.id}/${candidate.id}">${candidate.title}</a></li>
		</c:forEach>
	</ul>
</c:if>
<c:if test="${empty candidates}">
	<p>This search doesn't have any pending matches.</p>
</c:if>
