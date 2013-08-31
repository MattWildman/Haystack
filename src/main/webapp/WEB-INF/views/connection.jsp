<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<h1>${connection.title}</h1>
<p>${connection.summary}</p>
<h2>When they said you met</h2>
<p>${connection.contexts[0].earliestString} 
and ${connection.contexts[0].latestString}</p> 
<h2>Where they said you met</h2>
<c:choose>
	<c:when test="${connection.contexts[0].locationType == \"location\"}">
		<h3>${connection.contexts[0].location.title}</h3>
		<p>${connection.contexts[0].location.area}</p>
		<p>${connection.contexts[0].location.postcode}</p>
	</c:when>
	<c:otherwise>
		<h3>${connection.contexts[0].journey.title}</h3>
		<h4>Starting</h4>
		<p>${connection.contexts[0].journey.start.title}</p>
		<p>${connection.contexts[0].journey.start.area}</p>
		<p>${connection.contexts[0].journey.start.postcode}</p>
		<h4>Ending</h4>
		<p>${connection.contexts[0].journey.end.title}</p>
		<p>${connection.contexts[0].journey.end.area}</p>
		<p>${connection.contexts[0].journey.end.postcode}</p>
	</c:otherwise>
</c:choose>
<h2>How they described themselves</h2>
	<p>Name: ${connection.user.title}</p>
	<p>Aged between ${connection.user.minAge} and ${connection.user.maxAge}</p>
	<p>Height: ${connection.user.height}</p>
	<p>Description: ${connection.user.summary}</p>
<h2>How they described you</h2>
	<p>Name: ${connection.participants[0].title}</p>
	<p>Aged between ${connection.participants[0].minAge} and ${connection.participants[0].maxAge}</p>
	<p>Height: ${connection.participants[0].height}</p>
	<p>Description: ${connection.participants[0].summary}</p>

<p><a href="${originalURL}?action=reject">Reject this connection.</a></p>