<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<h1>Messages from ${user.username}</h1>
<c:if test="${not empty messages}">
	<ul>
		<c:forEach items="${messages}" var="message">
			<li>
			<h2>${message.title} - ${message.date}</h2>
			<p>${message.summary}</p>
			</li>
		</c:forEach>
	</ul>
</c:if>
<c:if test="${empty messages}">
	<p>You haven't received any messages from ${user.username} yet.</p>
</c:if>