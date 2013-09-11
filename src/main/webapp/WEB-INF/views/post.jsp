<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<h1>${meeting.title}</h1>
<p>${meeting.summary}</p>
<c:if test="${!hidden}">
<p>You will receive match notifications for this search.<br>
<a href="${originalURL}?action=hide">Hide from database?</a></p>
</c:if>
<c:if test="${hidden}">
<p>You are not receiving match notifications for this search.<br>
<a href="${originalURL}?action=activate">Activate search?</a></p>
</c:if>
<h2>When</h2>
<p>${meeting.contexts[0].earliestString} 
and ${meeting.contexts[0].latestString}</p> 
<h2>Where</h2>
<c:choose>
	<c:when test="${meeting.contexts[0].locationType == \"location\"}">
		<h3>${meeting.contexts[0].location.title}</h3>
		<p>${meeting.contexts[0].location.area}</p>
		<p>${meeting.contexts[0].location.postcode}</p>
	</c:when>
	<c:otherwise>
		<h3>${meeting.contexts[0].journey.title}</h3>
		<h4>Starting</h4>
		<p>${meeting.contexts[0].journey.start.title}</p>
		<p>${meeting.contexts[0].journey.start.area}</p>
		<p>${meeting.contexts[0].journey.start.postcode}</p>
		<h4>Ending</h4>
		<p>${meeting.contexts[0].journey.end.title}</p>
		<p>${meeting.contexts[0].journey.end.area}</p>
		<p>${meeting.contexts[0].journey.end.postcode}</p>
	</c:otherwise>
</c:choose>
<h2>About you</h2>
	<p>Name: ${meeting.user.title}</p>
	<p>Aged between ${meeting.user.minAge} and ${meeting.user.maxAge}</p>
	<p>Height: ${meeting.user.height}</p>
	<p>Description: ${meeting.user.summary}</p>
<h2>About them</h2>
	<p>Name: ${meeting.participants[0].title}</p>
	<p>Aged between ${meeting.participants[0].minAge} and ${meeting.participants[0].maxAge}</p>
	<p>Height: ${meeting.participants[0].height}</p>
	<p>Description: ${meeting.participants[0].summary}</p>