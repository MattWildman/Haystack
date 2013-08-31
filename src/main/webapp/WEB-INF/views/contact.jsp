<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h1>${contact.username}</h1>
<p><a href="<c:url value="Inbox/${contact.id}"/>">Message</a></p>
<p><a href="<c:url value="Contact/${contact.id}"/>?action=block">Block</a></p>
<c:if test="${not empty connections}">
	<ul>
		<c:forEach items="${connections}" var="connection">
			<li>
				Your post: ${connection.key.title}<br>
				Their match:
				<ul>
				    <c:forEach items="${connection.value}" var="match">
				        <li>${match.title}</li>
				    </c:forEach>
			    </ul>
    		</li>
		</c:forEach>
	</ul>
</c:if>
<c:if test="${empty connections}">
	<%-- In theory this test should never evaluate to true --%>
	<p>You appear to have no connection with this contact!</p>
</c:if>