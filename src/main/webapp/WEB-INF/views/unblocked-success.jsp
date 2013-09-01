<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h1>${contact.username} unblocked</h1>
<p>You can now exchange messages with them.</p>
<p><a href="<c:url value="/Inbox/${contact.id}" />">Message them now!</a>