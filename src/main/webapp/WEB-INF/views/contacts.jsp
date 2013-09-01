<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h1>People you're connected with</h1>
<c:if test="${not empty contacts}">
	<ul>
		<c:forEach items="${contacts}" var="contact">
			<li>
				<h2>${contact.username}</h2>
				<ul>
					<li><a href="<c:url value="Contacts/${contact.id}"/>">View shared connections</a></li>
					<li><a href="<c:url value="/Inbox/${contact.id}"/>">Message</a></li>
					<li><a href="<c:url value="Contacts/${contact.id}"/>?action=block">Block</a></li>
				</ul>
			</li>
		</c:forEach>
	</ul>
</c:if>
<c:if test="${empty contacts}">
	<p>You haven't connected with anyone yet.</p>
	<p><a href="<c:url value="FindSomeone" />">Search for someone now</a> or <a href="<c:url value="Matches" />">view your matches</a>.</p>
</c:if>
<c:if test="${not empty blockedContacts}">
	<p><a href="<c:url value="Contacts/Blocked" />">View contacts you've blocked</a>.</p>
</c:if>