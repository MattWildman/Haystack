<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h1>Match accepted</h1>
<c:if test="${success}">
	<p>Good news! The person who posted this search has also accepted yours! 
	Check your <a href="">inbox</a> now to get in touch with them.</p>
</c:if>
<c:if test="${not success}">
	<p>We will notify you if the other participant accepts your post.</p>
</c:if>