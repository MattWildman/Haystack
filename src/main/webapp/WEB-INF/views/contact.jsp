<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h1>${contact.username}</h1>
<p><a href="<c:url value="/Inbox/${contact.id}"/>">Message</a></p>
<p><a href="<c:url value="${contact.id}"/>?action=block">Block</a></p>
<c:if test="${not empty connections}">
	<c:forEach items="${connections}" var="connection">
	<div>
    <c:forEach items="${connection.value}" var="match">
    	<div>
			Your post: <a href="<c:url value="/Searches/${connection.key.id}" />">${connection.key.title}</a>
		</div>
		<div>
	    	Their match: <a href="<c:url value="/Connections/${connection.key.id}/${match.id}" />">${match.title}</a>
        </div>
    </c:forEach>
	</div>
	</c:forEach>
</c:if>
<c:if test="${empty connections}">
	<%-- In theory this test should never evaluate to true --%>
	<p>You appear to have no connection with this contact!</p>
</c:if>