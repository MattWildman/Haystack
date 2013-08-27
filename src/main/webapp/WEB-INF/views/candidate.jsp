<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<h1>${candidate.title}</h1>
<p>${candidate.summary}</p>
<h2>When they said you met</h2>
<p>${candidate.contexts[0].earliestString} 
and ${candidate.contexts[0].latestString}</p> 
<h2>Where they said you met</h2>
<c:choose>
	<c:when test="${candidate.contexts[0].locationType == \"location\"}">
		<h3>${candidate.contexts[0].location.title}</h3>
		<p>${candidate.contexts[0].location.area}</p>
		<p>${candidate.contexts[0].location.postcode}</p>
	</c:when>
	<c:otherwise>
		<h3>${candidate.contexts[0].journey.title}</h3>
		<h4>Starting</h4>
		<p>${candidate.contexts[0].journey.start.title}</p>
		<p>${candidate.contexts[0].journey.start.area}</p>
		<p>${candidate.contexts[0].journey.start.postcode}</p>
		<h4>Ending</h4>
		<p>${candidate.contexts[0].journey.end.title}</p>
		<p>${candidate.contexts[0].journey.end.area}</p>
		<p>${candidate.contexts[0].journey.end.postcode}</p>
	</c:otherwise>
</c:choose>
<h2>How they describe themselves</h2>
	<p>Name: ${candidate.user.title}</p>
	<p>Aged between ${candidate.user.minAge} and ${candidate.user.maxAge}</p>
	<p>Height: ${candidate.user.height}</p>
	<p>Description: ${candidate.user.summary}</p>
<h2>How they describe you</h2>
	<p>Name: ${candidate.participants[0].title}</p>
	<p>Aged between ${candidate.participants[0].minAge} and ${candidate.participants[0].maxAge}</p>
	<p>Height: ${candidate.participants[0].height}</p>
	<p>Description: ${candidate.participants[0].summary}</p>

<p><a href="${originalURL}?action=accept">This is the correct match!</a></p>
<p><a href="${originalURL}?action=reject">This is not the correct match.</a></p>