<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<h1>Your searches</h1>
<ul>
<c:forEach items="${meetings}" var="meeting">
<li><a href="<c:url value="/Searches/" />${meeting.id}">${meeting.title}</a></li>
</c:forEach>
</ul>