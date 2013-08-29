<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<h1>Messages from ${user.username}</h1>
<c:if test="${user.id != 1}">
	<form:form method="Post" action="../Inbox/${user.id}" commandName="newMessage">
		Subject: <form:input path="title" size="50" /><br>
		<form:textarea path="summary" cols="75" />
		<span class="error"><form:errors path="summary" /></span><br>
		<input type="submit" value="Send" />
	</form:form>
</c:if>
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