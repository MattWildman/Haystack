<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<form:form method="Post" action="FindSomeone" commandName="meeting" onload="setCurrentDateTime()">
	
		<h1>Find someone</h1>
		Title: <form:input path="title" />
		<span class="error"><form:errors path="title" /></span><br>
		Summary: <form:textarea path="summary" />
		<span class="error"><form:errors path="summary" /></span><br>
		
		<h2>When did you meet?</h2>
		<input type="radio" id="no-range" name="use-range" value="no"/>I know the exact date<br>
		<input type="radio" id="yes-range" name="use-range" value="yes"/>I don't know the exact date<br>
		Earliest date and time: <input id="edate" value="01/01/1900" /> <input id="etime" value="01:00" />
		<span class="error"><form:errors path="contexts[0].earliestString" /></span><br>
		Latest date and time: <input id="ldate" /> <input id="ltime" onkeyup="processDateInput()"/>
		<span class="error"><form:errors path="contexts[0].latestString" /></span>
		<form:input id="earliest" type="hidden" path="contexts[0].earliestString" />
		<form:input id="latest" type="hidden" path="contexts[0].latestString" />
		
		<h2>Where did you meet?</h2>	
		<form:select path="contexts[0].locationType">
			<form:option value="location">At a particular place</form:option>
			<form:option value="journey">On a journey</form:option>
		</form:select><br>
		
		<h3>The place</h3>
		Name: <form:input path="contexts[0].location.title" />
		<span class="error"><form:errors path="contexts[0].location.title" /></span><br>
		Area: <form:input path="contexts[0].location.area" />
		<span class="error"><form:errors path="contexts[0].location.area" /></span><br>
		Postcode: <form:input path="contexts[0].location.postcode" />
		<span class="error"><form:errors path="contexts[0].location.postcode" /></span><br>
		Country: <form:input path="contexts[0].location.country" />
		<span class="error"><form:errors path="contexts[0].location.country" /></span><br>
		Latitude: <form:input path="contexts[0].location.lat" />
		<span class="error"><form:errors path="contexts[0].location.lat" /></span><br>
		Longitude: <form:input path="contexts[0].location.longd" />
		<span class="error"><form:errors path="contexts[0].location.longd" /></span><br>
		Radius: <form:input path="contexts[0].location.rad" />
		<span class="error"><form:errors path="contexts[0].location.rad" /></span><br>
		
		<h3>The journey</h3>
		Type of transport: <form:input path="contexts[0].journey.type" />
		<span class="error"><form:errors path="contexts[0].journey.type" /></span><br>
		Transport company: <form:input path="contexts[0].journey.company" />
		<span class="error"><form:errors path="contexts[0].journey.company" /></span><br>
		Particular service: <form:input path="contexts[0].journey.service" />
		<span class="error"><form:errors path="contexts[0].journey.service" /></span><br>

		<h4>Where on the journey did you first meet?</h4>
		Name: <form:input path="contexts[0].journey.start.title" />
		<span class="error"><form:errors path="contexts[0].journey.start.title" /></span><br>
		Area: <form:input path="contexts[0].journey.start.area" />
		<span class="error"><form:errors path="contexts[0].journey.start.area" /></span><br>
		Postcode: <form:input path="contexts[0].journey.start.postcode" />
		<span class="error"><form:errors path="contexts[0].journey.start.postcode" /></span><br>
		Country: <form:input path="contexts[0].journey.start.country" />
		<span class="error"><form:errors path="contexts[0].journey.start.country" /></span><br>
		Latitude: <form:input path="contexts[0].journey.start.lat" />
		<span class="error"><form:errors path="contexts[0].journey.start.lat" /></span><br>
		Longitude: <form:input path="contexts[0].journey.start.longd" />
		<span class="error"><form:errors path="contexts[0].journey.start.longd" /></span><br>
		Radius: <form:input path="contexts[0].journey.start.rad" />
		<span class="error"><form:errors path="contexts[0].journey.start.rad" /></span><br>
		
		<h4>Where was the last place on the journey that you were together?</h4>
		Name: <form:input path="contexts[0].journey.end.title" />
		<span class="error"><form:errors path="contexts[0].journey.end.title" /></span><br>
		Area: <form:input path="contexts[0].journey.end.area" />
		<span class="error"><form:errors path="contexts[0].journey.end.area" /></span><br>
		Postcode: <form:input path="contexts[0].journey.end.postcode" />
		<span class="error"><form:errors path="contexts[0].journey.end.postcode" /></span><br>
		Country: <form:input path="contexts[0].journey.end.country" />
		<span class="error"><form:errors path="contexts[0].journey.end.country" /></span><br>
		Latitude: <form:input path="contexts[0].journey.end.lat" />
		<span class="error"><form:errors path="contexts[0].journey.end.lat" /></span><br>
		Longitude: <form:input path="contexts[0].journey.end.longd" />
		<span class="error"><form:errors path="contexts[0].journey.end.longd" /></span><br>
		Radius: <form:input path="contexts[0].journey.end.rad" />
		<span class="error"><form:errors path="contexts[0].journey.end.rad" /></span><br>
		
		<h2>About you</h2>
		Name: 	<form:input path="user.title"/>
				<span class="error"><form:errors path="user.title" /></span><br>
		Gender:<br>
		<form:select path="user.gender">
			<form:option value="female">Female</form:option>
			<form:option value="male">Male</form:option>
		</form:select><br>
		Minimum age: <form:input path="user.minAge" />
					 <span class="error"><form:errors path="user.minAge" /></span><br>
		Maximum age: <form:input path="user.maxAge" />
					 <span class="error"><form:errors path="user.maxAge" /></span><br>
		Height: <form:input path="user.height" />
					 <span class="error"><form:errors path="user.height" /></span><br>
		Description: <form:textarea path="user.summary" />
					 <span class="error"><form:errors path="user.summary" /></span><br>
					 
		<h2>About them</h2>
		Name: 	<form:input path="participants[0].title"/>
				<span class="error"><form:errors path="participants[0].title" /></span><br>
		Gender:<br>
		<form:select path="participants[0].gender">
			<form:option value="female">Female</form:option>
			<form:option value="male">Male</form:option>
		</form:select><br>
		Minimum age: <form:input path="participants[0].minAge" />
					 <span class="error"><form:errors path="participants[0].minAge" /></span><br>
		Maximum age: <form:input path="participants[0].maxAge" />
					 <span class="error"><form:errors path="participants[0].maxAge" /></span><br>
		Height: <form:input path="participants[0].height" />
				<span class="error"><form:errors path="participants[0].height" /></span><br>
		Description: <form:textarea path="participants[0].summary" />
					 <span class="error"><form:errors path="participants[0].summary" /></span><br>
					 
		<input type="submit" value="Submit" />
					 
	</form:form>
	<script src="resources/meeting-form.js"></script>