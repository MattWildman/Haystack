<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h1>People you've blocked</h1>
<c:if test="${not empty blockedContacts}">
	<ul>
		<c:forEach items="${blockedContacts}" var="contact">
			<li>
				<h2>${contact.username}</h2>
				<p><a href="<c:url value="${contact.id}"/>?action=unblock">Unblock?</a></p>
			</li>
		</c:forEach>
	</ul>
</c:if>
<c:if test="${empty blockedContacts}">
	<p>You haven't blocked anyone from messaging you!</p>
</c:if>