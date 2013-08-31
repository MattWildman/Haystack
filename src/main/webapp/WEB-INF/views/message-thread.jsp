<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<c:if test="${user.id != 1}">

<h1>Messages with ${user.username}</h1>

	<form:form method="Post" action="../Inbox/${user.id}" commandName="newMessage">
	
		<form:input type="hidden" path="toUser" value="${user.id}"/>
		<form:input type="hidden" path="fromUser" value="${senderId}"/>
		
		Subject: <form:input path="title" size="50" /><br>
		<form:textarea path="summary" cols="75" />
		<span class="error"><form:errors path="summary" /></span><br>
		
		<input type="submit" value="Send" />
		
	</form:form>
</c:if>

<c:if test="${user.id == 1}">
<h1>Messages from ${user.username}</h1>
</c:if>

<c:if test="${not empty messages}">
	<ul>
		<c:forEach items="${messages}" var="message">
			<li>
			<c:if test="${empty message.title}">
				<c:if test="${message.fromUser == user.id}">
				<h2>Message from ${user.username}</h2>
				</c:if>
				<c:if test="${message.fromUser != user.id}">
				<h2>Message from you</h2>
				</c:if>
			</c:if>
			<c:if test="${not empty message.title}">
				<h2>${message.title}</h2>
			</c:if>
				<p>${message.date}</p>
				<p>${message.summary}</p>
			</li>
		</c:forEach>
	</ul>
</c:if>

<c:if test="${empty messages}">
	<p>You haven't exchanged any messages with ${user.username} yet.</p>
</c:if>