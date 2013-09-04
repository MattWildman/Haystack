<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h1>Connections with '${target.title}'</h1>
<c:if test="${not empty connections}">
	<ul>
		<c:forEach items="${connections}" var="connection">
			<li><a href="<c:url value="${target.id}/${connection.id}"/>">${connection.title}</a></li>
		</c:forEach>
	</ul>
</c:if>
<c:if test="${empty connections}">
	<p>${target.title} doesn't have any connections yet.</p>
	<p><a href="<c:url value="Matches/${target.id}" />">View matches</a>.</p>
</c:if>