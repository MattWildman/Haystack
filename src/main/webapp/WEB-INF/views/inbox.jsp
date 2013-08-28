<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<h1>Your inbox</h1>
<c:if test="${not empty messageThreads}">
	<ul>
		<c:forEach items="${messageThreads}" var="messageThread">
			<a href="<c:url value="/Inbox/" />${messageThread.user.id}">
			<li>
			<h2>${messageThread.user.username} (${messageThread.unreadCount} unread)</h2>
			<h3>${messageThread.lastMessage.date} - ${messageThread.lastMessage.title}</h3>
			</li>
			</a>
		</c:forEach>
	</ul>
</c:if>
<c:if test="${empty messageThreads}">
	<p>You haven't received any messages yet.</p>
</c:if>