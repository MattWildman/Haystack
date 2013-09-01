<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fm" uri="http://java.sun.com/jsp/jstl/fmt" %>
<h1>Your inbox</h1>
<c:if test="${not empty messageThreads}">
	<ul>
		<c:forEach items="${messageThreads}" var="messageThread">
			<a href="<c:url value="/Inbox/" />${messageThread.user.id}">
			<li>
			<h2>${messageThread.user.username} (${messageThread.unreadCount} unread)</h2>
			<h3><fm:formatDate value="${messageThread.lastMessage.date}" pattern="E dd/MM/yyyy 'at' HH:mm:ss" />
			 - ${messageThread.lastMessage.title}</h3>
			</li>
			</a>
		</c:forEach>
	</ul>
</c:if>
<c:if test="${empty messageThreads}">
	<p>You haven't received any messages yet.</p>
</c:if>