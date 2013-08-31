<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h1>Connections you've made</h1>
<c:if test="${not empty connections}">
	<ul>
		<c:forEach items="${connections}" var="connection">
			<li><a href="<c:url value="Connections/${connection.id}"/>">${connection.title}</a></li>
		</c:forEach>
	</ul>
</c:if>
<c:if test="${empty connections}">
	<p>You haven't made any connections yet.</p>
	<p><a href="<c:url value="FindSomeone" />">Search for someone now</a> or <a href="<c:url value="Matches" />">view your matches</a>.</p>
</c:if>