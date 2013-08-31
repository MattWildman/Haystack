<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="statusFirstCap" 
	   value="${fn:toUpperCase(fn:substring(status, 0, 1))}${fn:toLowerCase(fn:substring(status, 1,fn:length(status)))}">
</c:set>
<h1>${statusFirstCap} matches for '${meeting.title}'</h1>
<c:if test="${not empty candidates}">
	<ul>
		<c:forEach items="${candidates}" var="candidate">
			<li>
				<a href="<c:url value="${meeting.id}/${candidate.id}"/>">${candidate.title}</a>
			</li>
		</c:forEach>
	</ul>
</c:if>
<c:if test="${empty candidates}">
	<p>This search doesn't have any ${status} matches.</p>
</c:if>
